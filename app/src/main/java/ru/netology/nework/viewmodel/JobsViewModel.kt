package ru.netology.nework.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.netology.nework.api.JobsApiService
import ru.netology.nework.auth.AppAuth
import ru.netology.nework.dto.Job
import ru.netology.nework.errors.ApiError
import ru.netology.nework.models.JobModel
import ru.netology.nework.models.StateModel
import ru.netology.nework.repository.JobsRepository
import ru.netology.nework.utils.SingleLiveEvent
import java.io.IOException
import javax.inject.Inject


private val emptyJob = Job()

@ExperimentalCoroutinesApi
@HiltViewModel
class JobsViewModel @Inject constructor(
    private val jobsRepository: JobsRepository,
    private val jobsApiService: JobsApiService,
    appAuth: AppAuth,
) : ViewModel() {
    val data: Flow<List<Job>> = appAuth.authSateFlow.flatMapLatest { (myId, _) ->
        jobsRepository.data.map {
            JobModel()
            it.map { job ->
                job.copy(
//                    ownedByMe = userId.value == myId
                )
            }
        }
    }
    private val userId = MutableLiveData<Long>()
    private val edited = MutableLiveData(emptyJob)
    private val _state = MutableLiveData<StateModel>()
    val state: LiveData<StateModel>
        get() = _state
    private val _created = SingleLiveEvent<Unit>()
    val created: LiveData<Unit>
        get() = _created

    fun loadJobs(id: Long) = viewModelScope.launch {
        _state.postValue(StateModel(loading = true))
        try {
            jobsRepository.getJobsByUserId(id)
            _state.postValue(StateModel())
        } catch (e: Exception) {
            e.printStackTrace()
            _state.postValue(StateModel(error = true))
        }
    }

    fun setId(id: Long) {
        userId.value = id
    }

    fun save() {
        edited.value?.let {
            _created.value = Unit
            viewModelScope.launch {
                _state.postValue(StateModel(loading = true))
                try {
                    jobsRepository.editJob(it)
                    _state.postValue(StateModel())
                } catch (e: Exception) {
                    e.printStackTrace()
                    _state.postValue(StateModel(error = true))
                }
            }
        }
        edited.value = Job()
    }

    fun changeJob(
        companyName: String,
        position: String,
        started: String,
        finished: String?,
        link: String?,
    ) {
        edited.value.let {
            val textCompany = companyName.trim()
            if (edited.value?.name != textCompany) {
                edited.value = edited.value?.copy(name = textCompany)
            }
            val textPosition = position.trim()
            if (edited.value?.name != textCompany) {
                edited.value = edited.value?.copy(position = textPosition)
            }
            val textLink = link?.trim()
            if (edited.value?.link != textLink) {
                edited.value = edited.value?.copy(link = textLink)
            }
            if (edited.value?.start != started) {
                edited.value = edited.value?.copy(start = started)
            }

            if (edited.value?.finish != finished) {
                edited.value = edited.value?.copy(finish = finished)
            }
        }
    }

    fun edit(job: Job) {
        edited.value = job
    }

    fun removeById(id: Long) = viewModelScope.launch {
        _state.postValue(StateModel(loading = true))
        try {
            jobsRepository.removeById(id)
            _state.postValue(StateModel())
        } catch (e: Exception) {
            e.printStackTrace()
            _state.postValue(StateModel(error = true))
        }
    }

    fun startDate(date: String) {
        edited.value = edited.value?.copy(start = date)
    }

    fun finishDate(date: String) {
        edited.value = edited.value?.copy(finish = date)
    }

    fun saveThroughViewModel(
        name: String,
        position: String,
        started: String,
        finished: String?,
        link: String?,
    ) {
        viewModelScope.launch {
            val newJob = edited.value?.copy(
                name = name,
                position = position,
                start = started,
                finish = finished,
                link = link,
            )
            try{
                if (newJob != null) {
                    val response = jobsApiService.saveJob(newJob)
                    if (!response.isSuccessful) {
                        throw ApiError(response.message())
                    }
                    edited.postValue(response.body())
                    _created.value = Unit
                }


            } catch (e: IOException) {
                e.printStackTrace()
                _state.postValue(StateModel(error = true))
            }
            catch (e: Exception) {
                e.printStackTrace()
                _state.postValue(StateModel(loginError = true))
            }
        }
    }
}