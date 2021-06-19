package com.darioArevalo.biblioisais.presentation.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.domain.auth.AuthRepo
import com.darioArevalo.biblioisais.domain.form.FormRepo
import com.darioArevalo.biblioisais.presentation.auth.AuthViewModel
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class FormViewModel(private val repo: FormRepo): ViewModel() {
    fun sendForm(username: String, email: String, age: String, numberPhone: String ) = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.sendForm(username,email,age,numberPhone)))
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