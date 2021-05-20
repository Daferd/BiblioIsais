package com.darioArevalo.biblioisais.domain.lecturahuerta

import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.CommentPost

interface CommentPostRepo {
    suspend fun getLatestComments(keyPost:String): Result<List<CommentPost>>
}