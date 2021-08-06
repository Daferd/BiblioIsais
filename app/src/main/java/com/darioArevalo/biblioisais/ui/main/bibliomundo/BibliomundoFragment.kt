package com.darioArevalo.biblioisais.ui.main.bibliomundo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.core.hide
import com.darioArevalo.biblioisais.core.show
import com.darioArevalo.biblioisais.databinding.FragmentBibliomundoBinding
import com.darioArevalo.biblioisais.data.model.LibraryServer
import com.darioArevalo.biblioisais.data.remote.bibliomundo.BibliomundoDataSource
import com.darioArevalo.biblioisais.domain.bibliomundo.BibliomundoRepoImpl
import com.darioArevalo.biblioisais.presentation.bibliomundo.BibliomundoViewModel
import com.darioArevalo.biblioisais.presentation.bibliomundo.BibliomundoViewModelFactory
import com.darioArevalo.biblioisais.ui.main.bibliomundo.adapters.BibliomundoAdapter


class BibliomundoFragment : Fragment(R.layout.fragment_bibliomundo), BibliomundoAdapter.OnLibraryClickListener {

    //private lateinit var concatAdapter : ConcatAdapter
    private lateinit var binding: FragmentBibliomundoBinding
    private val viewModel by viewModels<BibliomundoViewModel>{BibliomundoViewModelFactory(
        BibliomundoRepoImpl(
            BibliomundoDataSource()
        )
    )}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBibliomundoBinding.bind(view)


        viewModel.fetchLocalLibraries().observe(viewLifecycleOwner,{ localResult ->
            when(localResult){
                is Result.Loading -> {
                    binding.progressBar.show()
                    binding.localLibrariesTextView.hide()
                    binding.nationalLibrariesTextView.hide()
                    binding.internationalLibrariesTextView.hide()
                    viewModel.fetchNationalLibraries().observe(viewLifecycleOwner, { nationalResults ->
                        when(nationalResults){
                            is Result.Success ->{
                                binding.nationalLibrariesRecyclerView.adapter=BibliomundoAdapter(nationalResults.data,this@BibliomundoFragment)
                            }
                            is Result.Failure -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Hubo un error : ${nationalResults.exception}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else -> {}
                        }
                    })

                    viewModel.fetchInternationalLibraries().observe(viewLifecycleOwner, { internationalResults ->
                        when(internationalResults){
                            is Result.Success ->{
                                binding.internationalLibrariesRecyclerView.adapter=BibliomundoAdapter(internationalResults.data,this@BibliomundoFragment)
                            }
                            is Result.Failure -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Hubo un error : ${internationalResults.exception}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else -> {}
                        }
                    })
                }

                is Result.Success -> {
                    binding.progressBar.hide()
                    binding.localLibrariesTextView.show()
                    binding.nationalLibrariesTextView.show()
                    binding.internationalLibrariesTextView.show()
                    binding.localLibrariesRecyclerView.adapter = BibliomundoAdapter(localResult.data,this@BibliomundoFragment)
                }
                is Result.Failure -> {
                    //binding.progressBar.hide()
                    Toast.makeText(context,"Hubo un error: ${localResult.exception}",Toast.LENGTH_SHORT).show()

                }
            }
        })




        /*
        viewModel.fetchLibraries().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    //binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    //binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(0, LocalConcatAdapter(LibrariesAdapter(result.data.first, this@BibliomundoFragment)))
                        addAdapter(1, NationalConcatAdapter(LibrariesAdapter(result.data.second, this@BibliomundoFragment)))
                        addAdapter(2, InternationalConcatAdapter(LibrariesAdapter(result.data.third, this@BibliomundoFragment)))
                    }
                    Log.d("resultLocal","libreria local: ${result.data.first}")
                    binding.bibliotecasRecyclerView.adapter = concatAdapter
                }
                is Result.Failure -> {
                    //binding.progressBar.visibility = View.GONE
                    Log.e("FetchError", "Error: ${result.exception} ")
                    Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_SHORT)
                            .show()
                }
            }
        })
        */

    }

    override fun onLibraryClick(library: LibraryServer) {
        val libraryName = library.name
        if(libraryName=="Biblioteca ISAIS"){
            findNavController().navigate(R.id.action_navigation_bibliomundo_to_navigation_biblioteca)
        }else{
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(library.pageUrl))
            startActivity(webIntent)
        }

    }

}