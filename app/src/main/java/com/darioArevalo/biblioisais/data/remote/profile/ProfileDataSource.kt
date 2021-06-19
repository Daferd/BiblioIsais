package com.darioArevalo.biblioisais.data.remote.profile

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.UserServer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*

class ProfileDataSource {

    suspend fun getUser(): Result<UserServer> {
        var userData = UserServer("","","",false)
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val db = FirebaseFirestore.getInstance()
            val data = db.collection("users").document(user.uid).get().await()
            userData = data.toObject(UserServer::class.java) as UserServer
        }
        return Result.Success(userData)
    }

    suspend fun uploadPicture(imageBitmap:Bitmap){
        val user = FirebaseAuth.getInstance().currentUser
        val imageRef = FirebaseStorage.getInstance().reference.child("profilePictures/${user?.uid}.jpg")
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val downloadUrl = imageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await().toString()
        val profileUpdates = UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse(downloadUrl)).build()

        user?.updateProfile(profileUpdates)?.await()
        FirebaseFirestore.getInstance().collection("users").document("${user?.uid}").update(mapOf("photo_url" to downloadUrl))

    }

    suspend fun uploadUsername(username: String){
        val user = FirebaseAuth.getInstance().currentUser
        val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(username).build()
        user?.updateProfile(profileUpdates)?.await()
        FirebaseFirestore.getInstance().collection("users").document("${user?.uid}").update(mapOf("username" to username))
    }

    suspend fun uploadEmail(email: String){
        val user = FirebaseAuth.getInstance().currentUser
        user?.updateEmail(email)?.await()
        FirebaseFirestore.getInstance().collection("users").document("${user?.uid}").update(mapOf("email" to email))
    }

    suspend fun uploadPassword(password: String){
        val user = FirebaseAuth.getInstance().currentUser
        user?.updatePassword(password)?.await()
    }

}