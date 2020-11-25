package com.ort.healthyfoods.fragments

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.ort.healthyfoods.entities.Food

class ListColacionesViewModel : ViewModel() {
    private var colaciones: MutableList<Food> = arrayListOf()
    lateinit private var comidaPrueba: Food

    private val db = FirebaseFirestore.getInstance()

    fun initColaciones () {
        colaciones.add(Food(90,"Yogurt","Yogurt descremado x 200cc",Food.Constants.tipoDulce,"https://t1.uc.ltmcdn.com/images/4/6/6/img_como_hacer_yogur_desnatado_casero_33664_600_square.jpg",100))
        colaciones.add(Food(91,"Yogurt con frutas","Yogurt descremado con frutas x150cc",Food.Constants.tipoDulce,"https://www.infobae.com/new-resizer/f7ne9scZ8BTpG60rqKT47b1MXNc=/750x0/filters:quality(100)/arc-anglerfish-arc2-prod-infobae.s3.amazonaws.com/public/6VB7QLPDY5GLZK7R2R4RJ5LFF4",64))
        colaciones.add(Food(92,"Melón","Porción de 3 tajadas de melón",Food.Constants.tipoDulce,"https://estaticos.miarevista.es/media/cache/760x570_thumb/uploads/images/article/598475725bafe828522cbaf5/cenarmelon-ppal.jpg",46))
        colaciones.add(Food(93,"Sandía","Porción de 2 rodajas de sandía",Food.Constants.tipoDulce,"https://farmaciaegea.es/noticias/wp-content/uploads/2018/05/beneficios-Sandia.jpg",75))
        colaciones.add(Food(94,"Frutillas","Porción de 1 taza de frutillas",Food.Constants.tipoDulce,"https://image.freepik.com/foto-gratis/fresas-frescas-taza_73558-3002.jpg",46))
        colaciones.add(Food(95,"Pasas de uvas","Porción de 20 pasas de uvas chicas",Food.Constants.tipoDulce,"https://cdn2.cocinadelirante.com/sites/default/files/styles/gallerie/public/images/2018/10/cuantos-gramos-de-pasas-de-uva-hay-que-comer-por-dia.jpg",60))
        colaciones.add(Food(96,"Maníes","Porción de 15 maníes",Food.Constants.tipoSalada,"https://pxb.cdn.noticiasnet.com.ar/noticiasnet/082020/1597456636634.jpg",85))
        colaciones.add(Food(97,"Palmitos","Porción de 5 palmitos",Food.Constants.tipoSalada,"https://www.camporel.com/wp-content/uploads/2019/05/PALMITO-R-2-600x600.jpg",60))
        colaciones.add(Food(98,"Tomate","Porción de 1 tomate grande",Food.Constants.tipoSalada,"https://lh3.googleusercontent.com/proxy/ZAdARbS-qvnK63_m48HlUzIMzejnXkA-TWsXw1pNR_Ar6tdwNCIFi8srDKSGFAy1U3u6MdQF_SHEklSAP6dBpUmKjp8NuxcwEKMAw6qCmaDrSYYUudunxn5wZ8U",33))
        colaciones.add(Food(99,"Aceitunas","Porción de 4 aceitunas verdes",Food.Constants.tipoSalada,"https://www.regiomendoza.com/wp-content/uploads/2019/03/aceitunas-verdes-organicas-sueltas-250-gr.jpg",50))
        colaciones.add(Food(100,"Queso fundido","Porción de 1 triángulo de queso fundido light",Food.Constants.tipoSalada,"https://images.rappi.com.ar/products/568136-1564419417835.jpg?e=webp",75))
    }

    fun cargar_Colaciones_Base() {
        for (elemento in colaciones){
            comidaPrueba = elemento
            val newFood = hashMapOf(
                "idComida" to comidaPrueba.idComida,
                "nombre" to comidaPrueba.nombre,
                "tipoComida" to comidaPrueba.tipoComida,
                "calorias" to comidaPrueba.calorias,
                "descripcion" to comidaPrueba.descripcion,
                "urlImagen" to comidaPrueba.urlImagen
            )

            db.collection("colaciones")
                .add(newFood)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG,"DocumentSnapshot written with ID: ${documentReference.id}\"La comida se cargó en la BBDD\"")
                }
                .addOnFailureListener {
                        e -> Log.w(ContentValues.TAG, "ERROR writing document", e)
                }
        }
    }
}