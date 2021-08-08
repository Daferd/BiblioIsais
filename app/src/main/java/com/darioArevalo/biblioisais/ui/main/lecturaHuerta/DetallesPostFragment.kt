package com.darioArevalo.biblioisais.ui.main.lecturaHuerta

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.core.hide
import com.darioArevalo.biblioisais.data.model.ImageBundle
import com.darioArevalo.biblioisais.data.model.PostServer
import com.darioArevalo.biblioisais.data.model.TimeUtils
import com.darioArevalo.biblioisais.data.remote.lecturahuerta.CommentPostDataSource
import com.darioArevalo.biblioisais.databinding.FragmentDetallesPostBinding
import com.darioArevalo.biblioisais.domain.lecturahuerta.CommentPostRepoImp
import com.darioArevalo.biblioisais.presentation.lecturahuerta.CommentPostViewModel
import com.darioArevalo.biblioisais.presentation.lecturahuerta.CommentPostViewModelFactory
import com.darioArevalo.biblioisais.ui.main.lecturaHuerta.adapter.commentAdapter


class DetallesPostFragment : Fragment() {

    private lateinit var post : PostServer
    private lateinit var binding: FragmentDetallesPostBinding
    private lateinit var AdapterComment:commentAdapter
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
        val rootview = inflater.inflate(R.layout.fragment_detalles_post,container,false)
        val recyclerView = rootview?.findViewById<RecyclerView>(R.id.rv_postDetalles)

        AdapterComment = commentAdapter(ArrayList())

        return rootview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetallesPostBinding.bind(view)
        binding.rvPostDetalles.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPostDetalles.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))

        val created_at = post.created_at?.let {
            TimeUtils.timeAgo(it)
        }

        binding.profileUserNameDetalles.text = post.autor
        binding.txtTitleDetalles.text = post.titulo
        binding.txtContenidoDetalles.text = post.contenido

        binding.postTimeDetalles.text = created_at

        Glide.with(requireContext()).load(post.profile_picture).fitCenter().into(binding.profilePhotoDetalles)
        Glide.with(requireContext()).load(post.post_image).fitCenter().into(binding.photoViewDetalles)

            viewModel.fetchSuspendComments(post.post_Id).observe(viewLifecycleOwner, Observer { result->
                when(result){
                    is Result.Loading->{
                        Log.d("Livedata","Loading...")
                        binding.progressBarDetallesPost.visibility = View.VISIBLE


                    }
                    is Result.Success->{
                        binding.progressBarDetallesPost.visibility = View.GONE
                        Log.d("Livedata_comment","${result.data}")

                        if (result.data.isEmpty()){
                            binding.emptyContainer.visibility = View.VISIBLE
                            return@Observer
                        }else{
                            binding.emptyContainer.visibility = View.GONE
                        }

                        AdapterComment = commentAdapter(result.data)
                        binding.rvPostDetalles.adapter = AdapterComment//commentAdapter(result.data)

                    }

                    is Result.Failure->{
                        Log.d("livedata error","{${result.exception}}")
                    }
                }

            })


/*
            viewModel.fechtLatestComments(post.post_Id).observe(viewLifecycleOwner, Observer { result->
                when(result){
                    is Result.Loading->{
                        Log.d("Livedata","Loading...")
                        binding.progressBarDetallesPost.visibility = View.VISIBLE


                    }
                    is Result.Success->{
                        binding.progressBarDetallesPost.visibility = View.GONE
                        Log.d("Livedata_comment","${result.data}")

                        //if (result.data.isEmpty()){
                        //    binding.emptyContainer.visibility = View.VISIBLE
                        //    return@Observer
                        //}else{
                        //    binding.emptyContainer.visibility = View.GONE
                        //}


                        AdapterComment.notifyDataSetChanged()
                        AdapterComment = commentAdapter(result.data)
                        binding.rvPostDetalles.adapter = AdapterComment//commentAdapter(result.data)

                    }

                    is Result.Failure->{
                        Log.d("livedata error","{${result.exception}}")
                    }
                }

            })
*/

        var commentPost = ""
        binding.btnComment.setOnClickListener {
            commentPost = binding.editTxtContent.text.toString()
            val keyPost = post.post_Id

            if (TextUtils.isEmpty(commentPost)){
                Toast.makeText(context,"Tienes Espacios Vacios",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.addNewComment(commentPost,keyPost)
                binding.editTxtContent.setText("")
                Toast.makeText(this.context,"Has comentado",Toast.LENGTH_SHORT).show()
            }
        }

        binding.photoViewDetalles.setOnClickListener {
            val bundle = Bundle()
            val imagepass = ImageBundle(bitmap_string = post.post_image)
            bundle.putParcelable("img_view_detalles",imagepass)
            findNavController().navigate(R.id.action_detallesPostFragment_to_imageviewFragment,bundle)
        }


    }


}