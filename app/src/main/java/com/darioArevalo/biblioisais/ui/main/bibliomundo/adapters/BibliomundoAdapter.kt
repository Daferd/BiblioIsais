package com.darioArevalo.biblioisais.ui.main.bibliomundo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.darioArevalo.biblioisais.core.BaseViewHolder
import com.darioArevalo.biblioisais.data.model.LibraryServer
import com.darioArevalo.biblioisais.databinding.LibraryItemBinding

class BibliomundoAdapter(
        private val internationalList:List<LibraryServer>,
        private val itemClickListener: BibliomundoAdapter.OnLibraryClickListener
        ): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnLibraryClickListener{
        fun onLibraryClick(library:LibraryServer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = LibraryItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        val holder = InternationalViewHolder(itemBinding,parent.context)
        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onLibraryClick(internationalList[position])
        }
        return  holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is InternationalViewHolder -> holder.bind(internationalList[position])
        }
    }

    override fun getItemCount(): Int = internationalList.size

    private inner class InternationalViewHolder(
            val binding: LibraryItemBinding,
            val context: Context
    ):BaseViewHolder<LibraryServer>(binding.root){
        override fun bind(item: LibraryServer) {
            binding.nombreBibliotecaTextView.text=item.name
            binding.paisBibliotecaTextView.text = item.country
            Glide.with(context).load(item.imageUrl).centerCrop().into(binding.bibliotecaImageView)
        }
    }

}