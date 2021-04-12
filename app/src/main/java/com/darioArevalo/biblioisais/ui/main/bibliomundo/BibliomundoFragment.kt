package com.darioArevalo.biblioisais.ui.main.bibliomundo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.databinding.FragmentBibliomundoBinding
import com.darioArevalo.biblioisais.data.model.LibraryServer
import com.darioArevalo.biblioisais.data.remote.libraries.LibrariesDataSource
import com.darioArevalo.biblioisais.domain.Libraries.LibrariesRepoImpl
import com.darioArevalo.biblioisais.presentation.libraries.LibrariesViewModel
import com.darioArevalo.biblioisais.presentation.libraries.LibrariesViewModelFactory
import com.darioArevalo.biblioisais.ui.main.bibliomundo.adapters.LibrariesAdapter
import com.darioArevalo.biblioisais.ui.main.bibliomundo.adapters.concat.InternationalConcatAdapter
import com.darioArevalo.biblioisais.ui.main.bibliomundo.adapters.concat.LocalConcatAdapter
import com.darioArevalo.biblioisais.ui.main.bibliomundo.adapters.concat.NationalConcatAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BibliomundoFragment : Fragment(R.layout.fragment_bibliomundo), LibrariesAdapter.OnLibraryClickListener {

    //private lateinit var homeViewModel: HomeViewModel
    private lateinit var concatAdapter : ConcatAdapter
    private lateinit var binding: FragmentBibliomundoBinding
    private val viewModel by viewModels<LibrariesViewModel>{LibrariesViewModelFactory(
        LibrariesRepoImpl(
            LibrariesDataSource()
        )
    )}

    //private var bibliotecasList: MutableList<LibraryServer> = mutableListOf()
    //private lateinit var bibliotecaRVAdapter : BibliotecaRVAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBibliomundoBinding.bind(view)

        concatAdapter = ConcatAdapter()

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

        /*binding.bibliotecasRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        binding.bibliotecasRecyclerView.setHasFixedSize(true)

        bibliotecaRVAdapter = BibliotecaRVAdapter(
            bibliotecasList as ArrayList<BibliotecaServer>, this@BibliomundoFragment
        )

        binding.bibliotecasRecyclerView.adapter = bibliotecaRVAdapter

        cargarDesdeFirebase()*/
    }

  /*  private fun cargarDesdeFirebase() {
        val database = FirebaseDatabase.getInstance()
        val myBiblioRef = database.getReference("bibliotecas")

        bibliotecasList.clear()

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data: DataSnapshot in snapshot.children) {
                    val bibliotecaServer = data.getValue(LibraryServer::class.java)
                    bibliotecaServer?.let { bibliotecasList.add(it) }
                }
                bibliotecaRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        myBiblioRef.addValueEventListener(postListener)
    }

    override fun onItemClick(library: LibraryServer) {
        val webIntent : Intent = Uri.parse(library.pageUrl).let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }
        startActivity(webIntent)
    }*/

    override fun onLibraryClick(library: LibraryServer) {
        TODO("Not yet implemented")
    }

}