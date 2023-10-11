package ru.netology.nework.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nework.repository.PostsRepository
import ru.netology.nework.repository.PostsRepositoryImpl

class PostsViewModel: ViewModel() {
    private val repository: PostsRepository = PostsRepositoryImpl()
    val data = repository.getAll()
}