package com.darioArevalo.biblioisais.domain.Libraries

import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.LibraryServer

interface LibrariesRepo {
    suspend fun getLocalLibraries():Result<List<LibraryServer>>
    suspend fun getNationalLibraries():Result<List<LibraryServer>>
    suspend fun getInternationalLibraries():Result<List<LibraryServer>>

}