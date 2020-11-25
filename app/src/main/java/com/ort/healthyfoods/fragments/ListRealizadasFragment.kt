package com.ort.healthyfoods.fragments

import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.ort.healthyfoods.R
import com.ort.healthyfoods.entities.Food
import com.ort.healthyfoods.entities.FoodDetail
import kotlinx.android.synthetic.main.list_realizadas_fragment.*
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.temporal.ChronoUnit

class ListRealizadasFragment : Fragment() {

    companion object {
        fun newInstance() = ListRealizadasFragment()
    }

    private lateinit var viewModel: ListRealizadasViewModel
    private lateinit var vista: View
    private lateinit var caloriasConsumidas: TextView
    private lateinit var caloriasSemanales: TextView

    lateinit var btnDetalle : Button

    var comidasRealizadasList: MutableList<Food> = arrayListOf()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var acumuladorCalorias: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vista = inflater.inflate(R.layout.list_realizadas_fragment, container, false)

        caloriasConsumidas = vista.findViewById(R.id.edt_calorias_consumidas)
        caloriasSemanales = vista.findViewById(R.id.edt_calorias_semana)
        btnDetalle = vista.findViewById(R.id.btn_detalle_realizadas)

        val now = Instant.now()
        val truncated = now.truncatedTo((ChronoUnit.DAYS))

        val usuario: String = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE).getString("USER","default")!!

        db.collection("comidasRealizadas")
            .whereEqualTo("usuario", usuario)
            .whereGreaterThan("fechaRealizada", Timestamp.from(truncated)) //agregue
            .orderBy("fechaRealizada")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val myObject = document.toObject(Food::class.java)
                    comidasRealizadasList.add(myObject)
                    acumuladorCalorias += myObject.calorias
                }
                caloriasConsumidas.text = acumuladorCalorias.toString()
                caloriasSemanales.setText("Total de calorias consumidas en el día")


            }
            .addOnFailureListener {exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ")
            }

        btnDetalle.setOnClickListener {
            val irA = PresentacionFragmentDirections.actionPresentacionFragmentToComidasRealizadasFragment()
            //val comidasList = ListRealizadasFragmentDirections.actionListRealizadasFragmentToComidasRealizadasFragment()
            vista.findNavController().navigate(irA)
        }

        return vista
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListRealizadasViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
        val usuario: String = requireContext().getSharedPreferences("myPreferences",Context.MODE_PRIVATE).getString("USER", "default")!!

        var acumulados: ArrayList<Int> = ArrayList()
        var lText: ArrayList<String> = ArrayList()
        var formatter = SimpleDateFormat("dd-MM")

        val now1 = Instant.now()
        val truncatedUltimo = now1.minus(7, ChronoUnit.DAYS)

        db.collection("comidasRealizadas")
            .whereEqualTo("usuario", usuario)
            .orderBy("fechaRealizada", Query.Direction.ASCENDING)
            .whereGreaterThan("fechaRealizada", Timestamp.from(truncatedUltimo)) //agregue
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val myObject = document.toObject(FoodDetail::class.java)

                    val index = lText.indexOf(if(myObject.fechaRealizada == null) "Sin Informar" else formatter.format(myObject.fechaRealizada))
                    if(index == -1){
                        lText.add(if(myObject.fechaRealizada == null) "Sin Informar" else formatter.format(myObject.fechaRealizada))
                        acumulados.add(myObject.calorias)
                    }else{
                        acumulados[index] += myObject.calorias
                    }
                }

                val chart = this.grafico
                chart.setDrawGridBackground(false)
                chart.setTouchEnabled(false)
                chart.isDragEnabled = false
                chart.setPinchZoom(false)
                chart.isDoubleTapToZoomEnabled = false


                val l = chart.legend
                l.isEnabled = false

                val axisLeft = chart.axisLeft
                axisLeft.spaceTop = 35f
                axisLeft.axisMinimum = 0f

                val axisRight = chart.axisRight
                axisRight.isEnabled = false

                val axisX = chart.xAxis
                axisX.granularity = 1f
                axisX.position = XAxis.XAxisPosition.BOTTOM
                axisX.setValueFormatter(object : ValueFormatter() {
                    override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
                        return lText.get(value.toInt())
                    }
                })

                val lAcumulados: ArrayList<BarEntry> = ArrayList<BarEntry>()
                for ((index, value) in acumulados.withIndex()) {
                    lAcumulados.add(BarEntry(index.toFloat(), value.toFloat()))
                }

                val barAcumulado = BarDataSet(lAcumulados, "Acumulados")
                barAcumulado.axisDependency = YAxis.AxisDependency.LEFT
                barAcumulado.color = Color.GREEN
                barAcumulado.setDrawValues(false)

                val data = BarData(barAcumulado)
                data.barWidth = 0.9f

                chart.data= data
                chart.setFitBars(true)

                chart.invalidate()
            }

            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: " + exception.message.toString())
            }

    }
}
