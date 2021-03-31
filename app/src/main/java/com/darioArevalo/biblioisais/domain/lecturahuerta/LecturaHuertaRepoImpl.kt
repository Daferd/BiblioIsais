package com.darioArevalo.biblioisais.domain.lecturahuerta

import com.darioArevalo.biblioisais.core.Resource
import com.darioArevalo.biblioisais.data.model.PostServer
import com.darioArevalo.biblioisais.data.remote.lecturahuerta.LecturaHuertaDataSource

class LecturaHuertaRepoImpl(private val dataSource: LecturaHuertaDataSource): LecturaHuertaRepo {
    override suspend fun getLatestPosts(): Resource<List<PostServer>> = dataSource.getLatesPosts()
}