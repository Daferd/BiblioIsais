package com.darioArevalo.biblioisais.domain.auth

import com.darioArevalo.biblioisais.data.remote.auth.LoginDataSource
import com.google.firebase.auth.FirebaseUser

class LoginRepoImpl(private val dataSource:LoginDataSource):LoginRepo {
    override suspend fun singIn(email: String, password: String): FirebaseUser? = dataSource.singIn(email,password)
}