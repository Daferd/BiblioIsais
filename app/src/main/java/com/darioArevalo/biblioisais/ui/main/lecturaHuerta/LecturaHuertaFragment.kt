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
import com.darioArevalo.biblioisais.data.model.PostServer
import com.darioArevalo.biblioisais.data.remote.lecturahuerta.LecturaHuertaDataSource
import com.darioArevalo.biblioisais.databinding.FragmentLecturaHuertaBinding
import com.darioArevalo.biblioisais.domain.lecturahuerta.LecturaHuertaRepoImpl
import com.darioArevalo.biblioisais.presentation.LecturaHuertaViewModel
import com.darioArevalo.biblioisais.presentation.LecturaHuertaViewModelFactory
import com.darioArevalo.biblioisais.ui.main.lecturaHuerta.adapter.LecturaHuertaAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.grpc.internal.SharedResourceHolder


class LecturaHuertaFragment : Fragment(R.layout.fragment_lectura_huerta), LecturaHuertaAdapter.OnPostClickListener {

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
                    binding.progressBarLecturaHuerta.visibility = View.VISIBLE
                }
                is Result.Success->{
                    binding.progressBarLecturaHuerta.visibility = View.GONE
                    Log.d("Livedata","${result.data}")
                    binding.rvPostList.adapter = LecturaHuertaAdapter(result.data,this)
                }

                is Result.Failure->{
                    binding.progressBarLecturaHuerta.visibility = View.GONE
                    Log.d("livedata error","{${result.exception}}")
                }
            }

        })


        val fabButton = view.findViewById<ExtendedFloatingActionButton>(R.id.extended_fab)

        fabButton.setOnClickListener{
                findNavController().navigate(R.id.action_navigation_lecturaHuerta_to_agregarTemaFragment)
        }



    }

    override fun onPostClick(Post: PostServer) {
        val bundle = Bundle()
        bundle.putParcelable("post",Post)
        findNavController().navigate(R.id.action_navigation_lecturaHuerta_to_detallesPostFragment,bundle)
    }
}