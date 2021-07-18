package com.darioArevalo.biblioisais.data.remote.courses

import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.CourseServer
import com.darioArevalo.biblioisais.data.model.LibraryServer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CoursesDataSource {

    suspend fun getCourse1(): Result<List<CourseServer>> {
        val episodeList = mutableListOf<CourseServer>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("courseCertificateInOwnLaw").get().await()
        for(post in querySnapshot.documents){
            post.toObject(CourseServer::class.java)?.let { fbCourse->episodeList.add(fbCourse) }
        }
        return Result.Success(episodeList)
    }

    suspend fun getCourse2(): Result<List<CourseServer>> {
        val episodeList = mutableListOf<CourseServer>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("courseCertificateOnEconomiesAndOrchards").get().await()
        for(post in querySnapshot){
            post.toObject(CourseServer::class.java)?.let { fbCourse->episodeList.add(fbCourse) }
        }
        return Result.Success(episodeList)
    }

    suspend fun getCourse3(): Result<List<CourseServer>> {
        val episodeList = mutableListOf<CourseServer>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("course3").get().await()
        for(post in querySnapshot){
            post.toObject(CourseServer::class.java)?.let { fbCourse->episodeList.add(fbCourse) }
        }
        return Result.Success(episodeList)
    }


}