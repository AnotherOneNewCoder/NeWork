package ru.netology.nework.api

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*
import ru.netology.nework.dto.Media
import ru.netology.nework.dto.Post

interface PostsApiService {

    @GET("posts")
    suspend fun getAllPosts(): Response<List<Post>>

    @POST("posts")
    suspend fun addPost(@Body post: Post): Response<Post>

    @GET("posts/latest")
    suspend fun getLatestPosts(@Query("count") count: Int): Response<List<Post>>

    @GET("posts/{post_id}")
    suspend fun getPostById(@Path("id") id: Long): Response<Post>

    @DELETE("posts/{post_id}")
    suspend fun deletePostById(@Path("id") id: Long): Response<Unit>

    @GET("posts/{post_id}/after")
    suspend fun getPostsAfter(@Path("id") id: Long, @Query("count") count: Int): Response<List<Post>>
    @GET("posts/{post_id}/before")
    suspend fun getPostsBefore(@Path("id") id: Long, @Query("count") count: Int): Response<List<Post>>

    @GET("posts/{post_id}/newer")
    suspend fun getNewerPosts(@Path("id") id: Long): Response<List<Post>>
    @DELETE("posts/{post_id}/likes")
    suspend fun unlikePostById(@Path("id") id: Long): Response<Post>
    @POST("posts/{post_id}/likes")
    suspend fun likePostById(@Path("id") id: Long): Response<Post>
    @Multipart
    @POST("media")
    suspend fun uploadMedia(@Part media: MultipartBody.Part): Response<Media>


}