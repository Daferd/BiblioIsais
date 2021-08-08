package com.darioArevalo.biblioisais.domain.bibliomundo

import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.LibraryServer

interface BibliomundoRepo {

    suspend fun getLocalLibraries():Result<List<LibraryServer>>
    suspend fun getNationalLibraries():Result<List<LibraryServer>>
    suspend fun getInternationalLibraries():Result<List<LibraryServer>>


    /*
    suspend fun getLocalLibraries():List<LibraryServer>
    suspend fun getNationalLibraries():List<LibraryServer>
    suspend fun getInternationalLibraries():List<LibraryServer>

     */

}