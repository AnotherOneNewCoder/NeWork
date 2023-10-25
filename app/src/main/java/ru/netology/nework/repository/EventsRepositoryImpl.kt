package ru.netology.nework.repository


import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.MultipartBody
//import okhttp3.RequestBody.Companion.toRequestBody
import ru.netology.nework.api.EventsApiService
import ru.netology.nework.dao.EventDao
import ru.netology.nework.db.AppDb
import ru.netology.nework.dto.Event
import ru.netology.nework.dto.Media
import ru.netology.nework.dto.MediaUpload
import ru.netology.nework.entity.EventEntity
import ru.netology.nework.entity.toEvent
import ru.netology.nework.entity.toEventEntity
import ru.netology.nework.entity.toEventEntity2
import ru.netology.nework.errors.ApiError
import ru.netology.nework.errors.NetworkError
import java.io.IOException

import javax.inject.Inject

class EventsRepositoryImpl @Inject constructor(
    private val eventDao: EventDao,
    private val eventsApiService: EventsApiService,
//    appDb: AppDb
): EventsRepository {
    override val data: Flow<List<Event>> = eventDao.getAllEvents().map {
        it.toEvent()
    }.flowOn(Dispatchers.Default)

    override suspend fun getAll() {
        try{
            eventDao.getAllEvents()
            val response = eventsApiService.getAllEvents()
            if (!response.isSuccessful) {
                throw ApiError(response.message())
            }
            val body = response.body() ?: throw ApiError(response.message())
            eventDao.insertListEvents(body.toEventEntity2())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            e.printStackTrace()
            throw UnknownError()
        }
    }

    override suspend fun saveEvent(event: Event) {

    }

    override suspend fun saveWithAttachments(event: Event, upload: MediaUpload) {

    }

//    override suspend fun uploadWithContent(upload: MediaUpload): Media {
//        try {
//            val media = MultipartBody.Part.createFormData(
//                "file",
//                "name",
//                upload.inputStream.readBytes().toRequestBody("*/*".toMediaTypeOrNull())
//            )
//            val response = eventsApiService.uploadMedia(media)
//            if (!response.isSuccessful) {
//                throw ApiError(response.message())
//            }
//            return response.body() ?: throw ApiError(response.message())
//        } catch (e: IOException) {
//            throw NetworkError
//        } catch (e: Exception) {
//            throw UnknownError()
//        }
//
//    }

    override suspend fun removeById(id: Long) {

    }

    override suspend fun likeById(id: Long) {
        try {
            eventDao.likeEventById(id)
            val response = eventsApiService.likeEventById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.message())
            }
            val body = response.body() ?: throw ApiError(response.message())
            eventDao.insertEvent(EventEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {


            throw UnknownError()
        }
    }

    override suspend fun unlikeById(id: Long) {
        try {
            eventDao.unlikeEventById(id)
            val response = eventsApiService.unlikeEventById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.message())
            }
            val body = response.body() ?: throw ApiError(response.message())
            eventDao.insertEvent(EventEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            e.printStackTrace()
            throw UnknownError()
        }
    }

    override suspend fun participate(id: Long) {
        try {
            eventDao.participateEventById(id)
            val response = eventsApiService.participateEventById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.message())
            }
            val body = response.body() ?: throw ApiError(response.message())
            eventDao.insertEvent(EventEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            e.printStackTrace()
            throw UnknownError()
        }
    }

    override suspend fun dontParticipate(id: Long) {
        try {
            eventDao.dontParticipateEventById(id)
            val response = eventsApiService.dontParticipateEventById(id)
            if (!response.isSuccessful) {
                throw ApiError(response.message())
            }
            val body = response.body() ?: throw ApiError(response.message())
            eventDao.insertEvent(EventEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            e.printStackTrace()
            throw UnknownError()
        }
    }


}