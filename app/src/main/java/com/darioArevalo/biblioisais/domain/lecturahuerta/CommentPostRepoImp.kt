package com.darioArevalo.biblioisais.domain.lecturahuerta

import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.CommentPost
import com.darioArevalo.biblioisais.data.remote.lecturahuerta.CommentPostDataSource

class CommentPostRepoImp(private val dataSource: CommentPostDataSource): CommentPostRepo {

    override suspend fun suspend_get_comments(keyPost:String): Result<List<CommentPost>> = dataSource.suspend_get_comments(keyPost)
    override fun getLatestComments(keyPost:String): Result<List<CommentPost>> = dataSource.getLatestComments(keyPost)
    override fun addNewComment(content:String,keyPost: String) = dataSource.addNewComment(content,keyPost)
}