package com.darioArevalo.biblioisais.ui.main.biblioisais

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.darioArevalo.biblioisais.data.remote.biblioisais.BiblioisaisDataSource
import com.darioArevalo.biblioisais.domain.biblioisais.BiblioisaisRepoImpl
import com.darioArevalo.biblioisais.presentation.biblioisais.BiblioisaisViewModel
import com.darioArevalo.biblioisais.presentation.biblioisais.BiblioisaisViewModelFactory
import com.darioArevalo.biblioisais.ui.main.biblioisais.adapters.BiblioisaisAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BiblioisaisFragment : Fragment(R.layout.fragment_biblioisais), BiblioisaisAdapter.OnEpisodesClickListener{


    private lateinit var binding: FragmentBiblioisaisBinding
    private val viewModel by viewModels<BiblioisaisViewModel>{ BiblioisaisViewModelFactory(
            BiblioisaisRepoImpl(
                    BiblioisaisDataSource()
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
                    binding.certificateInOwnLawTextView.hide()
                    binding.certificateOnEconomiesAndOrchardsTextView.hide()
                    binding.otherCoursesTextView.hide()
                    binding.educativeOfferTextView.hide()
                    binding.descriptionOwnLawTextView.hide()
                    binding.descriptionEconomiesAndOrchardsTextView.hide()
                    binding.descriptionOtherCoursesTextView.hide()
                    binding.descriptionEducativeOfferTextView.hide()


                    viewModel.fetchEpisodesCourse2().observe(viewLifecycleOwner,{ courseResult->
                        when(courseResult){
                            is Result.Success -> {
                                binding.cursos2RecyclerView.adapter = BiblioisaisAdapter(courseResult.data, this@BiblioisaisFragment)
                            }
                            is Result.Failure -> {
                                Toast.makeText(context,"Hubo un error: ${courseResult.exception}",Toast.LENGTH_SHORT).show()
                            }
                        }
                    })

                    viewModel.fetchEpisodiesCourse3().observe(viewLifecycleOwner,{ courseResult->
                        when(courseResult){
                            is Result.Success -> {
                                binding.otherCoursesRecyclerView.adapter = BiblioisaisAdapter(courseResult.data, this@BiblioisaisFragment)
                            }
                            is Result.Failure -> {
                                Toast.makeText(context,"Hubo un error: ${courseResult.exception}",Toast.LENGTH_SHORT).show()
                            }
                        }
                    })

                    viewModel.fetchEpisodiesCourse4().observe(viewLifecycleOwner,{ courseResult->
                        when(courseResult){
                            is Result.Success -> {
                                binding.educativeOfferRecyclerView.adapter = BiblioisaisAdapter(courseResult.data, this@BiblioisaisFragment)
                            }
                            is Result.Failure -> {
                                Toast.makeText(context,"Hubo un error: ${courseResult.exception}",Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                }

                is Result.Success -> {
                    binding.progressBar.hide()
                    binding.certificateInOwnLawTextView.show()
                    binding.certificateOnEconomiesAndOrchardsTextView.show()
                    binding.otherCoursesTextView.show()
                    binding.educativeOfferTextView.show()
                    binding.descriptionOwnLawTextView.show()
                    binding.descriptionEconomiesAndOrchardsTextView.show()
                    binding.descriptionOtherCoursesTextView.show()
                    binding.descriptionEducativeOfferTextView.show()

                    binding.cursos1RecyclerView.adapter = BiblioisaisAdapter(courseResult.data, this@BiblioisaisFragment)
                }

                is Result.Failure -> {
                    binding.progressBar.hide()
                    Toast.makeText(context,"Hubo un error: ${courseResult.exception}",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onEpisodesClick(episode: CourseServer) {
        if(episode.nameCourse=="Diplomado en derecho propio"){
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

        } else if(episode.nameCourse=="Oferta educativa"){
            Toast.makeText(context,"Solicitar cita al WhatsApp 57 3216562260",Toast.LENGTH_LONG).show()
        }

        else {
            val webIntent : Intent = Uri.parse(episode.courseUrl).let { webpage ->
                Intent(Intent.ACTION_VIEW,webpage)
            }
            startActivity(webIntent)
        }

    }
}