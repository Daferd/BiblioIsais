package com.darioArevalo.biblioisais.data.remote.auth

import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.darioArevalo.biblioisais.data.model.UserServer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthDataSource {
    suspend fun singIn(email: String, password: String): FirebaseUser?{
        val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).await()
        return authResult.user
    }

    suspend fun singUp(email: String, password: String, username: String, form: Boolean): FirebaseUser? {
        val authResult = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).await()
        authResult.user?.let { user ->

            user.sendEmailVerification()

            val profileDates = UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/biblioisais.appspot.com/o/defaultPhoto.png?alt=media&token=647dbd4d-d667-43c1-99f9-bd58248a10d4"))
                .build()

            user.updateProfile(profileDates).await()
            FirebaseFirestore.getInstance().collection("users").document(user.uid).set(
                    UserServer(
                            email,
                            username,
                            "https://firebasestorage.googleapis.com/v0/b/biblioisais.appspot.com/o/defaultPhoto.png?alt=media&token=647dbd4d-d667-43c1-99f9-bd58248a10d4"
                    )
            )
        }

        return authResult.user
    }

    suspend fun recoverPassword(email: String){

        val auth = FirebaseAuth.getInstance()

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener{task->
                if(task.isSuccessful){
                    Log.d("correo","enviado")
                }

            }
    }

}