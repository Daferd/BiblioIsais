package com.darioArevalo.biblioisais.domain.form

import com.darioArevalo.biblioisais.data.remote.form.FormDataSource
import com.google.firebase.firestore.FirebaseFirestore

class FormRepoImpl(private val dataSource: FormDataSource): FormRepo {
    override suspend fun sendForm(
        username: String,
        email: String,
        date: String,
        numberPhone: String,
        organization: String,
        gender: String
    ): FirebaseFirestore? = dataSource.setForm(username,email,date,numberPhone,organization,gender)
}