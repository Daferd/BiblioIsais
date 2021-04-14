package com.darioArevalo.biblioisais.domain.products

import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.ImageServer

interface ProductsRepo {
    suspend fun getIsaisImages():Result<List<ImageServer>>
    suspend fun getGoogleImages():Result<List<ImageServer>>
}