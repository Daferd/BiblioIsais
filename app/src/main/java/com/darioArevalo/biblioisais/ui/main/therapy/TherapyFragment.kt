package com.darioArevalo.biblioisais.ui.main.therapy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.databinding.FragmentTherapyBinding


class TherapyFragment : Fragment(R.layout.fragment_therapy) {

    private lateinit var binding: FragmentTherapyBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTherapyBinding.bind(view)

        binding.psychologyCardView.setOnClickListener {
            Toast.makeText(context,"Psychology",Toast.LENGTH_SHORT).show()
        }
        binding.spiritualCardView.setOnClickListener {
            Toast.makeText(context,"Spiritual",Toast.LENGTH_SHORT).show()
        }
        binding.medicineCardview.setOnClickListener {
            Toast.makeText(context,"Medicine",Toast.LENGTH_SHORT).show()
        }


    }
}