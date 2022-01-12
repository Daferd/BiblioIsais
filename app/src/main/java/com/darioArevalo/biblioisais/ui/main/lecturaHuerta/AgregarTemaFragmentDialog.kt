package com.darioArevalo.biblioisais.ui.main.lecturaHuerta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.darioArevalo.biblioisais.R


class AgregarTemaFragmentDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_agregar_tema_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView(view)
        setupClickListeners(view)
    }

    private fun setupView(view: View) {
        //view.tvTitle.text = arguments?.getString(KEY_TITLE)
        //view.tvSubTitle.text = arguments?.getString(KEY_SUBTITLE)
    }

    private fun setupClickListeners(view: View) {
        /*view.btnPositive.setOnClickListener {
            dismiss()
        }
        view.btnNegative.setOnClickListener {
            dismiss()
        }*/
    }


}