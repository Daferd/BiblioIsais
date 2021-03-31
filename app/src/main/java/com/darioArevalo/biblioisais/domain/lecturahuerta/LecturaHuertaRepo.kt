package com.darioArevalo.biblioisais.domain.lecturahuerta

import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.PostServer

interface LecturaHuertaRepo {
    suspend fun getLatestPosts(): Result<List<PostServer>>
}