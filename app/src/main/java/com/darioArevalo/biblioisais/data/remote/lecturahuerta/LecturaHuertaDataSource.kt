package com.darioArevalo.biblioisais.data.remote.lecturahuerta

import android.util.Log
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.PostServer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LecturaHuertaDataSource {
    suspend fun getLatesPosts(): Result<List<PostServer>> {
        val postList = mutableListOf<PostServer>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("postblog").get().await()

        for(post in querySnapshot.documents){
            post.toObject(PostServer::class.java)?.let { postList.add(it) }
            Log.d("Query Tag", "${post.id} => ${post.data}")
        }
        return Result.Success(postList)
    }
}