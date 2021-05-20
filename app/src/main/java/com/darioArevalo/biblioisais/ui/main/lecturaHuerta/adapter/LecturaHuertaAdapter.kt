package com.darioArevalo.biblioisais.ui.main.lecturaHuerta.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.darioArevalo.biblioisais.core.BaseViewHolder
import com.darioArevalo.biblioisais.data.model.PostServer
import com.darioArevalo.biblioisais.databinding.LecturaHuertaRowBinding
import com.darioArevalo.biblioisais.databinding.PostItemBinding

class LecturaHuertaAdapter(private val postList:List<PostServer>, private val itemOnClickListener:OnPostClickListener):RecyclerView.Adapter<BaseViewHolder<*>>() {


    interface OnPostClickListener{
        fun onPostClick(Post:PostServer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = LecturaHuertaRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LecturaHuertaViewHolder(itemBinding,parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is LecturaHuertaViewHolder -> holder.bind(postList[position])
        }
    }

    override fun getItemCount(): Int = postList.size

    private inner class LecturaHuertaViewHolder(
        val binding: LecturaHuertaRowBinding,
        //itemView: View,
        //val binding: PostItemBinding,
        val context: Context
    ): BaseViewHolder<PostServer>(binding.root){
        override fun bind(item: PostServer) {
            Glide.with(context).load(item.profile_picture).centerCrop().into(binding.imgPost)
            binding.txtTitulo.text = item.titulo
            binding.txtDescripcion.text = item.contenido
            binding.root.setOnClickListener{itemOnClickListener.onPostClick(item)}

            //Glide.with(context).load(item.post_image).centerCrop().into(binding.postImage)
            //Glide.with(context).load(item.profile_picture).centerCrop().into(binding.profilePicture)
            //binding.profileName.text=item.profile_name
            //binding.postTimestamp.text="Hace 2 horas"
        }
    }
}