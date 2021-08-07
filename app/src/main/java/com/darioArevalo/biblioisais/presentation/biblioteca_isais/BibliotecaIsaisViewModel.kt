package com.darioArevalo.biblioisais.presentation.biblioteca_isais

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.domain.biblioteca_isais.BibliotecaIsaisRepo
import kotlinx.coroutines.Dispatchers

class BibliotecaIsaisViewModel(private val repo: BibliotecaIsaisRepo): ViewModel() {

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

    fun fetchDownloadPDF(urlString:String,context: Context){
        repo.downloadPDF(urlString,context)
    }
}

class BibliotecaIsaisViewModelFactory(private val repo: BibliotecaIsaisRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(BibliotecaIsaisRepo::class.java).newInstance(repo)
    }
}























