package ru.netology.nework.api

import retrofit2.Response
import retrofit2.http.*
import ru.netology.nework.dto.Job

interface JobsApiService {
    @GET("my/jobs")
    suspend fun getOwnJobs(): Response<List<Job>>
    @POST("my/jobs")
    suspend fun saveJob(@Body job: Job): Response<Job>

    @DELETE("my/jobs/{job_id}")
    suspend fun removeJobByID(@Path("id") id: Long): Response<Unit>
    @GET("{user_id}/jobs")
    suspend fun getUserJobs(@Path("id") id: Long): Response<List<Job>>
}