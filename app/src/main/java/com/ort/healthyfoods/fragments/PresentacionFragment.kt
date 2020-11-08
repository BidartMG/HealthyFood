package com.ort.healthyfoods.fragments

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ort.healthyfoods.R

class PresentacionFragment : Fragment() {
    private lateinit var vista: View
    private lateinit var imgAlm: ImageView
    private lateinit var imgDes: ImageView
    private lateinit var imgCol: ImageView
    private lateinit var imgAddAlm: ImageView
    private lateinit var imgAddDes: ImageView
    private lateinit var imgAddCol: ImageView
    private lateinit var imgRealiz: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_presentacion, container, false)
        imgAlm = vista.findViewById(R.id.img_alm)
        imgCol = vista.findViewById(R.id.img_col)
        imgDes = vista.findViewById(R.id.img_des)
        imgAddAlm = vista.findViewById(R.id.img_add_alm)
        imgAddCol = vista.findViewById(R.id.img_add_col)
        imgAddDes = vista.findViewById(R.id.img_add_des)
        imgRealiz = vista.findViewById(R.id.img_realizad)
        return vista
    }

    override fun onStart() {
        super.onStart()



    }
}