package ru.netology.nework.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.nework.dto.Media
import ru.netology.nework.dto.MediaUpload
import ru.netology.nework.dto.Post
import ru.netology.nework.dto.TypeAttachment

interface PostsRepository {

    val data: Flow<List<Post>>
    suspend fun getAll()

    suspend fun savePost(post: Post)

    suspend fun saveWithAttach(
        post: Post,
        upload: MediaUpload,
        type: TypeAttachment
    )
    suspend fun uploadWithContent(upload: MediaUpload): Media

    suspend fun removeById(id: Long)
    suspend fun likeById(id: Long)
    suspend fun unlikeById(id: Long)
}