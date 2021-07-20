package com.darioArevalo.biblioisais.ui.main.biblioisais

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.core.hide
import com.darioArevalo.biblioisais.core.show
import com.darioArevalo.biblioisais.databinding.FragmentBiblioisaisBinding
import com.darioArevalo.biblioisais.data.model.CourseServer
import com.darioArevalo.biblioisais.data.remote.courses.CoursesDataSource
import com.darioArevalo.biblioisais.domain.courses.CoursesRepoImpl
import com.darioArevalo.biblioisais.presentation.courses.CoursesViewModel
import com.darioArevalo.biblioisais.presentation.courses.CoursesViewModelFactory
import com.darioArevalo.biblioisais.ui.main.biblioisais.adapters.CoursesAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BiblioisaisFragment : Fragment(R.layout.fragment_biblioisais), CoursesAdapter.OnEpisodesClickListener{


    private lateinit var binding: FragmentBiblioisaisBinding
    private val viewModel by viewModels<CoursesViewModel>{ CoursesViewModelFactory(
            CoursesRepoImpl(
                    CoursesDataSource()
            )
    ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBiblioisaisBinding.bind(view)

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)

        viewModel.fetchEpisodesCourse1().observe(viewLifecycleOwner,{ courseResult->
            when(courseResult){
                is Result.Loading -> {
                    binding.progressBar.show()
                    viewModel.fetchEpisodesCourse2().observe(viewLifecycleOwner,{ courseResult->
                        when(courseResult){
                            is Result.Loading -> {

                            }

                            is Result.Success -> {
                                binding.cursos2RecyclerView.adapter = CoursesAdapter(courseResult.data, this@BiblioisaisFragment)
                            }

                            is Result.Failure -> {
                                Toast.makeText(context,"Hubo un error: ${courseResult.exception}",Toast.LENGTH_SHORT).show()
                            }
                        }
                    })

                    viewModel.fetchEpisodiesCourse3().observe(viewLifecycleOwner,{ courseResult->
                        when(courseResult){
                            is Result.Loading -> {

                            }

                            is Result.Success -> {
                                binding.otherCoursesRecyclerView.adapter = CoursesAdapter(courseResult.data, this@BiblioisaisFragment)
                            }

                            is Result.Failure -> {
                                Toast.makeText(context,"Hubo un error: ${courseResult.exception}",Toast.LENGTH_SHORT).show()
                            }
                        }
                    })

                }

                is Result.Success -> {
                    binding.progressBar.hide()
                    binding.curso1textView.show()
                    binding.curso2textView.show()
                    binding.otherCoursestextView.show()
                    binding.cursos1RecyclerView.adapter = CoursesAdapter(courseResult.data, this@BiblioisaisFragment)
                }

                is Result.Failure -> {
                    binding.progressBar.hide()
                    Toast.makeText(context,"Hubo un error: ${courseResult.exception}",Toast.LENGTH_SHORT).show()
                }
            }
        })





    }

    override fun onEpisodesClick(episode: CourseServer) {
        if(episode.name=="Justicia Especial Indígena"){
            val user = FirebaseAuth.getInstance().currentUser
            user?.let {
                val db = FirebaseFirestore.getInstance()
                db.collection("users").document(user.uid).get().addOnSuccessListener{ document ->
                    document.let {
                        val form = document.getBoolean("form")
                        if(form==false){
                            val action = BiblioisaisFragmentDirections.actionNavigationBiblioisaisToSurveyFragment(episode)
                            findNavController().navigate(action)
                        } else {
                            val webIntent : Intent = Uri.parse(episode.courseUrl).let { webpage ->
                                Intent(Intent.ACTION_VIEW,webpage)
                            }
                            startActivity(webIntent)
                        }
                    }
                }
            }

        } else {
            val webIntent : Intent = Uri.parse(episode.courseUrl).let { webpage ->
                Intent(Intent.ACTION_VIEW,webpage)
            }
            startActivity(webIntent)
        }

    }


}