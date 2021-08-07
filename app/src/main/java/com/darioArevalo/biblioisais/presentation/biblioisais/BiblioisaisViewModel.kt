package com.darioArevalo.biblioisais.presentation.biblioisais

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.darioArevalo.biblioisais.domain.biblioisais.BiblioisaisRepo
import kotlinx.coroutines.Dispatchers
import com.darioArevalo.biblioisais.core.Result


class BiblioisaisViewModel(private val repo: BiblioisaisRepo): ViewModel() {
    fun fetchEpisodesCourse1() = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(repo.getCourse1())
        } catch (e: Exception){
            emit(Result.Failure(e))
        }
    }

    fun fetchEpisodesCourse2() = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(repo.getCourse2())
        } catch (e: Exception){
            emit(Result.Failure(e))
        }
    }

    fun fetchEpisodiesCourse3() = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(repo.getCourse3())
        } catch (e: Exception){
            emit(Result.Failure(e))
        }
    }

}

class BiblioisaisViewModelFactory(private val repo: BiblioisaisRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(BiblioisaisRepo::class.java).newInstance(repo)
    }
}












