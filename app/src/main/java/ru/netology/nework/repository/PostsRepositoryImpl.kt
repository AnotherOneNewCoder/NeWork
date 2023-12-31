package ru.netology.nework.repository


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.netology.nework.api.PostsApiService
import ru.netology.nework.dao.PostDao
import ru.netology.nework.dto.Attachment
import ru.netology.nework.dto.Media
import ru.netology.nework.dto.MediaUpload
import ru.netology.nework.dto.Post
import ru.netology.nework.dto.TypeAttachment
import ru.netology.nework.entity.PostEntity
import ru.netology.nework.entity.toPost
import ru.netology.nework.entity.toPostEntity
import ru.netology.nework.errors.ApiError
import ru.netology.nework.errors.NetworkError
import java.io.IOException
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val postDao: PostDao,
    private val postsApiService: PostsApiService
) : PostsRepository {

    override val data: Flow<List<Post>> =
        postDao.getAllPosts().map { it.toPost() }.flowOn(Dispatchers.Default)

    override suspend fun getAll() {
        try {
            val response = postsApiService.getAllPosts()
            if (!response.isSuccessful) {
                throw ApiError(response.message())
            }
            postDao.clearPostEntity()
            val body = response.body() ?: throw ApiError(response.message())
            postDao.insertListPosts(body.toPostEntity())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            e.printStackTrace()

            throw UnknownError()
        }

    }

    override suspend fun savePost(post: Post) {
        try {
            val response = postsApiService.addPost(post)
            if (!response.isSuccessful) {
                throw ApiError(response.message())
            }
            val body = response.body() ?: throw ApiError(response.message())
            postDao.savePost(PostEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            e.printStackTrace()

            throw UnknownError()
        }
    }

    override suspend fun saveWithAttach(post: Post, upload: MediaUpload, type: TypeAttachment) {
        try {
            val media = uploadWithContent(upload)
            val postWithAttach = post.copy(
                attachment = Attachment(media.url, type)
            )
            savePost(postWithAttach)
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            e.printStackTrace()

            throw UnknownError()
        }
    }

    override suspend fun uploadWithContent(upload: MediaUpload): Media {
        try {
            val media = MultipartBody.Part.createFormData(
                "file",
                "name",
                upload.inputStream.readBytes().toRequestBody("*/*".toMediaTypeOrNull())
            )
            val response = postsApiService.uploadMedia(media)
            if (!response.isSuccessful) {
                throw ApiError(response.message())
            }
            return response.body() ?: throw ApiError(response.message())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            e.printStackTrace()

            throw UnknownError()
        }
    }

    override suspend fun removeById(id: Long) {
        try {
            val response = postsApiService.deletePostById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.message())
            }
            postDao.deletePostById(id)
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            e.printStackTrace()

            throw UnknownError()
        }
    }

    override suspend fun likeById(id: Long) {
        try {
            postDao.likePostById(id)
            val response = postsApiService.likePostById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.message())
            }
            val body = response.body() ?: throw ApiError(response.message())
            postDao.insertPost(PostEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            e.printStackTrace()

            throw UnknownError()
        }
    }

    override suspend fun unlikeById(id: Long) {
        try {
            postDao.unlikePostById(id)
            val response = postsApiService.unlikePostById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.message())
            }
            val body = response.body() ?: throw ApiError(response.message())
            postDao.insertPost(PostEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            e.printStackTrace()

            throw UnknownError()
        }
    }


}