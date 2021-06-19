package com.darioArevalo.biblioisais.presentation.profile

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.domain.products.ProductsRepo
import com.darioArevalo.biblioisais.domain.profile.ProfileRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class ProfileViewModel(private val repo: ProfileRepo): ViewModel() {
    fun fetchUser() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(repo.getUser())
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }

    fun updatePictureProfile(imageBitmap: Bitmap) = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.updateProfilePicture(imageBitmap)))
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }

    fun updateUsername(username: String) = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.updateUsername(username)))
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }

    fun updateEmail(email: String) = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.updateEmail(email)))
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }

    fun updatePassword(password: String) = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.updatePassword(password)))
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }
}

class ProfileViewModelFactory(private val repo: ProfileRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ProfileRepo::class.java).newInstance(repo)
    }
}