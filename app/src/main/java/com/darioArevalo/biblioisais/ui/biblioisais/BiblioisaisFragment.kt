package com.darioArevalo.biblioisais.ui.biblioisais

import android.content.Intent
import android.net.Uri
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
import com.darioArevalo.biblioisais.databinding.FragmentBiblioisaisBinding
import com.darioArevalo.biblioisais.server.CursoServer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BiblioisaisFragment : Fragment(), CursosRVAdapter.OnItemClickListener {

    //private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var binding: FragmentBiblioisaisBinding
    private var cursosList: MutableList<CursoServer> = mutableListOf()
    private lateinit var cursosRVAdapter: CursosRVAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_biblioisais,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBiblioisaisBinding.bind(view)
        binding.cursos1RecyclerView.layoutManager=
                LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        binding.cursos1RecyclerView.setHasFixedSize(true)

        cursosRVAdapter = CursosRVAdapter(
                cursosList as ArrayList<CursoServer>, this@BiblioisaisFragment
        )

        binding.cursos1RecyclerView.adapter = cursosRVAdapter

        cargarDesdeFirebase()

    }

    private fun cargarDesdeFirebase() {
        val database = FirebaseDatabase.getInstance()
        val myCursosRef = database.getReference("cursos").child("curso1")

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(data: DataSnapshot in snapshot.children){
                    val cursoServer = data.getValue(CursoServer::class.java)
                    cursoServer?.let { cursosList.add(it) }
                }
                cursosRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        myCursosRef.addValueEventListener(postListener)
    }

    override fun onItemClik(curso: CursoServer) {
        val webIntent : Intent = Uri.parse(curso.url).let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }
        startActivity(webIntent)
    }
}