package ru.netology.nework.api

import retrofit2.Response
import retrofit2.http.*
import ru.netology.nework.dto.Job

interface JobsApiService {
    @GET("api/my/jobs")
    suspend fun getOwnJobs(): Response<List<Job>>
    @POST("api/my/jobs")
    suspend fun saveJob(@Body job: Job): Response<Job>

    @DELETE("api/my/jobs/{job_id}")
    suspend fun removeJobByID(@Path("id") id: Long): Response<Unit>
    @GET("api/{user_id}/jobs")
    suspend fun getUserJobs(@Path("id") id: Long): Response<List<Job>>
}