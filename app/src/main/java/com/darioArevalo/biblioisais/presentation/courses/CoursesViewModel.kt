package com.darioArevalo.biblioisais.presentation.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.darioArevalo.biblioisais.domain.courses.CoursesRepo
import kotlinx.coroutines.Dispatchers
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.domain.Libraries.LibrariesRepo


class CoursesViewModel(private val repo: CoursesRepo): ViewModel() {
    fun fetchEpisodesCourse0() = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(repo.getCourse0())
        } catch (e: Exception){
            emit(Result.Failure(e))
        }
    }

    fun fetchEpisodesCourse1() = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(repo.getCourse1())
        } catch (e: Exception){
            emit(Result.Failure(e))
        }
    }

    fun fetchEpisodiesCourse2() = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(repo.getCourse2())
        } catch (e: Exception){
            emit(Result.Failure(e))
        }
    }

}

class CoursesViewModelFactory(private val repo: CoursesRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CoursesRepo::class.java).newInstance(repo)
    }
}