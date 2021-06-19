package com.darioArevalo.biblioisais.data.remote.lecturahuerta

import android.os.Message
import android.util.Log
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.CommentPost
import com.darioArevalo.biblioisais.data.model.PostServer
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import okhttp3.Response

class CommentPostDataSource {
    //private lateinit var database: DatabaseReference

    //ingresar argumento id del post
    suspend fun  getLatestComments(keyPost:String): Result<List<CommentPost>> {
        val commentPostList = mutableListOf<CommentPost>()
        //Log.d("KeyPost","::::::${keyPost}")
        //val querySnapshot = FirebaseFirestore.getInstance().collection("comentarios_post").get().await()
        //for (comments in querySnapshot.documents){
        //    comments.toObject(CommentPost::class.java)?.let {
        //        commentPostList.add(it)
        //        Log.d("comments","${it.content}")
         //   }
        //}
        //return Result.Success(commentPostList)


        val database = FirebaseDatabase.getInstance().reference
        val databaseReference = database.child("comentarios_post/${keyPost}").get().await()

        for (comments in databaseReference.children){
           comments.getValue<CommentPost>().let {
               commentPostList.add(it!!)
            }

        }


        /*

        databaseReference.get().addOnCompleteListener { task->
            if (task.isSuccessful){
                val result = task.result

                result.let {
                    result?.children?.forEach {snapShot->
                        val post = snapShot.getValue<CommentPost>()
                        commentPostList.add(post!!)


                    }
                }

            } else{
                Log.d("Error_database_realtime", task.exception.toString())
            }

        }*/

        return Result.Success(commentPostList)


    }

    fun addNewComment(content:String,keyPost: String) {
        /*val path = "commentPost/$keyPost"

        var querySnapshot = FirebaseFirestore.getInstance().collection("postblog")
            .document(keyPost).collection("comentarios_post").document()
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
        }*/


        //val database = Firebase.database.reference
        val database = FirebaseDatabase.getInstance().reference
        val key_random = database.push().key.toString()
        Log.d("key_random", key_random)
        Log.d("key_post",keyPost)
        val comment = CommentPost(content,"bool","null","autor x",keyPost)
        database.child("comentarios_post").child(keyPost).child(key_random).setValue(comment)



    }
}