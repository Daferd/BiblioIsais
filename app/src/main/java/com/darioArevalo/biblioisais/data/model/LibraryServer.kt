package com.darioArevalo.biblioisais.data.model

data class LibraryServer (
        val name: String = "",
        val country: String = "",
        val city: String = "",
        val imageUrl: String = "",
        val pageUrl: String = ""
)

data class LibraryList(
        val results: List<LibraryServer> = listOf()
)