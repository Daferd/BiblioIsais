package com.darioArevalo.biblioisais.presentation.lecturahuerta

import android.util.Log
import androidx.lifecycle.*
import com.darioArevalo.biblioisais.domain.lecturahuerta.CommentPostRepo
import kotlinx.coroutines.Dispatchers
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.CommentPost
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CommentPostViewModel(private val repo: CommentPostRepo):ViewModel(){
    
    //active function

    fun fetchSuspendComments(keyPost: String)= liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {

            repo.getComments(keyPost).collect{
                Log.d("viewmodelcomment",it.toString())
                emit(it)
            }
            //emit(repo.getComments(keyPost))
        }catch(e:Exception){
            emit(Result.Failure(e))
        }
    }


    fun fectchStaticComments(keyPost: String)= liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(repo.suspend_get_comments(keyPost))
        }catch(e:Exception){
            emit(Result.Failure(e))
        }
    }


/*
    fun latestpost(keyPost: String) : StateFlow<Result<List<CommentPost>>> = flow {
        kotlin.runCatching {
            repo.suspend_get_comments(keyPost)
        }.onSuccess {resultpostlist->
            repo.suspend_get_comments(keyPost)
        }.onFailure { Throwable ->
            emit(Result.Failure(Exception(Throwable)))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(2000),
        initialValue = Result.Loading()
    )*/
/*
    private val comments = MutableStateFlow<Result<List<CommentPost>>>(Result.Loading())

    fun fetchPosts(keyPost: String)=
        viewModelScope.launch {
            kotlin.runCatching {
                repo.suspend_get_comments(keyPost)

            }.onSuccess { resultPostList->
                comments.value = resultPostList
            }.onFailure { throwable ->
            comments.value = Result.Failure(Exception(throwable))
            }
        }


    fun getComments():StateFlow<Result<List<CommentPost>>> = comments

    */
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