package com.darioArevalo.biblioisais.ui.main.lecturaHuerta.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.darioArevalo.biblioisais.core.BaseViewHolder
import com.darioArevalo.biblioisais.data.model.PostServer
import com.darioArevalo.biblioisais.data.model.TimeUtils
import com.darioArevalo.biblioisais.databinding.LecturaHuertaRowBinding
import com.darioArevalo.biblioisais.databinding.PostItemBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

//List<PostServer>
class LecturaHuertaAdapter(private val postList:ArrayList<PostServer>, private val itemOnClickListener:OnPostClickListener):RecyclerView.Adapter<BaseViewHolder<*>>(),Filterable {

    private val main_list = postList
    private val searchList = ArrayList<PostServer>(postList)

    interface OnPostClickListener{
        fun onPostClick(Post:PostServer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = LecturaHuertaRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LecturaHuertaViewHolder(itemBinding,parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is LecturaHuertaViewHolder -> {

                holder.bind(postList[position])
            }
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



            Glide.with(context).load(item.post_image).centerCrop().into(binding.imgPost)
            binding.txtTitulo.text = item.titulo
            binding.txtDescripcion.text = item.contenido

            binding.root.setOnClickListener{itemOnClickListener.onPostClick(item)}

            //Glide.with(context).load(item.post_image).centerCrop().into(binding.postImage)
            //Glide.with(context).load(item.profile_picture).centerCrop().into(binding.profilePicture)
            //binding.profileName.text=item.profile_name
            //binding.postTimestamp.text="Hace 2 horas"
        }
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<PostServer>()


                    if (constraint.isNullOrBlank() or constraint.isNullOrEmpty()){
                        filteredList.addAll(searchList)
                    }else{
                        val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim()
                        searchList.forEach{
                            if (it.autor.toLowerCase(Locale.ROOT).contains(filterPattern) || it.titulo.toLowerCase(
                                    Locale.ROOT).contains(filterPattern)){
                                filteredList.add(it)

                            }
                        }
                    }
                val result = FilterResults()
                result.values = filteredList
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                main_list.clear()
                main_list.addAll(results!!.values as List<PostServer>)
                notifyDataSetChanged()
            }

        }
    }


}