package com.darioArevalo.biblioisais.ui.main.therapy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.databinding.FragmentTherapyDialogBinding

class TherapyFragmentDialog : DialogFragment() {

    private lateinit var binding: FragmentTherapyDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_therapy_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTherapyDialogBinding.bind(view)
    }


}