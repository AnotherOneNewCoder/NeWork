package ru.netology.nework.repository


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.netology.nework.api.JobsApiService
import ru.netology.nework.dao.JobDao
import ru.netology.nework.dto.Job
import ru.netology.nework.entity.JobEntity
import ru.netology.nework.entity.toDto
import ru.netology.nework.entity.toEntity
import ru.netology.nework.errors.ApiError
import ru.netology.nework.errors.NetworkError
import javax.inject.Inject
import java.io.IOException

class JobsRepositoryImpl @Inject constructor(
    private val jobDao: JobDao,
    private val jobsApiService: JobsApiService,
) : JobsRepository {


    override val data: Flow<List<Job>> = jobDao.getAllJobs()
        .map { it.toDto() }
        .flowOn(Dispatchers.Default)

    private val _data = MutableLiveData<List<Job>>()


    override suspend fun getAuthJobs() {
        try {
            jobDao.getAllJobs()
            val response = jobsApiService.getOwnJobs()
            if (!response.isSuccessful) {
                throw ApiError(response.message())
            }
            val body = response.body() ?: throw ApiError(response.message())
            jobDao.insertListJobs(body.toEntity())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            e.printStackTrace()
            throw UnknownError()
        }
    }

    override suspend fun getJobsByUserId(id: Long) {
        try {
            jobDao.clear()
            val response = jobsApiService.getUserJobs(id)
            if (!response.isSuccessful) {
                throw ApiError(response.message())
            }
            val body = response.body() ?: throw ApiError(response.message())
            _data.postValue(body)
            jobDao.insertListJobs(body.toEntity())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            e.printStackTrace()
            throw UnknownError()
        }
    }

    override suspend fun removeById(id: Long) {
        try {
            val response = jobsApiService.removeJobByID(id)
            if (!response.isSuccessful) {
                throw ApiError(response.message())
            }
            jobDao.deleteJobById(id)
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            e.printStackTrace()
            throw UnknownError()
        }
    }

    override suspend fun editJob(job: Job) {
        try {
            val response = jobsApiService.saveJob(job)
            if (!response.isSuccessful) {
                throw ApiError(response.message())
            }
            val body = response.body() ?: throw ApiError(response.message())
            jobDao.insertJob(JobEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            e.printStackTrace()
            throw UnknownError()
        }
    }
}