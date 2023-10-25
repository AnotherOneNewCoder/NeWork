package ru.netology.nework.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.netology.nework.api.EventsApiService
import ru.netology.nework.auth.AppAuth
import ru.netology.nework.db.AppDb
import ru.netology.nework.dto.Event
import ru.netology.nework.dto.TypeEvent
import ru.netology.nework.models.MediaModel
import ru.netology.nework.models.StateModel
import ru.netology.nework.repository.EventsRepository
import ru.netology.nework.repository.EventsRepositoryImpl
import javax.inject.Inject
private val emptyEvent= Event(
    id = 0,
    authorId = 0,
    author = "",
    authorAvatar = "",
    authorJob = "",
    content = "",
    datetime = "",
    published = "",
    type = TypeEvent.ONLINE,
    likeOwnerIds = emptySet(),
)

private val emptyMedia = MediaModel()


@HiltViewModel
class EventsViewModel @Inject constructor(
    private val eventsRepository: EventsRepository,
    private val eventsApiService: EventsApiService,

    appAuth: AppAuth
): ViewModel() {

//    val data: LiveData<List<Event>> = appAuth.authSateFlow.flatMapLatest { (myId,_) ->
//        eventsRepository.data.asLiveData().map { events->
//            events.map { event -> event.copy(
//                ownedByMe = event.authorId == myId,
//                likedByMe = event.likeOwnerIds.contains(myId),
//                participatedByMe = event.participantsIds.contains(myId))
//
//            }
//
//        }.asFlow()
//    }.asLiveData(Dispatchers.Default)
//    val data: LiveData<List<Event>> = appAuth.authSateFlow.flatMapLatest { (myId,_) ->
//        eventsRepository.data.map { events->
//            events.map { event -> event.copy(
//                ownedByMe = event.authorId == myId,
//                likedByMe = event.likeOwnerIds.contains(myId),
//                participatedByMe = event.participantsIds.contains(myId))
//
//            }
//
//        }
//    }.asLiveData(Dispatchers.Default)


    val data: LiveData<List<Event>> = eventsRepository.data.asLiveData(Dispatchers.Default)





    private val _state = MutableLiveData<StateModel>()
    val state: LiveData<StateModel>
        get() = _state
    init {
        getAllEvents()
    }

    private fun getAllEvents() = viewModelScope.launch {
        _state.postValue(StateModel(loading = true))
        try{
            eventsRepository.getAll()
            _state.postValue(StateModel())
        } catch (e: Exception) {
            _state.postValue(StateModel(loading = true))
        }
    }




    fun likeById(id: Long) = viewModelScope.launch {
        try {
            eventsRepository.likeById(id)
        } catch (e: Exception) {
            _state.value = StateModel(error = true)
        }
    }
    fun unlikeById(id: Long) = viewModelScope.launch {
        try {
            eventsRepository.unlikeById(id)
        } catch (e: Exception) {
            _state.value = StateModel(error = true)
        }
    }
    fun participateById(id: Long) = viewModelScope.launch {
        try {
            eventsRepository.participate(id)
        } catch (e: Exception) {
            _state.value = StateModel(error = true)
        }
    }
    fun dontParticipateById(id: Long) = viewModelScope.launch {
        try {
            eventsRepository.dontParticipate(id)
        } catch (e: Exception) {
            _state.value = StateModel(error = true)
        }
    }
    fun refreshEvents() = viewModelScope.launch {
        _state.postValue(StateModel(loading = true))
        try{
            eventsRepository.getAll()
            _state.postValue(StateModel())
        } catch (e: Exception) {
            _state.postValue(StateModel(loading = true))
        }
    }
}