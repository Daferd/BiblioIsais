package com.darioArevalo.biblioisais.data.model

import java.io.Serializable

data class UserServer(
    val email: String = "",
    val username: String = "",
    val photo_url: String = "",
    val form: Boolean = false
) : Serializable