package com.darioArevalo.biblioisais.domain.form

import com.google.firebase.firestore.FirebaseFirestore

interface FormRepo {
    suspend fun sendForm(username: String, email: String, date: String, numberPhone: String, organization: String, gender:String): FirebaseFirestore?
}