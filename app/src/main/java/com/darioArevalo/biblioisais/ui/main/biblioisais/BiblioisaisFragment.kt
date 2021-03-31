package com.darioArevalo.biblioisais.ui.main.biblioisais

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
import com.darioArevalo.biblioisais.databinding.FragmentBiblioisaisBinding
import com.darioArevalo.biblioisais.data.model.CursoServer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BiblioisaisFragment : Fragment(), CursosRVAdapter.OnItemClickListener {

    //private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var binding: FragmentBiblioisaisBinding

    private var cursos1List: MutableList<CursoServer> = mutableListOf()
    private lateinit var cursos1RVAdapter: CursosRVAdapter

    private var cursos2List: MutableList<CursoServer> = mutableListOf()
    private lateinit var cursos2RVAdapter: CursosRVAdapter

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

        cursos1RVAdapter = CursosRVAdapter(
                cursos1List as ArrayList<CursoServer>, this@BiblioisaisFragment
        )

        binding.cursos1RecyclerView.adapter = cursos1RVAdapter

        cargarCurso1DesdeFirebase()

        binding.cursos2RecyclerView.layoutManager=
                LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        binding.cursos2RecyclerView.setHasFixedSize(true)

        cursos2RVAdapter = CursosRVAdapter(
                cursos2List as ArrayList<CursoServer>, this@BiblioisaisFragment
        )

        binding.cursos2RecyclerView.adapter = cursos2RVAdapter

        cargarCurso2DesdeFirebase()

    }

    private fun cargarCurso2DesdeFirebase() {
        val database = FirebaseDatabase.getInstance()
        val myCursosRef = database.getReference("cursos").child("curso1")

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(data: DataSnapshot in snapshot.children){
                    val cursoServer = data.getValue(CursoServer::class.java)
                    cursoServer?.let { cursos2List.add(it) }
                }
                cursos2RVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        myCursosRef.addValueEventListener(postListener)
    }


    private fun cargarCurso1DesdeFirebase() {
        val database = FirebaseDatabase.getInstance()
        val myCursosRef = database.getReference("cursos").child("curso1")

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(data: DataSnapshot in snapshot.children){
                    val cursoServer = data.getValue(CursoServer::class.java)
                    cursoServer?.let { cursos1List.add(it) }
                }
                cursos1RVAdapter.notifyDataSetChanged()
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