package com.darioArevalo.biblioisais.data.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostServer(
    val profile_picture: String = "",
    //val profile_name: String = "",
    //val post_timestamp: Timestamp? = null,
    val post_image: String = "",
    val autor: String = "",
    val contenido: String =  "",
    val titulo: String ="",
    val post_Id: String = ""

):Parcelable