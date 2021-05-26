package com.darioArevalo.biblioisais.presentation.libraries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.domain.Libraries.LibrariesRepo
import kotlinx.coroutines.Dispatchers

class LibrariesViewModel(private val repo : LibrariesRepo):ViewModel() {

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

class LibrariesViewModelFactory(private val repo: LibrariesRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LibrariesRepo::class.java).newInstance(repo)
    }
}