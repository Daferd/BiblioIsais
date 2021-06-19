package com.darioArevalo.biblioisais.data.model

import java.io.Serializable

data class CourseServer (
    val name : String = "",
    val author: String = "",
    val courseImage : String = "",
    val courseUrl : String = ""
) : Serializable