package com.darioArevalo.biblioisais.domain.lecturahuerta

import android.graphics.Bitmap
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.ImageServer
import com.darioArevalo.biblioisais.data.model.PostServer
import kotlinx.coroutines.flow.Flow

interface LecturaHuertaRepo {
    suspend fun getIsaisImages():Result<List<ImageServer>>
    suspend fun getLatestPosts(): Flow<Result<MutableList<PostServer>>>
    fun setPost(autor:String,contenido:String,titulo:String,date:String,bitmap: Bitmap)
}