package ru.netology.nework.viewmodel


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
import ru.netology.nework.auth.AppAuth
import ru.netology.nework.dto.Job
import ru.netology.nework.models.JobModel
import ru.netology.nework.models.StateModel
import ru.netology.nework.repository.JobsRepository
import ru.netology.nework.utils.SingleLiveEvent
import javax.inject.Inject


private val emptyJob = Job(
    id = 0L,
    name = "",
    position = "",
    start = "",
    finish = null,

)

@ExperimentalCoroutinesApi
@HiltViewModel
class JobsViewModel @Inject constructor(
    private val jobsRepository: JobsRepository,
    appAuth: AppAuth,
) : ViewModel() {
    val data: Flow<List<Job>> = appAuth.authSateFlow.flatMapLatest { (myId, _) ->
        jobsRepository.data.map {
            JobModel()
            it.map { job ->
                job.copy(
                    ownedByMe = userId.value == myId
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
        edited.value?.let {job ->
            viewModelScope.launch {
                _state.postValue(StateModel(loading = true))
                try {
                    jobsRepository.editJob(job)
                    _state.postValue(StateModel())
                    _created.value = Unit
                } catch (e: Exception) {
                    e.printStackTrace()
                    _state.postValue(StateModel(error = true))
                }
            }
        }
        edited.value = emptyJob
    }

    fun changeJob(
        companyName: String,
        position: String,
        link: String?,
        start: String,
        finish: String?,
    ) {
        edited.value.let {
            val textCompany = companyName.trim()
            if (edited.value?.name != textCompany) {
                edited.value = edited.value?.copy(name = textCompany)
            }
            val textPosition = position.trim()
            if (edited.value?.position != textPosition) {
                edited.value = edited.value?.copy(position = textPosition)
            }
            val textLink = link?.trim()
            if (edited.value?.link != textLink) {
                edited.value = edited.value?.copy(link = textLink)
            }
            if (edited.value?.start != start) {
                edited.value = edited.value?.copy(start = start)
            }

            if (edited.value?.finish != finish) {
                edited.value = edited.value?.copy(finish = finish)

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

    fun refreshJobs(id: Long) = viewModelScope.launch {
        _state.postValue(StateModel(loading = true))
        try {
            jobsRepository.getJobsByUserId(id)
            _state.postValue(StateModel())
        } catch (e: Exception) {
            _state.postValue(StateModel(loading = true))
        }
    }

}