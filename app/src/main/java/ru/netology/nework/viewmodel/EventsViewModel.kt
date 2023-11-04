package ru.netology.nework.viewmodel

import android.net.Uri
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
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.netology.nework.api.EventsApiService
import ru.netology.nework.auth.AppAuth
import ru.netology.nework.db.AppDb
import ru.netology.nework.dto.Coordinates
import ru.netology.nework.dto.Event
import ru.netology.nework.dto.MediaUpload
import ru.netology.nework.dto.TypeAttachment
import ru.netology.nework.dto.TypeEvent
import ru.netology.nework.errors.UnknownError
import ru.netology.nework.models.MediaModel
import ru.netology.nework.models.StateModel
import ru.netology.nework.repository.EventsRepository
import ru.netology.nework.repository.EventsRepositoryImpl
import ru.netology.nework.utils.SingleLiveEvent
import java.io.InputStream
import javax.inject.Inject

private val emptyEvent = Event(
    id = 0,
    authorId = 0,
    author = "",
    authorAvatar = "",
    authorJob = "",
    content = "",
    datetime = "2023-02-01T12:00:00.000000Z",
    published = "2023-02-01T12:00:00.000000Z",
    type = TypeEvent.ONLINE,
    likeOwnerIds = emptySet(),
)

private val emptyMedia = MediaModel()

@ExperimentalCoroutinesApi
@HiltViewModel
class EventsViewModel @Inject constructor(
    private val eventsRepository: EventsRepository,
    private val appAuth: AppAuth,
) : ViewModel() {

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
    @OptIn(ExperimentalCoroutinesApi::class)
//val data: LiveData<List<Event>> = appAuth.authSateFlow.flatMapLatest { (myId,_) ->
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

//    val userData: MutableLiveData<List<Event>>()
    private val _state = MutableLiveData<StateModel>()
    val state: LiveData<StateModel>
        get() = _state

    val edited = MutableLiveData(emptyEvent)
    private val _media = MutableLiveData(emptyMedia)
    val media: LiveData<MediaModel>
        get() = _media

    private val _eventCreated = SingleLiveEvent<Unit>()
    val eventCreated: LiveData<Unit>
        get() = _eventCreated




    init {
        getAllEvents()
    }

    fun saveEvent() {
        edited.value?.let { event ->

            viewModelScope.launch {
                _state.postValue(StateModel(loading = true))
                try {
                    when (_media.value) {
                        emptyMedia -> {
                            eventsRepository.saveEvent(event)
                        }

                        else -> {
                            _media.value?.inputStream?.let {
                                MediaUpload(it)
                            }?.let {
                                eventsRepository.saveWithAttachments(event, it)
                            }
                        }


                    }
                    _eventCreated.value = Unit
                    _state.value = StateModel()
                    edited.value = emptyEvent
                    _media.value = emptyMedia

                } catch (e: Exception) {
                    e.printStackTrace()
                    throw UnknownError()
                }
            }

        }

    }



    fun changeEventWithContent(
        content: String,
        date: String,
        coordinates: Coordinates?,
        link: String?
    ) {
        edited.value?.let {
            val text = content.trim()

            if (edited.value?.content != text) {
                edited.value = edited.value?.copy(content = text)
            }
            if (edited.value?.datetime != date) {
                edited.value = edited.value?.copy(datetime = date)
            }
            if (edited.value?.coords != coordinates) {
                edited.value = edited.value?.copy(coords = coordinates)
            }
            val link = link?.trim()
            if (edited.value?.link != link) {
                edited.value = edited.value?.copy(link = link)
            }
        }
    }

    fun setSpeaker(id: Long) {
        if (edited.value?.speakerIds?.contains(id) == false) {
            edited.value = edited.value?.speakerIds?.plus(id)?.let {
                edited.value?.copy(speakerIds = it)
            }
        }
    }



    fun changeMedia(
        uri: Uri?,
        inputStream: InputStream?,
        type: TypeAttachment?,
    ) {
        _media.value = MediaModel(uri = uri, inputStream = inputStream, type = type)
    }

    fun removeEventById(id: Long) = viewModelScope.launch {
        try {
            eventsRepository.removeById(id)
        } catch (e: Exception) {
            _state.value = StateModel(error = true)
        }
    }

    fun edit(event: Event) {
        edited.value = event
    }

    private fun getAllEvents() = viewModelScope.launch {
        _state.postValue(StateModel(loading = true))
        try {
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
        try {
            eventsRepository.getAll()
            _state.postValue(StateModel())
        } catch (e: Exception) {
            _state.postValue(StateModel(loading = true))
        }
    }







}