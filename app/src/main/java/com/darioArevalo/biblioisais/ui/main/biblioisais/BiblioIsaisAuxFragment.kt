package com.darioArevalo.biblioisais.ui.main.biblioisais

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.databinding.FragmentBiblioIsaisAuxBinding


class BiblioIsaisAuxFragment : Fragment(R.layout.fragment_biblio_isais_aux) {

    private lateinit var binding: FragmentBiblioIsaisAuxBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentBiblioIsaisAuxBinding.bind(view)
        //mncxz

    }
}