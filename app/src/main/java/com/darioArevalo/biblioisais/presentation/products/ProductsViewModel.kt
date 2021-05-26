package com.darioArevalo.biblioisais.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.domain.products.ProductsRepo
import kotlinx.coroutines.Dispatchers

class ProductsViewModel(private val repo: ProductsRepo): ViewModel() {

    fun fetchPdf() = liveData(Dispatchers.IO){
        emit(Result.Loading())

        try{
            emit(repo.getPDFs())
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }
    fun fetchIsaisImages() = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(repo.getIsaisImages())
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }

    fun fetchGoogleImages()= liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(repo.getGoogleImages())
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }
}

class ProductsViewModelFactory(private val repo: ProductsRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ProductsRepo::class.java).newInstance(repo)
    }
}