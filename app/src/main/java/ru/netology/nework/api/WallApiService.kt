package ru.netology.nework.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.netology.nework.dto.Post

interface WallApiService {

    @GET("{id}/wall")
    suspend fun getUserPosts(@Path("id") id: Long): Response<List<Post>>
    @GET("{user_id}/wall/latest")
    suspend fun getLatestUserPosts(@Path("id") id: Long, @Query("count") count: Int): Response<List<Post>>


    @GET("{user_id}/wall/{post_id}/after")
    suspend fun getUserPostsAfter(@Path("user_id") userId: Long,@Path("post_id") postId: Long, @Query("count") count: Int): Response<List<Post>>

    @GET("{user_id}/wall/{post_id}/before")
    suspend fun getUserPostsBefore(@Path("user_id") userId: Long,@Path("post_id") postId: Long, @Query("count") count: Int): Response<List<Post>>

    @GET("{user_id}/wall/{post_id}/newer")
    suspend fun getUserPostsNewer(@Path("user_id") userId: Long,@Path("post_id") postId: Long): Response<List<Post>>
}