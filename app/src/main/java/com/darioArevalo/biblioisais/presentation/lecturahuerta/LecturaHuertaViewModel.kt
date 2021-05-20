    package com.darioArevalo.biblioisais.presentation

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.PostServer
import com.darioArevalo.biblioisais.domain.lecturahuerta.LecturaHuertaRepo
import kotlinx.coroutines.Dispatchers

class LecturaHuertaViewModel(private val repo: LecturaHuertaRepo):ViewModel() {
    fun fetchLatestPosts() = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(repo.getLatestPosts())
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }

    fun setearNewPost(autor:String,contenido:String,titulo:String,date:String,bitmap:Bitmap){
        repo.setPost(autor,contenido,titulo,date,bitmap)
    }

}

class LecturaHuertaViewModelFactory(private val repo: LecturaHuertaRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LecturaHuertaRepo::class.java).newInstance(repo)
    }
}

