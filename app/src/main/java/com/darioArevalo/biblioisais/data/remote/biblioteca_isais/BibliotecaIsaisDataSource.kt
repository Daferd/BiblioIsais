package com.darioArevalo.biblioisais.data.remote.biblioteca_isais

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.ImageServer
import com.darioArevalo.biblioisais.data.model.PdfServer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class BibliotecaIsaisDataSource {
    companion object{
        val CREATE_FiLE = 1
    }


    suspend fun getPDFs(): Result<List<PdfServer>>{
        val pdfsList = mutableListOf<PdfServer>()

        val querySnapshot = FirebaseFirestore.getInstance().collection("pdfCourses").get().await()
        for (pdfs in querySnapshot.documents){
            pdfs.toObject(PdfServer::class.java)?.let {   it1 -> pdfsList.add(it1)  }
            Log.d("Query_pdf", "${pdfs}")
        }
        return Result.Success(pdfsList)
    }

    fun downloadPDF(urlString:String,context: Context){
        Log.d("datasource_download_pdf",urlString)
        //val storage = FirebaseStorage.getInstance()
        //val localfile = File.createTempFile("document","pdf")
        //val httpsReference = storage.getReferenceFromUrl(urlString)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val fileuri = Uri.parse(urlString)
        intent.setDataAndType(fileuri, "application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val in1 = Intent.createChooser(intent, "open file")
        in1.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(in1)
  }

    suspend fun getIsaisImages(): Result<List<ImageServer>>{
        val imageList = mutableListOf<ImageServer>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("libraryIsaisImages").get().await()
        imageList.clear()
        for(image in querySnapshot){
            image.toObject(ImageServer::class.java)?.let { fbImage -> imageList.add(fbImage) }
        }
        return Result.Success(imageList)
    }

    suspend fun getImageGoogle(): Result<List<ImageServer>>{
        val imageList = mutableListOf<ImageServer>()

        val querySnapshot = FirebaseFirestore.getInstance().collection("googleImages").get().await()
        for(image in querySnapshot){
            image.toObject(ImageServer::class.java)?.let { fbImage -> imageList.add(fbImage) }
        }
        return Result.Success(imageList)
    }
}