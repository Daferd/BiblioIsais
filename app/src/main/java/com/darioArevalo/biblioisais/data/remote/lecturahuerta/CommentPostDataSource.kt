package com.darioArevalo.biblioisais.data.remote.lecturahuerta

import android.util.Log
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.CommentPost
import com.darioArevalo.biblioisais.data.model.PostServer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CommentPostDataSource {
    //ingresar argumento id del post
    suspend fun  getLatestComments(keyPost:String): Result<List<CommentPost>> {
        val commentPostList = mutableListOf<CommentPost>()
        Log.d("KeyPost","::::::${keyPost}")
        val querySnapshot = FirebaseFirestore.getInstance().collection("commentsPost").get().await()
        for (comments in querySnapshot.documents){
            comments.toObject(CommentPost::class.java)?.let {
                commentPostList.add(it)
            }
        }
        return Result.Success(commentPostList)
    }
}