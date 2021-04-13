package com.darioArevalo.biblioisais.ui.main.bibliomundo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.databinding.FragmentBibliomundoBinding
import com.darioArevalo.biblioisais.data.model.LibraryServer
import com.darioArevalo.biblioisais.data.remote.libraries.LibrariesDataSource
import com.darioArevalo.biblioisais.domain.Libraries.LibrariesRepoImpl
import com.darioArevalo.biblioisais.presentation.libraries.LibrariesViewModel
import com.darioArevalo.biblioisais.presentation.libraries.LibrariesViewModelFactory
import com.darioArevalo.biblioisais.ui.main.bibliomundo.adapters.LibrariesAdapterAux

class BibliomundoFragment : Fragment(R.layout.fragment_bibliomundo), LibrariesAdapterAux.OnLibraryClickListener {

    //private lateinit var homeViewModel: HomeViewModel
    private lateinit var concatAdapter : ConcatAdapter
    private lateinit var binding: FragmentBibliomundoBinding
    private val viewModel by viewModels<LibrariesViewModel>{LibrariesViewModelFactory(
        LibrariesRepoImpl(
            LibrariesDataSource()
        )
    )}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBibliomundoBinding.bind(view)

        //concatAdapter = ConcatAdapter()

        viewModel.fetchLocalLibraries().observe(viewLifecycleOwner,{ localResult ->
            when(localResult){
                is Result.Loading -> {
                    //binding.progressBar.show()
                }

                is Result.Success -> {
                    //binding.progressBar.hide()
                    binding.localLibrariesRecyclerView.adapter = LibrariesAdapterAux(localResult.data,this@BibliomundoFragment)
                }
                is Result.Failure -> {
                    //binding.progressBar.hide()
                    Toast.makeText(context,"Hubo un error: ${localResult.exception}",Toast.LENGTH_SHORT).show()

                }
            }
        })

        viewModel.fetchNationalLibraries().observe(viewLifecycleOwner, { nationalResults ->
            when(nationalResults){
                is Result.Success ->{
                    binding.nationalLibrariesRecyclerView.adapter=LibrariesAdapterAux(nationalResults.data,this@BibliomundoFragment)
                }
                is Result.Failure -> {
                    Toast.makeText(
                            requireContext(),
                            "Hubo un error : ${nationalResults.exception}",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        viewModel.fetchInternationalLibraries().observe(viewLifecycleOwner, { internationalResults ->
            when(internationalResults){
                is Result.Success ->{
                    binding.internationalLibrariesRecyclerView.adapter=LibrariesAdapterAux(internationalResults.data,this@BibliomundoFragment)
                }
                is Result.Failure -> {
                    Toast.makeText(
                            requireContext(),
                            "Hubo un error : ${internationalResults.exception}",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        /*viewModel.fetchLibraries().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(0, LocalConcatAdapter(LibrariesAdapter(result.data.first., this@BibliomundoFragment)))
                        addAdapter(1, NationalConcatAdapter(LibrariesAdapter(result.data.second., this@BibliomundoFragment)))
                        addAdapter(2, InternationalConcatAdapter(LibrariesAdapter(result.data.third., this@BibliomundoFragment)))
                    }
                    binding.bibliotecasRecyclerView.adapter = concatAdapter
                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("FetchError", "Error: $result.exception ")
                    Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_SHORT)
                            .show()
                }
            }
        })*/

    }

    override fun onLibraryClick(library: LibraryServer) {
        val webIntent : Intent = Uri.parse(library.pageUrl).let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }
        startActivity(webIntent)
    }

}