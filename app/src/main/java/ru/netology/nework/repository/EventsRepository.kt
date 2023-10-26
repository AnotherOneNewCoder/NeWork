package ru.netology.nework.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.nework.dto.Event
import ru.netology.nework.dto.Media

import ru.netology.nework.dto.MediaUpload

interface EventsRepository {
    val data: Flow<List<Event>>

    suspend fun getAll()

    suspend fun saveEvent(event: Event)
    suspend fun saveWithAttachments(event: Event, upload: MediaUpload)

    suspend fun uploadWithContent(upload: MediaUpload): Media

    suspend fun removeById(id: Long)

    suspend fun likeById(id: Long)

    suspend fun unlikeById(id: Long)

    suspend fun participate(id: Long)

    suspend fun dontParticipate(id: Long)
}