package com.darioArevalo.biblioisais.data.remote.lecturahuerta

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.ProgressBar
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.ImageServer
import com.darioArevalo.biblioisais.data.model.PostServer
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*

class LecturaHuertaDataSource {

    suspend fun getLatesPosts(): Flow<Result<MutableList<PostServer>>> = callbackFlow{
        val postList = mutableListOf<PostServer>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("postblog")//.get().await()



        val suscription = querySnapshot.addSnapshotListener { snapshot, e ->

            /*
            if (e != null) {
                Log.w("FailedData", "Listen failed.", e)
                return@addSnapshotListener
            }*/

            if (snapshot != null ) {
                Log.d("CuerrentData", "Current data: ${snapshot.metadata.toString()}")
                postList.clear()
                for(post in snapshot.documents){
                    post.toObject(PostServer::class.java)?.let { postList.add(it) }
                    Log.d("Query Tag", "${post.id} => ${post.data}")}
                    this.trySend(Result.Success(postList)).isSuccess

            } else {
                Log.d("NUllData", "Current data: null")
            }
        }
       // delay(2000)
        Log.d("Data_post",postList.toString())
        awaitClose{suscription.remove()}



        /*querySnapshot.addSnapshotListener{snapshot, e->
            if (e!=null){
                Log.d("Error_dataFirestorage","Listen failed")
                return@addSnapshotListener
            }

            if( snapshot!=null && !snapshot.isEmpty){
                for(post in snapshot.documents){
                    post.toObject(PostServer::class.java).let {
                        postList.add(it!!)
                    }
                }

            }
            else{
                Log.d("Data_Null","Current Data Null")
            }

        }*/

       // Log.d("post_datasource",postList.toString())

        /*
        for(post in querySnapshot.documents){
            post.toObject(PostServer::class.java)?.let { postList.add(it) }
            Log.d("Query Tag", "${post.id} => ${post.data}")}*/

        //return Result.Success(postList)
    }

    fun setPost(autor:String, contenido:String, titulo:String, date: String, bitmap: Bitmap){
        val querySnapshot = FirebaseFirestore.getInstance().collection("postblog")
        uuid = UUID.randomUUID()
        downloadTask = ""

        val user = FirebaseAuth.getInstance().currentUser
        val user_id = user?.uid.toString()
        val photo_user = user?.photoUrl.toString()
        val user_name = user?.displayName.toString()


        var post_Id = ""
        val storaRef = FirebaseStorage.getInstance().reference
        val imageRef = storaRef.child("fotosPost/" + uuid.toString())
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)

        val time_server =  Timestamp.now()
        val time_created = time_server.seconds * 1000 + time_server.nanoseconds / 1000000
        Log.d("map_timestamp","${time_created.toString()}}")

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
                    "autor" to  user_name,//autor,
                    "contenido" to contenido,
                    "titulo" to titulo,
                    "created_at" to time_created,//FieldValue.serverTimestamp(),
                    "post_image" to downloadTask,
                    "post_Id" to post_Id,
                    "profile_picture" to photo_user
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

    suspend fun getIsaisImages(): Result<List<ImageServer>>{
        val imageList = mutableListOf<ImageServer>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("isaisImages").get().await()
        for(image in querySnapshot.documents){
            image.toObject(ImageServer::class.java)?.let { fbImage -> imageList.add(fbImage) }
        }
        return Result.Success(imageList)
    }
    companion object {
        private lateinit var downloadTask : String
        private lateinit var uuid: UUID
    }
}