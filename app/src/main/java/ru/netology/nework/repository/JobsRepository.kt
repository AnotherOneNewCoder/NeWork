package ru.netology.nework.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.nework.dto.Job

interface JobsRepository {
    fun getAll(): Flow<List<Job>>
    fun removeById(id:Long)
    fun editJob(job: Job)
}