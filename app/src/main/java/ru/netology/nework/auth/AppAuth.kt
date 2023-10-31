package ru.netology.nework.auth

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppAuth @Inject constructor(
    @ApplicationContext
    private val  context: Context,
) {

    private val pref = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    private val _authStateFlow: MutableStateFlow<AuthState>

    init {
        val id = pref.getLong(ID_KEY, 0)
        val token = pref.getString(TOKEN_KEY, null)

        if (id == 0L || token == null) {
            _authStateFlow = MutableStateFlow(AuthState())
            with(pref.edit()) {
                clear()
                apply()
            }
        } else {
            _authStateFlow = MutableStateFlow(AuthState(id, token))
        }
    }

    val authSateFlow: StateFlow<AuthState> = _authStateFlow.asStateFlow()

    @Synchronized
    fun setAuth(id: Long, token: String) {
        _authStateFlow.value = AuthState(id, token)
        with(pref.edit()) {
            putLong(ID_KEY, id)
            putString(TOKEN_KEY, token)
            apply()
        }
    }

    @Synchronized
    fun removeAuth() {
        _authStateFlow.value = AuthState()
        with(pref.edit()) {
            clear()
            commit()
        }
    }
    fun getToken(): String?{
        return pref.getString(TOKEN_KEY, null)
    }


    companion object {

        private const val ID_KEY = "ID_KEY"
        private const val TOKEN_KEY = "TOKEN_KEY"
        private var INSTANCE: AppAuth? = null

        //2
        fun getInstance(): AppAuth = requireNotNull(INSTANCE)

        // 1
        fun initApp(context: Context) {
            INSTANCE = AppAuth(context)

        }

    }

}

data class AuthState(
    val id: Long = 0,
    val token:String? = null,
)