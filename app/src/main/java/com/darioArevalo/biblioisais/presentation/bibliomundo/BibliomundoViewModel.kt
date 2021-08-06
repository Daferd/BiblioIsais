package com.darioArevalo.biblioisais.presentation.bibliomundo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.domain.bibliomundo.BibliomundoRepo
import kotlinx.coroutines.Dispatchers

class BibliomundoViewModel(private val repo : BibliomundoRepo):ViewModel() {

    fun fetchLibraries() = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(Result.Success(Triple(repo.getLocalLibraries(),repo.getNationalLibraries(),repo.getInternationalLibraries())))
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }

    fun fetchLocalLibraries() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(repo.getLocalLibraries())
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun fetchNationalLibraries() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(repo.getNationalLibraries())
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }

    fun fetchInternationalLibraries() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(repo.getInternationalLibraries())
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }



}

class BibliomundoViewModelFactory(private val repo: BibliomundoRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(BibliomundoRepo::class.java).newInstance(repo)
    }
}