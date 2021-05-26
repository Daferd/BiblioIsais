package com.darioArevalo.biblioisais.domain.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

interface AuthRepo {
    suspend fun singIn(email: String, password: String): FirebaseUser?
    suspend fun singUp(email: String, password: String, username: String, form: Boolean): FirebaseUser?
}