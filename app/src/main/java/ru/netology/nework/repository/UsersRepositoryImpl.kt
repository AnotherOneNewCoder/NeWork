package ru.netology.nework.repository


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.netology.nework.api.UsersApiService
import ru.netology.nework.dao.UserDao
import ru.netology.nework.dto.User
import ru.netology.nework.entity.toUser
import ru.netology.nework.entity.toUserEntity
import ru.netology.nework.errors.ApiError
import ru.netology.nework.errors.NetworkError
import java.io.IOException

import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val usersApiService: UsersApiService,
) : UsersRepository {
    override val data: Flow<List<User>> = userDao.getAllUsers().map {
        it.toUser()
    }.flowOn(Dispatchers.Default)

    override suspend fun getAll() {
        try {
            userDao.getAllUsers()
            val  response = usersApiService.getAllUsers()
            if (!response.isSuccessful) {
                throw ApiError(response.message())
            }
            val body = response.body() ?: throw ApiError(response.message())
            userDao.insertListUsers(body.toUserEntity())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            e.printStackTrace()
//            throw UnknownError()
        }
    }

    override fun searchUser(searchQuery: String): Flow<List<User>> {
        return userDao.searchUser(searchQuery).map {
            it.toUser()
        }.flowOn(Dispatchers.Default)
    }
}