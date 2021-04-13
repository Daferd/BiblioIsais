package com.darioArevalo.biblioisais.data.remote.libraries

import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.LibraryList
import com.darioArevalo.biblioisais.data.model.LibraryServer
import com.darioArevalo.biblioisais.data.model.PostServer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LibrariesDataSource {
    val libraryListAux = mutableListOf<List<LibraryList>>()

    suspend fun getLocalLibraries():Result<List<LibraryServer>>{
        val libraryList = mutableListOf<LibraryServer>()

        val querySnapshot = FirebaseFirestore.getInstance().collection("localLibraries").get().await()
        for(post in querySnapshot){
            post.toObject(LibraryServer::class.java)?.let { fbLibrary->libraryList.add(fbLibrary) }
        }
        return Result.Success(libraryList)
    }

    suspend fun getNationalLibraries():Result<List<LibraryServer>>{
        val libraryList = mutableListOf<LibraryServer>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("nationalLibraries").get().await()
        for(post in querySnapshot.documents){
            post.toObject(LibraryServer::class.java)?.let { fbLibrary->libraryList.add(fbLibrary) }
        }
        return Result.Success(libraryList)
    }

    suspend fun getInternationalLibraries():Result<List<LibraryServer>>{
        val libraryList = mutableListOf<LibraryServer>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("internationalLibraries").get().await()
        for(post in querySnapshot.documents){
            post.toObject(LibraryServer::class.java)?.let { fbLibrary->libraryList.add(fbLibrary) }
        }
        return Result.Success(libraryList)
    }
}
