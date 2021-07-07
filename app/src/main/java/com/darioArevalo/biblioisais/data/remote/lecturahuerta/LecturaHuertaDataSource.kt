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
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.properties.Delegates

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
         val user = FirebaseAuth.getInstance().currentUser
         uuid = UUID.randomUUID()
         downloadTask = ""


         var User_Id = user?.uid.toString()
         val user_name = user?.displayName.toString()
         val storaRef = FirebaseStorage.getInstance().reference
         val imageRef = storaRef.child("fotosPost/" + uuid.toString())
         val baos = ByteArrayOutputStream()
         bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
         val data = baos.toByteArray()
         val uploadTask = imageRef.putBytes(data)

        val time_server =  Timestamp.now()
        val time_created = time_server.seconds * 1000 + time_server.nanoseconds / 1000000


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
                 val post_Id = querySnapshot.document().id
                 val postHashMap = hashMapOf(
                     "autor" to  user_name,
                     "contenido" to contenido,
                     "titulo" to titulo,
                     "timestamp" to FieldValue.serverTimestamp(),
                     "post_image" to downloadTask,
                     "post_Id" to post_Id,
                     "User_Id" to User_Id,
                     "created_at" to time_created
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




     }

    companion object {
        private lateinit var downloadTask : String
        private lateinit var uuid: UUID
    }

    }











