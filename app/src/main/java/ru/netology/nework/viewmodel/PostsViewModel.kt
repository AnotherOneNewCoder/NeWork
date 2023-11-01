package ru.netology.nework.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _state = MutableLiveData<StateModel>()
    val state: LiveData<StateModel>
        get() = _state

    init {
        getAllPosts()
    }
    private fun getAllPosts() = viewModelScope.launch {
        _state.postValue(StateModel(loading = true))
        try {
            postsRepository.getAll()
            _state.postValue(StateModel())
        } catch (e: Exception) {
            _state.postValue(StateModel(loading = true))
        }
    }
    fun refreshEvents() = viewModelScope.launch {
        _state.postValue(StateModel(loading = true))
        try {
            postsRepository.getAll()
            _state.postValue(StateModel())
        } catch (e: Exception) {
            _state.postValue(StateModel(loading = true))
        }
    }
}