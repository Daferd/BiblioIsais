package com.darioArevalo.biblioisais.ui.main.lecturaHuerta.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.darioArevalo.biblioisais.core.BaseViewHolder
import com.darioArevalo.biblioisais.data.model.CommentPost
import com.darioArevalo.biblioisais.data.model.TimeUtils
import com.darioArevalo.biblioisais.databinding.CommentRowBinding
import kotlin.collections.ArrayList

class commentAdapter(private val commentPostList: List<CommentPost>, private val itemOnClickListener:onCommentClickListener):RecyclerView.Adapter<BaseViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = CommentRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CommentPostViewHolder(itemBinding,parent.context)
    }

    interface onCommentClickListener{
        fun onCommentClick(commentEditText: Array<String>)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is CommentPostViewHolder -> holder.bind(commentPostList[position])
        }
    }

    override fun getItemCount(): Int = commentPostList.size



    private inner class CommentPostViewHolder(
        val binding: CommentRowBinding,
        val context: Context): BaseViewHolder<CommentPost>(binding.root){

        override fun bind(item: CommentPost) {

           /* val created_at = item.create_at?.time?.div(1000L)?.let {
                TimeUtils.timeAgo(it.toInt())
            }*/
            val array_data_comment = arrayOf(item.content,item.key_id_comment,item.post_Id)
            val created_at = item.create_at?.let {
                TimeUtils.timeAgo(it)
            }
            binding.commentDate.text = created_at
            binding.commentUsername.text = item.autor
            binding.commentContent.text = item.content

            //validation for edit comment
            binding.editCommentRvpost.isVisible = item.validation

            //Dialog
            binding.editCommentRvpost.setOnClickListener{ itemOnClickListener.onCommentClick(array_data_comment)}
            Glide.with(context).load(item.photo_url_user).centerCrop().into(binding.commentUserImg)
        }

    }





}