package com.darioArevalo.biblioisais.domain.products

import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.ImageServer
import com.darioArevalo.biblioisais.data.remote.products.ProductsDataSource

class ProductsRepoImpl(private val dataSource: ProductsDataSource):ProductsRepo {
    override suspend fun getIsaisImages(): Result<List<ImageServer>> = dataSource.getIsaisImages()
    override suspend fun getGoogleImages(): Result<List<ImageServer>> =dataSource.getImageGoogle()
}