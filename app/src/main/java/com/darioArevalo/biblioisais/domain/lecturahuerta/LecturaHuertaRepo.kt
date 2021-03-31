package com.darioArevalo.biblioisais.domain.lecturahuerta

import com.darioArevalo.biblioisais.core.Resource
import com.darioArevalo.biblioisais.data.model.PostServer

interface LecturaHuertaRepo {
    suspend fun getLatestPosts(): Resource<List<PostServer>>
}