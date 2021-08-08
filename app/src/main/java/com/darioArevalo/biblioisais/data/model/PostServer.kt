package com.darioArevalo.biblioisais.data.model


import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class PostServer(
    val profile_picture: String = "",
    val User_Id:String = "",
    val post_image: String = "",
    val autor: String = "",
    val contenido: String =  "",
    val titulo: String ="",
    val post_Id: String = "",
    //val timestamp: String = "",
    val created_at: Long?= null

):Parcelable