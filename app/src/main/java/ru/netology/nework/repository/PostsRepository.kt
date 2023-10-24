package ru.netology.nework.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.nework.dto.Post

interface PostsRepository {

    val data: Flow<List<Post>>
    suspend fun getAll()
}