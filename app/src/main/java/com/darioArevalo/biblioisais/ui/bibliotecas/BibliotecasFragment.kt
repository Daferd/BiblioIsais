package com.darioArevalo.biblioisais.ui.bibliotecas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.databinding.FragmentBibliomundoBinding
import com.darioArevalo.biblioisais.databinding.FragmentBibliotecasBinding
import com.darioArevalo.biblioisais.server.BibliotecaServer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class BibliotecasFragment : Fragment(), BibliotecaRVAdapter.OnItemClickListener {

    //private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var binding: FragmentBibliotecasBinding
    private var bibliotecasList: MutableList<BibliotecaServer> = mutableListOf()
    private lateinit var bibliotecaRVAdapter : BibliotecaRVAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bibliotecas,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBibliotecasBinding.bind(view)

        binding.bibliotecasRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        binding.bibliotecasRecyclerView.setHasFixedSize(true)

        bibliotecaRVAdapter = BibliotecaRVAdapter(
                bibliotecasList as ArrayList<BibliotecaServer>, this@BibliotecasFragment
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
        TODO("Not yet implemented")
    }
}