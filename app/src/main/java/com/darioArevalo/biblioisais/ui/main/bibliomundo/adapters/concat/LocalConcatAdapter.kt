package com.darioArevalo.biblioisais.ui.main.bibliomundo.adapters.concat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darioArevalo.biblioisais.core.BaseConcatHolder
import com.darioArevalo.biblioisais.databinding.LocalLibrariesRowBinding
import com.darioArevalo.biblioisais.ui.main.bibliomundo.adapters.LibrariesAdapter

class LocalConcatAdapter(private val librariesAdapter: LibrariesAdapter): RecyclerView.Adapter<BaseConcatHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itemBinding = LocalLibrariesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConcatViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when(holder){
            is ConcatViewHolder -> holder.bind(librariesAdapter)
        }
    }

    override fun getItemCount(): Int = 1

    private inner class ConcatViewHolder(val binding: LocalLibrariesRowBinding): BaseConcatHolder<LibrariesAdapter>(binding.root){
        override fun bind(adapter: LibrariesAdapter) {
            binding.localLibrariesRecyclerView.adapter = adapter
        }
    }
}