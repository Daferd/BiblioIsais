package com.darioArevalo.biblioisais.ui.bibliomundo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.databinding.FragmentBibliomundoBinding
import com.darioArevalo.biblioisais.server.BibliotecaServer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BibliomundoFragment : Fragment(), BibliotecaRVAdapter.OnItemClickListener {

    //private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentBibliomundoBinding
    private var bibliotecasList: MutableList<BibliotecaServer> = mutableListOf()
    private lateinit var bibliotecaRVAdapter : BibliotecaRVAdapter
    private val link : String = "https://bibliotecanacional.gov.co/es-co"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bibliomundo,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBibliomundoBinding.bind(view)

        binding.bibliotecasRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        binding.bibliotecasRecyclerView.setHasFixedSize(true)

        bibliotecaRVAdapter = BibliotecaRVAdapter(
            bibliotecasList as ArrayList<BibliotecaServer>, this@BibliomundoFragment
        )

        binding.bibliotecasRecyclerView.adapter = bibliotecaRVAdapter

        cargarDesdeFirebase()
    }

    private fun cargarDesdeFirebase() {
        val database = FirebaseDatabase.getInstance()
        val myBiblioRef = database.getReference("bibliotecas")

        bibliotecasList.clear()

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data: DataSnapshot in snapshot.children) {
                    val bibliotecaServer = data.getValue(BibliotecaServer::class.java)
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

    override fun onItemClick(biblioteca: BibliotecaServer) {
        val webIntent : Intent = Uri.parse(biblioteca.url).let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }
        startActivity(webIntent)
    }

}