package com.darioArevalo.biblioisais.data.remote.lecturahuerta

import android.net.sip.SipSession
import android.util.Log
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.CommentPost
import com.darioArevalo.biblioisais.ui.main.lecturaHuerta.adapter.commentAdapter
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.*

class CommentPostDataSource {

    fun  getLatestComments(keyPost:String): Result<List<CommentPost>> {
        val commentPostList = mutableListOf<CommentPost>()
        val database = FirebaseDatabase.getInstance().reference
        database.keepSynced(true)

        val databaseReference = database.child("comentarios_post/${keyPost}")//.get().await()
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                commentPostList.clear()
                for(comments in snapshot.children){
                    comments.getValue<CommentPost>().let {
                        commentPostList.add(it!!)
                        Log.d("ValueEvent_res",it.content)
                }
             } // End if
            } // End Function
            override fun onCancelled(error: DatabaseError) {
                Log.d("Error",error.toString())
            }
        })
/*
            for (comments in databaseReference.children){
           comments.getValue<CommentPost>().let {
               //Log.d("xxx","${it?.user_Id}")
               //it?.photo_url_user = getPhoto(it?.user_Id.toString())
               commentPostList.add(it!!)
            }

        }*/
        Log.d("size_list",commentPostList.size.toString())


        return Result.Success(commentPostList)
    }
    
   suspend fun suspend_get_comments(keyPost: String):Result<List<CommentPost>>{
       val commentPostList = mutableListOf<CommentPost>()
       val database = FirebaseDatabase.getInstance().reference
       database.keepSynced(true)

       val databaseReference = database.child("comentarios_post/${keyPost}").get().await()
       for (comments in databaseReference.children){
           comments.getValue<CommentPost>().let {
               commentPostList.add(it!!)
           }
       }
       Log.d("size_list",commentPostList.size.toString())
       return Result.Success(commentPostList)
   }

    private fun getPhoto(userId: String): String {
        val db = FirebaseFirestore.getInstance()
        var photo_url = ""
        val user = db.collection("users").document(userId).get().addOnSuccessListener { document ->
            photo_url = document.data?.get("photo_url").toString()
            Log.d("photo123","sus $photo_url")
            return@addOnSuccessListener
        }
        Log.d("photo123","$user.")
        return ""
    }

    fun addNewComment(content:String,keyPost: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val user_id = user?.uid.toString()
        val photo_user = user?.photoUrl.toString()
        val user_name = user?.displayName.toString()
        //val create_at = FieldValue.serverTimestamp()

        Log.d("comment_namexxx","${user?.displayName}")
        val database = FirebaseDatabase.getInstance().reference        
        database.keepSynced(true)
        val key_random = database.push().key.toString()
        Log.d("key_random", key_random)
        Log.d("key_post",keyPost)
        Log.d("user_name_datasource",user_name)
        val time_server =  Timestamp.now()
        val time_created = time_server.seconds * 1000 + time_server.nanoseconds / 1000000
        Log.d("map_timestamp","${time_created.toString()}}")

        //val comment = CommentPost(content = content,create_at = FieldValue.serverTimestamp(),autor = User_Id,post_Id = keyPost)
        database.child("comentarios_post").child(keyPost).child(key_random).setValue(
            CommentPost(
                content = content,
                create_at = time_created, //'ServerValue.TIMESTAMP.toString()',
                autor = user_name,
                post_Id =  keyPost,
                user_Id = user_id,
                photo_url_user = photo_user
            )
        )
    }
}