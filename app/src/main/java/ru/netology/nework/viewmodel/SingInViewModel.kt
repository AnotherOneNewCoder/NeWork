package ru.netology.nework.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.netology.nework.api.UsersApiService
import ru.netology.nework.dto.Token
import ru.netology.nework.errors.ApiError
import ru.netology.nework.models.StateModel
import java.io.IOException

import javax.inject.Inject

@HiltViewModel
class SingInViewModel @Inject constructor(
    private val usersApiService: UsersApiService,

) : ViewModel() {
    val data = MutableLiveData<Token>()
    private val _state = MutableLiveData<StateModel>()
    val state: LiveData<StateModel>
        get() = _state
    fun singIn(login: String, passwd: String) {
        viewModelScope.launch {
            _state.postValue(StateModel(loading = true))
            try {


                val response = usersApiService.authUser(login = login, passwd = passwd)
                if (!response.isSuccessful) {
                    throw ApiError(response.message())
                }
                _state.postValue(StateModel())
                val body = response.body() ?: throw ApiError(response.message())
                data.value = Token(body.id, body.token)
            }
            catch (e: IOException) {
                _state.postValue(StateModel(error = true))
            }
            catch (e: Exception) {
                _state.postValue(StateModel(loginError = true))
            }
        }
    }
}