package com.darioArevalo.biblioisais.domain.lecturahuerta

import android.graphics.Bitmap
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.PostServer

interface LecturaHuertaRepo {
    suspend fun getLatestPosts(): Result<List<PostServer>>
    fun setPost(autor:String,contenido:String,titulo:String,date:String,bitmap: Bitmap)
}