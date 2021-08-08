package com.darioArevalo.biblioisais.data.remote.form

import android.widget.Toast
import com.darioArevalo.biblioisais.data.model.FormServer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FormDataSource {
    suspend fun setForm(username: String, email: String, date: String, numberPhone: String, organization: String, gender: String) : FirebaseFirestore? {
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            db.collection("forms").document(user.uid).set(FormServer(username,email,date,numberPhone,organization,gender)).addOnSuccessListener {

            }.addOnFailureListener{ error->

            }
            db.collection("users").document(user.uid)
                    .update("form",true)
                    .addOnSuccessListener {  }.addOnFailureListener {  }
        }
        return db
    }
}
