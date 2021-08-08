package com.darioArevalo.biblioisais.ui.main.biblioisais.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.darioArevalo.biblioisais.core.BaseViewHolder
import com.darioArevalo.biblioisais.core.show
import com.darioArevalo.biblioisais.data.model.CourseServer
import com.darioArevalo.biblioisais.databinding.CoursesItemBinding

class BiblioisaisAdapter(
        private val episodesList: List<CourseServer>,
        private val itemClickListener: BiblioisaisAdapter.OnEpisodesClickListener
        ):RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnEpisodesClickListener{
        fun onEpisodesClick(episode:CourseServer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = CoursesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val holder = CoursesViewHolder(itemBinding,parent.context)
        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onEpisodesClick(episodesList[position])
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is CoursesViewHolder -> holder.bind(episodesList[position])
        }
    }

    override fun getItemCount(): Int = episodesList.size

    private inner class CoursesViewHolder(
            val binding: CoursesItemBinding,
            val context: Context
    ):BaseViewHolder<CourseServer>(binding.root){
        override fun bind(item: CourseServer) {
            binding.nombreCapituloTextView.text = item.name
            binding.autorCursoTextView.text = item.author
            Glide.with(context).load(item.courseImage).centerCrop().into(binding.cursoImageView)
        }
    }
}