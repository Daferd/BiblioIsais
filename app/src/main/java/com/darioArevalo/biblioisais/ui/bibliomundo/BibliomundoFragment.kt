package com.darioArevalo.biblioisais.ui.bibliomundo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.databinding.FragmentBibliomundoBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BibliomundoFragment : Fragment() {

    //private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentBibliomundoBinding

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

        showPrueba()
    }

    private fun showPrueba() {
        val database = FirebaseDatabase.getInstance()
        val myDatabaseRef=database.getReference("usuarios").child("nombre")

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.value
                binding.textHome.text=name.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        myDatabaseRef.addValueEventListener(postListener)
    }
}