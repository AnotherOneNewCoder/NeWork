package ru.netology.nework.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.netology.nework.api.UsersApiService
import ru.netology.nework.dto.User
import ru.netology.nework.entity.UserEntity
import ru.netology.nework.models.StateModel
import ru.netology.nework.repository.UsersRepository
import ru.netology.nework.repository.UsersRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val usersApiService: UsersApiService
): ViewModel() {
    val data: LiveData<List<User>> = usersRepository.data
        .asLiveData(Dispatchers.Default)

    private val _state = MutableLiveData<StateModel>()
    val state: LiveData<StateModel>
        get() = _state

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _usersIds = MutableLiveData<Set<Long>>()
    val usersIds: LiveData<Set<Long>>
        get() = _usersIds

    val userId: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }


    init {
        getAllUsers()
    }


    private fun getAllUsers() = viewModelScope.launch {
        _state.postValue(StateModel(loading = true))
        try {
            usersRepository.getAll()
            _state.postValue(StateModel())
        } catch (e: Exception) {
            _state.postValue(StateModel(error = true))
        }
    }
    fun getUserById(id: Long) = viewModelScope.launch {
        _state.postValue(StateModel(loading = true))
        try {
            val response = usersApiService.getUserById(id = id)
            if (response.isSuccessful) {
                _user.value = response.body()
            }
            _state.postValue(StateModel())
        } catch (e: Exception) {
            _state.postValue(StateModel(error = true))
        }
    }
    fun getUsersIds(set: Set<Long>) {
        viewModelScope.launch {
            _usersIds.value = set
        }
    }
    fun searchUser(searchQuery: String): LiveData<List<User>> {
        return usersRepository.searchUser(searchQuery).asLiveData()
    }
}