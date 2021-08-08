package com.darioArevalo.biblioisais.domain.biblioteca_isais

import android.content.Context
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.ImageServer
import com.darioArevalo.biblioisais.data.model.PdfServer

interface BibliotecaIsaisRepo {
    suspend fun getPDFs():Result<List<PdfServer>>
    suspend fun getIsaisImages():Result<List<ImageServer>>
    suspend fun getGoogleImages():Result<List<ImageServer>>
    fun downloadPDF(urlString: String,context: Context)
}