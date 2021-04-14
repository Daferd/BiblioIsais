package com.darioArevalo.biblioisais.data.remote.products

import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.ImageServer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.imaginativeworld.whynotimagecarousel.CarouselItem

class ProductsDataSource {
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