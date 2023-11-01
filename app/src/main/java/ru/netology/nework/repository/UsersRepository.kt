package ru.netology.nework.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.nework.dto.User

interface UsersRepository {
    val data: Flow<List<User>>
    suspend fun getAll()

    fun searchUser(searchQuery: String): Flow<List<User>>
}