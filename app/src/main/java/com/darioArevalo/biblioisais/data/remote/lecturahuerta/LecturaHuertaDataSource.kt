package com.darioArevalo.biblioisais.data.remote.lecturahuerta

import android.graphics.Bitmap
import android.util.Log
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.PostServer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*

class LecturaHuertaDataSource {
    //private lateinit var downloadUrlPhoto:String
    suspend fun getLatesPosts(): Result<List<PostServer>> {
        val postList = mutableListOf<PostServer>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("postblog").get().await()

        for(post in querySnapshot.documents){
            post.toObject(PostServer::class.java)?.let { postList.add(it) }
            Log.d("Query Tag", "${post.id} => ${post.data}")
        }
        return Result.Success(postList)
    }

     fun setPost(autor:String, contenido:String, titulo:String, date: String, bitmap: Bitmap){
         var downloadUrlPhoto = ""
         var post_Id = ""
         val storaRef = FirebaseStorage.getInstance().reference
         val imageRef = storaRef.child("fotosPost/image.jpg")
         val baos = ByteArrayOutputStream()
         bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
         val data = baos.toByteArray()
         val uploadTask = imageRef.putBytes(data)

         uploadTask.continueWithTask{ task->
            if (!task.isSuccessful){
                task.exception?.let { exception->
                    throw exception
                }

            }
            imageRef.downloadUrl

         }.addOnCompleteListener {task->
            if (task.isSuccessful){
                val downloadUrl = task.result.toString()
                Log.d("storage","value ${downloadUrl}")
            }else{
                Log.d("storage","fallo App ${task.isSuccessful}   ")
            }
         }


         var querySnapshot = FirebaseFirestore.getInstance().collection("postblog")
         post_Id = querySnapshot.document().id
         val postHashMap = hashMapOf(
             "autor" to  autor,
             "contenido" to contenido,
             "titulo" to titulo,
             "timestamp" to FieldValue.serverTimestamp(),
             "post_image" to downloadUrlPhoto,
             "post_Id" to post_Id
         )
         querySnapshot.document(post_Id)
             .set(postHashMap)
             .addOnCompleteListener{
                 if (it.isSuccessful){
                     //message for succesfull
                 }else{
                     //message for failure
                 }

             }
         /*querySnapshot.add(postHashMap).addOnCompleteListener {

             if (it.isSuccessful){
                     //message for succesfull
             }else{
                     //message for failure
             }
         }*/


     }



    }










/*
* val user = FirebaseAuth.getInstance().currentUser
        var userID = ""
        user?.let {
            userID = user.uid
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(userID).get().await()
        }
*
*
*
* */