package ru.netology.nework.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


import ru.netology.nework.dto.Post

interface MyWallApiService {
    @GET("my/wall")
    suspend fun getOwnPosts(): Response<List<Post>>
    @GET("my/wall/latest")
    suspend fun getLatestOwnPosts(@Query("count") count: Int): Response<List<Post>>


    @GET("my/wall/{post_id}/after")
    suspend fun getOwnPostsAfter(@Path("id") id: Long, @Query("count") count: Int): Response<List<Post>>

    @GET("my/wall/{post_id}/before")
    suspend fun getOwnPostsBefore(@Path("id") id: Long, @Query("count") count: Int): Response<List<Post>>

    @GET("my/wall/{post_id}/newer")
    suspend fun getOwnPostsNewer(@Path("id") id: Long): Response<List<Post>>




}