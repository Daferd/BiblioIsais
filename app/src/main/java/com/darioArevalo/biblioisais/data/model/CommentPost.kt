package com.darioArevalo.biblioisais.data.model


data class CommentPost(
    val content: String = "",
    val create_at: Long? = null,
    val autor: String = "",
    val post_Id: String = "",
    val user_Id: String = "",
    var photo_url_user: String = ""
)
