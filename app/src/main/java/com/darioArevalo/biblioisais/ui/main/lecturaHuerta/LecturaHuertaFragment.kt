package com.darioArevalo.biblioisais.ui.main.lecturaHuerta

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.PostServer
import com.darioArevalo.biblioisais.data.remote.lecturahuerta.LecturaHuertaDataSource
import com.darioArevalo.biblioisais.databinding.ActivityMainBinding
import com.darioArevalo.biblioisais.databinding.FragmentLecturaHuertaBinding
import com.darioArevalo.biblioisais.domain.lecturahuerta.LecturaHuertaRepoImpl
import com.darioArevalo.biblioisais.presentation.LecturaHuertaViewModel
import com.darioArevalo.biblioisais.presentation.LecturaHuertaViewModelFactory
import com.darioArevalo.biblioisais.ui.main.lecturaHuerta.adapter.LecturaHuertaAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.grpc.internal.SharedResourceHolder


class LecturaHuertaFragment : Fragment(R.layout.fragment_lectura_huerta), LecturaHuertaAdapter.OnPostClickListener {
    private lateinit var Adapter: LecturaHuertaAdapter
    private lateinit var binding: FragmentLecturaHuertaBinding
    private val viewModel by viewModels<LecturaHuertaViewModel> { LecturaHuertaViewModelFactory(
        LecturaHuertaRepoImpl(LecturaHuertaDataSource())
    ) }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentLecturaHuertaBinding.bind(view)


        binding.rvPostList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPostList.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
        viewModel.fetchLatestPosts().observe(viewLifecycleOwner, Observer { result->
            when(result){
                is Result.Loading->{
                    Log.d("Livedata","Loading...")
                    binding.progressBarLecturaHuerta.visibility = View.VISIBLE
                }
                is Result.Success->{
                    binding.progressBarLecturaHuerta.visibility = View.GONE
                    Log.d("Livedata","${result.data}")
                    Adapter = LecturaHuertaAdapter(result.data as ArrayList<PostServer>,this)
                    binding.rvPostList.adapter = Adapter//LecturaHuertaAdapter(result.data as ArrayList<PostServer>,this)
                }

                is Result.Failure->{
                    binding.progressBarLecturaHuerta.visibility = View.GONE
                    Log.d("livedata error","{${result.exception}}")
                }
            }

        })


        binding.searchView.imeOptions =EditorInfo.IME_ACTION_DONE
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                Adapter.filter.filter(newText)
                return false
            }
        })

        val fabButton = view.findViewById<ExtendedFloatingActionButton>(R.id.extended_fab)
        fabButton.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_lecturaHuerta_to_agregarTemaFragment)
        }

    }
/*
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        try{
            val item = menu?.findItem(R.id.searchView_MenuMain)
            val searchView: SearchView = item?.actionView as SearchView
            searchView.imeOptions = EditorInfo.IME_ACTION_DONE
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Adapter.filter.filter(newText)
                    return false

                }

            })

        }catch (e:Exception){
            e.printStackTrace()
        }

    }*/
/*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
        return true

    }*/

    override fun onPostClick(Post: PostServer) {
        val bundle = Bundle()
        bundle.putParcelable("post",Post)
        findNavController().navigate(R.id.action_navigation_lecturaHuerta_to_detallesPostFragment,bundle)
    }
}