package com.darioArevalo.biblioisais.domain.bibliomundo

import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.LibraryServer
import com.darioArevalo.biblioisais.data.remote.bibliomundo.BibliomundoDataSource

class BibliomundoRepoImpl(private val dataSource: BibliomundoDataSource): BibliomundoRepo {

    override suspend fun getLocalLibraries(): Result<List<LibraryServer>> = dataSource.getLocalLibraries()
    override suspend fun getNationalLibraries(): Result<List<LibraryServer>> = dataSource.getNationalLibraries()
    override suspend fun getInternationalLibraries(): Result<List<LibraryServer>> = dataSource.getInternationalLibraries()
    /*
    override suspend fun getLocalLibraries(): List<LibraryServer> = dataSource.getLocalLibraries()
    override suspend fun getNationalLibraries(): List<LibraryServer> = dataSource.getNationalLibraries()
    override suspend fun getInternationalLibraries(): List<LibraryServer> = dataSource.getInternationalLibraries()
     */
}