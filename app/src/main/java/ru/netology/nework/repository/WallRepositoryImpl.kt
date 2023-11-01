package ru.netology.nework.repository

import ru.netology.nework.api.WallApiService
import ru.netology.nework.dao.PostDao
import ru.netology.nework.dto.Post
import ru.netology.nework.entity.toPostEntity
import ru.netology.nework.errors.ApiError
import ru.netology.nework.errors.NetworkError
import java.io.IOException
import javax.inject.Inject

class WallRepositoryImpl @Inject constructor(
    private val postDao: PostDao,
    private val wallApiService: WallApiService,
): WallRepository {
    override suspend fun getPostsByUserId(id: Long) {
        //val oldListPosts = postDao.getAllPosts()
        try {
            postDao.clearPostEntity()
            val response = wallApiService.getUserPosts(id)
            if (!response.isSuccessful) {
                throw ApiError(response.message())
            }
            val body = response.body() ?: throw ApiError(response.message())
            postDao.insertListPosts(body.toPostEntity())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            e.printStackTrace()

            throw UnknownError()
        }

    }
}