package com.darioArevalo.biblioisais.presentation.lecturahuerta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.darioArevalo.biblioisais.domain.lecturahuerta.CommentPostRepo
import kotlinx.coroutines.Dispatchers
import com.darioArevalo.biblioisais.core.Result

class CommentPostViewModel(private val repo: CommentPostRepo):ViewModel(){
    //no active function
    fun fechtLatestComments(keyPost:String) = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(repo.getLatestComments(keyPost))
        }catch(e:Exception){
            emit(Result.Failure(e))
        }

    }
    //active function
    fun fetchSuspendComments(keyPost: String)= liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(repo.suspend_get_comments(keyPost))
        }catch(e:Exception){
            emit(Result.Failure(e))
        }
    }
    
    fun addNewComment(content:String,keyPost:String){
        repo.addNewComment(content,keyPost)
    }

    fun edit_comment(content: String,key_id_comment: String,keyPost: String){
        repo.edit_comment_repo(content,key_id_comment, keyPost)
    }

    fun delete_comment_vm(key_id_comment: String,keyPost: String){
        repo.delete_comment(key_id_comment,keyPost)
    }

}

class CommentPostViewModelFactory(private val repo:CommentPostRepo):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CommentPostRepo::class.java).newInstance(repo)
    }
}