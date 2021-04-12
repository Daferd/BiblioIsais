package com.darioArevalo.biblioisais.ui.main.bibliomundo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.data.model.LibraryServer
import com.darioArevalo.biblioisais.databinding.LibraryItemBinding
import com.squareup.picasso.Picasso

class BibliotecaRVAdapter(
    private var bibliotecasList:ArrayList<LibraryServer>,
    private val onItemClickListener: OnItemClickListener
        ) : RecyclerView.Adapter<BibliotecaRVAdapter.BibliotecaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BibliotecaViewHolder {
        val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.library_item,parent,false)
        return BibliotecaViewHolder(itemView,onItemClickListener)

    }

    override fun onBindViewHolder(holder: BibliotecaViewHolder, position: Int) {
        val biblioteca = bibliotecasList[position]
        holder.bindBiblioteca(biblioteca)
    }

    override fun getItemCount(): Int {
        return bibliotecasList.size
    }

    class BibliotecaViewHolder(
            itemView: View,
            private val onItemClickListener: OnItemClickListener
    ):RecyclerView.ViewHolder(itemView){
        private val binding = LibraryItemBinding.bind(itemView)

        fun bindBiblioteca(library: LibraryServer){
            binding.nombreBibliotecaTextView.text=library.name
            binding.paisBibliotecaTextView.text = library.country
            binding.urlTextView.text = library.pageUrl
            Picasso.get().load(library.imageUrl).into(binding.bibliotecaImageView)

            binding.itemBibliotecaCardView.setOnClickListener {
                onItemClickListener.onItemClick(library)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(library: LibraryServer)
    }
}