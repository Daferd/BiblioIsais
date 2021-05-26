package com.darioArevalo.biblioisais.data.remote.lecturahuerta

import android.os.Message
import android.util.Log
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.CommentPost
import com.darioArevalo.biblioisais.data.model.PostServer
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CommentPostDataSource {
    //ingresar argumento id del post
    suspend fun  getLatestComments(keyPost:String): Result<List<CommentPost>> {
        val commentPostList = mutableListOf<CommentPost>()
        Log.d("KeyPost","::::::${keyPost}")
        val querySnapshot = FirebaseFirestore.getInstance().collection("comentarios_post").get().await()
        for (comments in querySnapshot.documents){
            comments.toObject(CommentPost::class.java)?.let {
                commentPostList.add(it)
                Log.d("comments","${it.content}")
            }
        }
        return Result.Success(commentPostList)
    }

    fun addNewComment(content:String,keyPost: String) {
        val path = "commentPost/$keyPost"

        var querySnapshot = FirebaseFirestore.getInstance().collection("postblog")
            .document("$keyPost").collection("comentarios_post").document()
        val commentHashMap = hashMapOf(
            "content" to content,
            "post_Id" to keyPost,
            "autor" to "autor x",
            "timestamp" to FieldValue.serverTimestamp()
        )


        querySnapshot.set(commentHashMap).addOnCompleteListener {
            if (it.isSuccessful){

            }else{

            }
        }


        val database = Firebase.database.reference
        //val databaseReference = database.child("comentarios_post").push().key
        //Log.d("keydatabase","${databaseReference}")
        val comment = CommentPost(content,"bool","null","autor x",keyPost)
        database.child("comentarios_post").child(keyPost).setValue(comment)
       /* querySnapshot.document(keyPost)
            .set(commentHashMap)
            .addOnCompleteListener {
                if (it.isSuccessful) {

                } else {

                }

            }*/

    }
}