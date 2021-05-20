package com.darioArevalo.biblioisais.ui.main.lecturaHuerta

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.PostServer
import com.darioArevalo.biblioisais.data.remote.lecturahuerta.CommentPostDataSource
import com.darioArevalo.biblioisais.databinding.FragmentDetallesPostBinding
import com.darioArevalo.biblioisais.domain.lecturahuerta.CommentPostRepoImp
import com.darioArevalo.biblioisais.presentation.lecturahuerta.CommentPostViewModel
import com.darioArevalo.biblioisais.presentation.lecturahuerta.CommentPostViewModelFactory
import com.darioArevalo.biblioisais.ui.main.lecturaHuerta.adapter.LecturaHuertaAdapter
import com.darioArevalo.biblioisais.ui.main.lecturaHuerta.adapter.commentAdapter


class DetallesPostFragment : Fragment() {

    private lateinit var post : PostServer
    private lateinit var binding: FragmentDetallesPostBinding
    private val viewModel by viewModels<CommentPostViewModel> { CommentPostViewModelFactory(
        CommentPostRepoImp(CommentPostDataSource())
    ) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            post = it.getParcelable<PostServer>("post")!!
            Log.d("detalles","$post")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalles_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetallesPostBinding.bind(view)
        binding.rvPostDetalles.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPostDetalles.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))


        binding.profileUserNameDetalles.text = post.autor
        binding.txtTitleDetalles.text = post.titulo
        binding.txtContenidoDetalles.text = post.contenido
        Glide.with(requireContext()).load(post.profile_picture).centerCrop().into(binding.profilePhotoDetalles)
        Glide.with(requireContext()).load(post.post_image).centerCrop().into(binding.photoViewDetalles)

        viewModel.fechtLatestComments(post.post_Id).observe(viewLifecycleOwner, Observer { result->
            when(result){
                is Result.Loading->{
                    Log.d("Livedata","Loading...")
                    //binding.progressBarLecturaHuerta.visibility = View.VISIBLE
                }
                is Result.Success->{
                    //binding.progressBarLecturaHuerta.visibility = View.GONE
                    Log.d("Livedata","${result.data}")
                    binding.rvPostDetalles.adapter = commentAdapter(result.data)
                    //binding.rvPostList.adapter = LecturaHuertaAdapter(result.data)
                }

                is Result.Failure->{
                    //binding.progressBarLecturaHuerta.visibility = View.GONE
                    Log.d("livedata error","{${result.exception}}")
                }
            }

        })


    }


}