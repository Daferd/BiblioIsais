package com.darioArevalo.biblioisais.ui.bibliotecas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.databinding.FragmentBibliotecasBinding
import com.darioArevalo.biblioisais.server.BibliotecaServer
import com.darioArevalo.biblioisais.ui.bibliomundo.BibliotecaRVAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BibliotecasFragment : Fragment() {

    //private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var binding: FragmentBibliotecasBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bibliotecas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBibliotecasBinding.bind(view)


    }
}

