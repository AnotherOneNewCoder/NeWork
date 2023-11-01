package ru.netology.nework.repository

import ru.netology.nework.dto.Post


interface WallRepository {
    suspend fun getPostsByUserId(id: Long)
}