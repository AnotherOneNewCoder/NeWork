package ru.netology.nework.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.nework.dto.Post

interface PostsRepository {
    fun getAll(): Flow<List<Post>>
}