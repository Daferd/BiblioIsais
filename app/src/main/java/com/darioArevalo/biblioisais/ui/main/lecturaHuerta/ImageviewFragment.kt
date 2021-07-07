package com.darioArevalo.biblioisais.ui.main.lecturaHuerta

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.data.model.ImageBundle
import com.darioArevalo.biblioisais.data.model.PostServer
import com.darioArevalo.biblioisais.databinding.FragmentImageviewBinding


class ImageviewFragment : Fragment() {

    private lateinit var image_bundle : ImageBundle
    private lateinit var binding: FragmentImageviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireArguments().let {
            image_bundle = it.getParcelable("image_view")!!
            Log.d("detalles","$image_bundle")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_imageview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentImageviewBinding.bind(view)
        Glide.with(requireContext()).load(image_bundle.bitmap).fitCenter().into(binding.photoViewDetallesFragment)

        //setupView(view)
        //setupClickListeners(view)
    }

    private fun setupView(view: View) {

    }

}