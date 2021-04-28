package com.darioArevalo.biblioisais.ui.main.lecturaHuerta

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.remote.lecturahuerta.LecturaHuertaDataSource
import com.darioArevalo.biblioisais.databinding.FragmentLecturaHuertaBinding
import com.darioArevalo.biblioisais.domain.lecturahuerta.LecturaHuertaRepoImpl
import com.darioArevalo.biblioisais.presentation.LecturaHuertaViewModel
import com.darioArevalo.biblioisais.presentation.LecturaHuertaViewModelFactory
import com.darioArevalo.biblioisais.ui.main.lecturaHuerta.adapter.LecturaHuertaAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.grpc.internal.SharedResourceHolder


class LecturaHuertaFragment : Fragment(R.layout.fragment_lectura_huerta) {

    private lateinit var binding: FragmentLecturaHuertaBinding
    private val viewModel by viewModels<LecturaHuertaViewModel> { LecturaHuertaViewModelFactory(
        LecturaHuertaRepoImpl(LecturaHuertaDataSource())
    ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLecturaHuertaBinding.bind(view)

        binding.rvPostList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPostList.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))


        viewModel.fetchLatestPosts().observe(viewLifecycleOwner, Observer { result->
            when(result){
                is Result.Loading->{
                    Log.d("Livedata","Loading...")
                }
                is Result.Success->{
                    Log.d("Livedata","${result.data}")
                    binding.rvPostList.adapter = LecturaHuertaAdapter(result.data)
                }

                is Result.Failure->{
                    Log.d("livedata error","{${result.exception}}")
                }
            }

        })


        val fabButton = view.findViewById<FloatingActionButton>(R.id.floating_action_button)

        fabButton.setOnClickListener{
                findNavController().navigate(R.id.action_navigation_lecturaHuerta_to_agregarTemaFragmentDialog)
        }

       // val floraCardView = view.findViewById<CardView>(R.id.card_view_Lectura_Flora)
       // val faunaCardViewImg = view.findViewById<ImageButton>(R.id.imgButtonFauna)
/*
    floraCardView.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_lecturaHuerta_to_flora_blog_graph_nav)
        }

    faunaCardViewImg.setOnClickListener{
        findNavController().navigate(R.id.action_navigation_lecturaHuerta_to_fauna_blog_graph_nav)
    }
*/

        //binding = FragmentLecturaHuertaBinding.bind(view)
        //binding.imgButtonFauna.setOnClickListener{
        //    findNavController().navigate(R.id.fauna_blog_navigation)

        //}



        /*
        binding = FragmentLecturaHuertaBinding.bind(view)

        viewModel.fetchLatestPosts().observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvBlog.adapter = LecturaHuertaAdapter(result.data)
                }

                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                            requireContext(),
                            "Hubo un error : ${result.exception}",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })*/

    }


}