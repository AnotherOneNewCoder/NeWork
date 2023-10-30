package ru.netology.nework.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.nework.dto.Job

interface JobsRepository {

    val data: Flow<List<Job>>
    suspend fun getAuthJobs()
    suspend fun getJobsByUserId(id: Long)
    suspend fun removeById(id:Long)
    suspend fun editJob(job: Job)
}