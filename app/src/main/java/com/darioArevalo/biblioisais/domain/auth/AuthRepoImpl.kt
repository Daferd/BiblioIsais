package com.darioArevalo.biblioisais.domain.auth

import com.darioArevalo.biblioisais.data.remote.auth.AuthDataSource
import com.google.firebase.auth.FirebaseUser

class AuthRepoImpl(private val dataSource:AuthDataSource):AuthRepo {
    override suspend fun singIn(email: String, password: String): FirebaseUser? =
        dataSource.singIn(email,password)
    override suspend fun singUp(email: String, password: String, username: String): FirebaseUser? =
        dataSource.singUp(email,password,username)
}