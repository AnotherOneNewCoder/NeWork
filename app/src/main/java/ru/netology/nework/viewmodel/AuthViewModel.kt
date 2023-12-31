package ru.netology.nework.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import ru.netology.nework.auth.AppAuth
import ru.netology.nework.auth.AuthState
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val appAuth: AppAuth
): ViewModel() {
    val data: LiveData<AuthState> = appAuth.authSateFlow.asLiveData(Dispatchers.Default)

    val isAuthorized: Boolean
        get() = appAuth.authSateFlow.value.id != 0L
}