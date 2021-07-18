package com.darioArevalo.biblioisais.ui.main.therapy

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.hide
import com.darioArevalo.biblioisais.core.show
import com.darioArevalo.biblioisais.databinding.FragmentTherapyBinding


class TherapyFragment : Fragment(R.layout.fragment_therapy) {

    private lateinit var binding: FragmentTherapyBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTherapyBinding.bind(view)

        /*binding.psychologyCardView.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_therapy_to_therapyFragmentDialog)
            Toast.makeText(context,"Psychology",Toast.LENGTH_SHORT).show()
        }*/

        /*binding.expandButton.setOnClickListener {
            if(binding.expandableLayoutPsychology.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(binding.psychologyCardView,AutoTransition())
                binding.expandableLayoutPsychology.show()
                binding.collapseButton.hide()
                binding.expandButton.show()
            } else {
                TransitionManager.beginDelayedTransition(binding.psychologyCardView,AutoTransition())
                binding.expandableLayoutPsychology.hide()
                binding.collapseButton.show()
                binding.expandButton.hide()
            }
        }*/

        binding.expandButton.setOnClickListener {
                TransitionManager.beginDelayedTransition(binding.psychologyCardView,AutoTransition())
                binding.expandableLayoutPsychology.show()
                binding.collapseButton.show()
                binding.expandButton.hide()
        }

        binding.collapseButton.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.psychologyCardView,AutoTransition())
            binding.expandableLayoutPsychology.hide()
            binding.collapseButton.hide()
            binding.expandButton.show()
        }

        binding.spiritualCardView.setOnClickListener {
            Toast.makeText(context,"Spiritual",Toast.LENGTH_SHORT).show()
        }
        binding.medicineCardview.setOnClickListener {
            Toast.makeText(context,"Medicine",Toast.LENGTH_SHORT).show()
        }


    }
}