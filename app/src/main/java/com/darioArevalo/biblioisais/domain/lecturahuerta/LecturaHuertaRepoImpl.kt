package com.darioArevalo.biblioisais.domain.lecturahuerta

import android.graphics.Bitmap
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.ImageServer
import com.darioArevalo.biblioisais.data.model.PostServer
import com.darioArevalo.biblioisais.data.remote.lecturahuerta.LecturaHuertaDataSource

class LecturaHuertaRepoImpl(private val dataSource: LecturaHuertaDataSource): LecturaHuertaRepo {
    override suspend fun getLatestPosts(): Result<List<PostServer>> = dataSource.getLatesPosts()
    override suspend fun getIsaisImages(): Result<List<ImageServer>> = dataSource.getIsaisImages()
    override fun setPost(autor: String, contenido: String, titulo: String,date:String,bitmap: Bitmap) = dataSource.setPost(autor,contenido,titulo,date,bitmap)
}