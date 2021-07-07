package com.darioArevalo.biblioisais.data.remote.lecturahuerta

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.ProgressBar
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.PostServer
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*

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

    fun setPost(autor:String, contenido:String, titulo:String, date: String, bitmap: Bitmap){
        val querySnapshot = FirebaseFirestore.getInstance().collection("postblog")
        uuid = UUID.randomUUID()
        downloadTask = ""


        var post_Id = ""
        val storaRef = FirebaseStorage.getInstance().reference
        val imageRef = storaRef.child("fotosPost/" + uuid.toString())
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
                downloadTask = task.result.toString()
                post_Id = querySnapshot.document().id
                val postHashMap = hashMapOf(
                    "autor" to  autor,
                    "contenido" to contenido,
                    "titulo" to titulo,
                    "timestamp" to FieldValue.serverTimestamp(),
                    "post_image" to downloadTask,
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
            }else{
                Log.d("storage","fallo App ${task.isSuccessful}   ")
            }
        }


/*
         Log.d("storageUriOut","value ${downloadTask}")
         post_Id = querySnapshot.document().id
         val postHashMap = hashMapOf(
             "autor" to  autor,
             "contenido" to contenido,
             "titulo" to titulo,
             "timestamp" to FieldValue.serverTimestamp(),
             "post_image" to downloadTask,
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
             }*/

        //querySnapshot.document(post_Id).collection("comentarios_post").document()

        /*querySnapshot.add(postHashMap).addOnCompleteListener {
            if (it.isSuccessful){
                    //message for succesfull
            }else{
                    //message for failure
            }
        }*/


    }

    companion object {
        private lateinit var downloadTask : String
        private lateinit var uuid: UUID
    }



}









/*
val user = FirebaseAuth.getInstance().currentUser
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