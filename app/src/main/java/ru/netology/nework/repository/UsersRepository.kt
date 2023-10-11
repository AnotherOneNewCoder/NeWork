package ru.netology.nework.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.nework.dto.User

interface UsersRepository {
    fun getAll(): Flow<List<User>>
}