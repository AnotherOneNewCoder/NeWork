package ru.netology.nework.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.netology.nework.auth.AppAuth
import ru.netology.nework.dto.CalendarNote
import ru.netology.nework.dto.TypeEvent
import ru.netology.nework.models.StateModel
import ru.netology.nework.repository.CalendarNoteRepository
import javax.inject.Inject


private val emptyCalendarNote = CalendarNote(
    id = 0,
    currentUserId = 0,
    author = "",
    link = null,
    datetime = "",
    type = TypeEvent.ONLINE,
)

@ExperimentalCoroutinesApi
@HiltViewModel
class CalendarNoteViewModel @Inject constructor(
    private val calendarNoteRepository: CalendarNoteRepository,
    private val appAuth: AppAuth,
): ViewModel() {


    val data: Flow<List<CalendarNote>> = calendarNoteRepository.data.flowOn(Dispatchers.Default)
    private val _state = MutableLiveData<StateModel>()
    val state: LiveData<StateModel>
        get() = _state
    val edited = MutableLiveData(emptyCalendarNote)


    fun saveCalendarNote() {
        edited.value?.let { calendarNote ->
            viewModelScope.launch {
                _state.postValue(StateModel(loading = true))
                try {
                    calendarNoteRepository.saveCalendarNote(calendarNote)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                edited.value = emptyCalendarNote
                _state.value = StateModel()
            }
        }
    }

    fun removeCalendarNoteById(id: Long) = viewModelScope.launch {
        try {
            _state.postValue(StateModel(loading = true))
            calendarNoteRepository.removeCalendarNoteById(id)
        } catch (e: Exception) {
            _state.value = StateModel(error = true)
        }
    }
    fun getAll() = viewModelScope.launch {
        try {
            _state.postValue(StateModel(loading = true))
            calendarNoteRepository.getAllCurrentUserCalendarNotes(appAuth.authSateFlow.value.id)
        } catch (e: Exception) {
            _state.value = StateModel(error = true)
        }
    }

}