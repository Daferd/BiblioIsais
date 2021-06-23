package com.darioArevalo.biblioisais.data.remote.products

import android.util.Log
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.ImageServer
import com.darioArevalo.biblioisais.data.model.PdfServer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import kotlin.math.log

class ProductsDataSource {


    suspend fun getPDFs(): Result<List<PdfServer>>{
        val pdfsList = mutableListOf<PdfServer>()

        val querySnapshot = FirebaseFirestore.getInstance().collection("pdfCourses").get().await()
        for (pdfs in querySnapshot.documents){
            pdfs.toObject(PdfServer::class.java)?.let {   it1 -> pdfsList.add(it1)  }
            Log.d("Query_pdf", "${pdfs}")


        }
        return Result.Success(pdfsList)
    }

    fun downloadPDF(urlString:String){
        Log.d("datasource_download_pdf",urlString)
    }

    suspend fun getIsaisImages(): Result<List<ImageServer>>{
        val imageList = mutableListOf<ImageServer>()

        val querySnapshot = FirebaseFirestore.getInstance().collection("isaisImages").get().await()
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