package com.darioArevalo.biblioisais.ui.main.biblioisais

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.databinding.CoursesItemBinding
import com.darioArevalo.biblioisais.data.model.CourseServer
import com.squareup.picasso.Picasso

class CursosRVAdapter (
        private val cursosList: ArrayList<CourseServer>,
        private val onItemClickListener: OnItemClickListener
        ) : RecyclerView.Adapter<CursosRVAdapter.CursoViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoViewHolder {
        val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.courses_item,parent,false)
        return CursoViewHolder(itemView,onItemClickListener)
    }

    override fun onBindViewHolder(holder: CursoViewHolder, position: Int) {
        val curso = cursosList[position]
        holder.bindCurso(curso)
    }

    override fun getItemCount(): Int {
        return cursosList.size
    }

    class CursoViewHolder(
            itemView: View,
            private val onItemClickListener: OnItemClickListener
    ):RecyclerView.ViewHolder(itemView){
        val binding = CoursesItemBinding.bind(itemView)
        fun bindCurso(course: CourseServer){
            binding.nombreCapituloTextView.text=course.name
            binding.autorCursoTextView.text=course.author
            //binding.urlTextView.text=course.courseUrl
            Picasso.get().load(course.courseUrl).into(binding.cursoImageView)

            binding.itemCursosCardView.setOnClickListener {
                onItemClickListener.onItemClik(course)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClik(biblioteca: CourseServer)
    }

}
