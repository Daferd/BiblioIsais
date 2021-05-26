package com.darioArevalo.biblioisais.data.model

import java.io.Serializable

data class LibraryServer (
        val name: String = "",
        val country: String = "",
        val city: String = "",
        val imageUrl: String = "",
        val pageUrl: String = ""
) : Serializable

data class LibraryList(
        val results: List<LibraryServer> = listOf()
)