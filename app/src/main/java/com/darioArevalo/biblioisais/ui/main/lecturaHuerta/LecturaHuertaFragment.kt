package com.darioArevalo.biblioisais.ui.main.lecturaHuerta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.remote.lecturahuerta.LecturaHuertaDataSource
import com.darioArevalo.biblioisais.databinding.FragmentLecturaHuertaBinding
import com.darioArevalo.biblioisais.domain.lecturahuerta.LecturaHuertaRepoImpl
import com.darioArevalo.biblioisais.presentation.LecturaHuertaViewModel
import com.darioArevalo.biblioisais.presentation.LecturaHuertaViewModelFactory
import com.darioArevalo.biblioisais.ui.main.lecturaHuerta.adapter.LecturaHuertaAdapter


class LecturaHuertaFragment : Fragment(R.layout.fragment_lectura_huerta) {

    private lateinit var binding: FragmentLecturaHuertaBinding
    private val viewModel by viewModels<LecturaHuertaViewModel> { LecturaHuertaViewModelFactory(
        LecturaHuertaRepoImpl(LecturaHuertaDataSource())
    ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        })

    }
}