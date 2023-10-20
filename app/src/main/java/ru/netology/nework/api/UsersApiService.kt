package ru.netology.nework.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import ru.netology.nework.dto.Token
import ru.netology.nework.dto.User

interface UsersApiService {
    @GET("api/users")
    suspend fun getAllUsers(): Response<List<User>>
    @GET("api/users/{user_id}")
    suspend fun getUserById(@Path("id") id: Long): Response<User>

    @FormUrlEncoded
    @POST("/api/users/authentication")
    suspend fun authUser(
        @Field("login") login: String,
        @Field("password") passwd: String,
    ): Response<Token>

    @Multipart
    @POST("/api/users/registration")
    suspend fun regUser(
        @Part("login") login: RequestBody,
        @Part("password") passwd: RequestBody,
        @Part("name") name: RequestBody,
        @Part image: MultipartBody.Part?,
    ): Response<Token>
}