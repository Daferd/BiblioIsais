    package com.darioArevalo.biblioisais.presentation

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.PostServer
import com.darioArevalo.biblioisais.domain.lecturahuerta.LecturaHuertaRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch

    class LecturaHuertaViewModel(private val repo: LecturaHuertaRepo):ViewModel() {
    fun fetchLatestPosts() = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            repo.getLatestPosts().collect { emit(it) }
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }

/*
    private val latest_post = MutableStateFlow<Result<List<PostServer>>>(Result.Loading())
    fun fetchLatestPosts()=
        viewModelScope.launch {
            kotlin.runCatching {
                repo.getLatestPosts()
            }.onSuccess {post->
                latest_post.value = post
            }.onFailure { Throwable->
                latest_post.value = Result.Failure(Exception(Throwable))
            }

        }
    fun getPost():StateFlow<Result<List<PostServer>>> = latest_post

*/




    fun fetchIsaisImages() = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(repo.getIsaisImages())
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

