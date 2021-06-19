package com.darioArevalo.biblioisais.domain.products

import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.ImageServer
import com.darioArevalo.biblioisais.data.model.PdfServer

interface ProductsRepo {
    suspend fun getPDFs():Result<List<PdfServer>>
    suspend fun getIsaisImages():Result<List<ImageServer>>
    suspend fun getGoogleImages():Result<List<ImageServer>>
}