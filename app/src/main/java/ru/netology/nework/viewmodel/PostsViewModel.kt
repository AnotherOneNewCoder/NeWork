package ru.netology.nework.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.netology.nework.api.PostsApiService
import ru.netology.nework.dto.Post
import ru.netology.nework.errors.UnknownError
import ru.netology.nework.models.StateModel
import ru.netology.nework.repository.PostsRepository
import ru.netology.nework.repository.PostsRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsRepository: PostsRepository,
    private val postsApiService: PostsApiService
): ViewModel() {

    val data: LiveData<List<Post>> = postsRepository.data.asLiveData(Dispatchers.Default)

    init {
        getAllPosts()
    }
    private fun getAllPosts() = viewModelScope.launch {
        try {
            postsRepository.getAll()
        } catch (e: Exception) {
            throw UnknownError
        }
    }
}