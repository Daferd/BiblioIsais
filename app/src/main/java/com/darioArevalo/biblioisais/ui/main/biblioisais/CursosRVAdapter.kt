package com.darioArevalo.biblioisais.ui.main.biblioisais

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.databinding.CursosItemBinding
import com.darioArevalo.biblioisais.data.model.CursoServer
import com.squareup.picasso.Picasso

class CursosRVAdapter (
        private val cursosList: ArrayList<CursoServer>,
        private val onItemClickListener: OnItemClickListener
        ) : RecyclerView.Adapter<CursosRVAdapter.CursoViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursoViewHolder {
        val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.cursos_item,parent,false)
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
        val binding = CursosItemBinding.bind(itemView)
        fun bindCurso(curso: CursoServer){
            binding.nombreCapituloTextView.text=curso.nombre
            binding.autorCursoTextView.text=curso.autor
            binding.urlTextView.text=curso.url
            Picasso.get().load(curso.imagen).into(binding.cursoImageView)

            binding.itemCursosCardView.setOnClickListener {
                onItemClickListener.onItemClik(curso)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClik(biblioteca: CursoServer)
    }

}
