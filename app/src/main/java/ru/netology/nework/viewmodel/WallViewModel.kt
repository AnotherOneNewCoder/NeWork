package ru.netology.nework.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ru.netology.nework.repository.WallRepository
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class WallViewModel @Inject constructor(
    private val wallRepository: WallRepository,

): ViewModel() {
    fun loadUserPosts(id: Long) = viewModelScope.launch {

        try {
            wallRepository.getPostsByUserId(id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}