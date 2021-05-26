package com.darioArevalo.biblioisais.presentation.lecturahuerta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.darioArevalo.biblioisais.domain.lecturahuerta.CommentPostRepo
import kotlinx.coroutines.Dispatchers
import com.darioArevalo.biblioisais.core.Result

class CommentPostViewModel(private val repo: CommentPostRepo):ViewModel(){
    fun fechtLatestComments(keyPost:String) = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(repo.getLatestComments(keyPost))
        }catch(e:Exception){
            emit(Result.Failure(e))
        }

    }
    
    fun addNewComment(content:String,keyPost:String){
        repo.addNewComment(content,keyPost)
    }

}

class CommentPostViewModelFactory(private val repo:CommentPostRepo):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CommentPostRepo::class.java).newInstance(repo)
    }
}