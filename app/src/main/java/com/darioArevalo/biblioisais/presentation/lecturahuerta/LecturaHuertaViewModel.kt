package com.darioArevalo.biblioisais.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.darioArevalo.biblioisais.core.Resource
import com.darioArevalo.biblioisais.domain.lecturahuerta.LecturaHuertaRepo
import kotlinx.coroutines.Dispatchers

class LecturaHuertaViewModel(private val repo: LecturaHuertaRepo):ViewModel() {
    fun fetchLatestPosts() = liveData(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            emit(repo.getLatestPosts())
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

}

class LecturaHuertaViewModelFactory(private val repo: LecturaHuertaRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LecturaHuertaRepo::class.java).newInstance(repo)
    }
}