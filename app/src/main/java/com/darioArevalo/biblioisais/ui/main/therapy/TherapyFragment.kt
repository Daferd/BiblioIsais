package com.darioArevalo.biblioisais.ui.main.therapy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.databinding.FragmentTherapyBinding


class TherapyFragment : Fragment(R.layout.fragment_therapy) {

    private lateinit var binding: FragmentTherapyBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTherapyBinding.bind(view)

    }
}