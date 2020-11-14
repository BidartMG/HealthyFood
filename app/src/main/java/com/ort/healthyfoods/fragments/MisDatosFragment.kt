package com.ort.healthyfoods.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ort.healthyfoods.R

class MisDatosFragment : Fragment() {

    companion object {
        fun newInstance() = MisDatosFragment()
    }

    private lateinit var viewModel: MisDatosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.mis_datos_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MisDatosViewModel::class.java)
        // TODO: Use the ViewModel
    }

}