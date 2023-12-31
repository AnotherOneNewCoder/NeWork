package ru.netology.nework.api


import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

import ru.netology.nework.dto.Event
import ru.netology.nework.dto.Media

interface EventsApiService {

    @GET("events")
    suspend fun getAllEvents(): Response<List<Event>>
    @GET("events/latest")
    suspend fun getLatestEvents(@Query("count") count: Int): Response<List<Event>>
    @GET("events/{id}/after")
    suspend fun getEventsAfter(@Path("id") id: Long, @Query("count") count: Int): Response<List<Event>>
    @GET("events/{id}/before")
    suspend fun getEventsBefore(@Path("id") id: Long, @Query("count") count: Int): Response<List<Event>>
    @GET("events/{id}")
    suspend fun getEventById(@Path("id") id: Long): Response<Event>

    @GET("events/{id}/newer")
    suspend fun getNewerEvent(@Path("id") id: Long): Response<List<Event>>

//
//    @POST("events")
//    suspend fun saveEvent(@Body event: Event): Response<Event>
    @POST("events")
    suspend fun saveEvent(@Body event: Event): Response<Event>

    @POST("events/{id}/likes")
    suspend fun likeEventById(@Path("id") id: Long): Response<Event>
    @POST("events/{id}/participants")
    suspend fun participateEventById(@Path("id") id: Long): Response<Event>

    @DELETE("events/{id}/participants")
    suspend fun dontParticipateEventById(@Path("id") id: Long): Response<Event>

    @DELETE("events/{id}")
    suspend fun removeEventById(@Path("id") id: Long): Response<Unit>

    @DELETE("events/{id}/likes")
    suspend fun unlikeEventById(@Path("id") id: Long): Response<Event>


    @Multipart
    @POST("media")
    suspend fun uploadMedia(@Part media: MultipartBody.Part): Response<Media>

}