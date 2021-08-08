package com.darioArevalo.biblioisais.presentation.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.domain.form.FormRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class FormViewModel(private val repo: FormRepo): ViewModel() {
    fun sendForm(
        username: String,
        email: String,
        date: String,
        numberPhone: String,
        organization: String,
        gender: String
    ) = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.sendForm(username,email,date,numberPhone,organization,gender)))
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }
}

class FormViewModelFactory(private val repo: FormRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FormViewModel(repo) as T
    }
}