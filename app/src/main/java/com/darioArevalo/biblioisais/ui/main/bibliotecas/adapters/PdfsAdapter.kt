package com.darioArevalo.biblioisais.ui.main.bibliotecas.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.darioArevalo.biblioisais.core.BaseViewHolder
import com.darioArevalo.biblioisais.data.model.PdfServer
import com.darioArevalo.biblioisais.databinding.PdfItemBinding

class PdfsAdapter(
        private val pdfsList: List<PdfServer>,
        private val itemClickListener:OnPdfClickListener
):RecyclerView.Adapter<BaseViewHolder<*>> (){
    interface OnPdfClickListener{
        fun onPdfClick(pdf: PdfServer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = PdfItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val holder = PdfsViewHolder(itemBinding,parent.context)

        itemBinding.root.setOnClickListener {
            val position = holder.absoluteAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onPdfClick(pdfsList[position])
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is PdfsViewHolder -> holder.bind(pdfsList[position])
        }
    }

    override fun getItemCount(): Int = pdfsList.size

    private inner class PdfsViewHolder(
            val binding: PdfItemBinding,
            val context: Context
    ): BaseViewHolder<PdfServer>(binding.root){
        override fun bind(item: PdfServer) {
            binding.nombreBibliotecaTextView.text = item.name
            binding.urlTextView.text = item.pdfUrl
        }
    }
}