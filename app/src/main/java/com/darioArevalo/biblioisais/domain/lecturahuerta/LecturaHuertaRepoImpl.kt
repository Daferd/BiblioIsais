package com.darioArevalo.biblioisais.domain.lecturahuerta

import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.PostServer
import com.darioArevalo.biblioisais.data.remote.lecturahuerta.LecturaHuertaDataSource

class LecturaHuertaRepoImpl(private val dataSource: LecturaHuertaDataSource): LecturaHuertaRepo {
    override suspend fun getLatestPosts(): Result<List<PostServer>> = dataSource.getLatesPosts()
}