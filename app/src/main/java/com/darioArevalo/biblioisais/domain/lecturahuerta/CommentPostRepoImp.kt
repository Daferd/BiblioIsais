package com.darioArevalo.biblioisais.domain.lecturahuerta

import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.CommentPost
import com.darioArevalo.biblioisais.data.remote.lecturahuerta.CommentPostDataSource
import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.flow.Flow


class CommentPostRepoImp(private val dataSource: CommentPostDataSource): CommentPostRepo {

    override suspend fun suspend_get_comments(keyPost:String): Result<List<CommentPost>> = dataSource.suspend_get_comments(keyPost)
    override suspend fun getComments(keyPost: String): Flow<Result<List<CommentPost>>> = dataSource.getComments(keyPost)

    override fun addNewComment(content:String,keyPost: String) = dataSource.addNewComment(content,keyPost)
    override fun edit_comment_repo(content: String,key_id_comment:String,keyPost: String) = dataSource.fun_edit_comment(content,key_id_comment, keyPost)
    override fun delete_comment(key_id_comment:String,keyPost: String) = dataSource.delete_comment(key_id_comment,keyPost)


}