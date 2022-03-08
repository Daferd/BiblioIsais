package com.darioArevalo.biblioisais.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentPost(
    val content: String = "",
    var create_at: Long? = null,
    var autor: String = "",
    val post_Id: String = "",
    val user_Id: String = "",
    var validation: Boolean = false,
    val key_id_comment: String = "",
    var photo_url_user: String = ""
): Parcelable
