package com.darioArevalo.biblioisais.domain.lecturahuerta

import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.CommentPost
import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.flow.Flow

interface CommentPostRepo {
    suspend fun suspend_get_comments(keyPost:String):Result<List<CommentPost>>
    suspend fun getComments(keyPost: String):Flow<Result<List<CommentPost>>>
    fun addNewComment(content:String,keyPost: String)
    fun edit_comment_repo(content: String,key_id_comment:String,keyPost: String)
    fun delete_comment(key_id_comment:String,keyPost: String)
}