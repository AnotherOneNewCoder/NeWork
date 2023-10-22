package ru.netology.nework.viewmodel


import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.netology.nework.api.UsersApiService
import ru.netology.nework.dto.ImageUpload
import ru.netology.nework.dto.Token
import ru.netology.nework.errors.ApiError

import ru.netology.nework.models.PhotoModel
import ru.netology.nework.models.StateModel
import java.io.IOException

import javax.inject.Inject

@HiltViewModel
class SingUpViewModel@Inject constructor(
    private val usersApiService: UsersApiService,
): ViewModel() {
    val data = MutableLiveData<Token>()
    private val _state = MutableLiveData<StateModel>()
    val state: LiveData<StateModel>
        get() = _state

    private val noPhoto = PhotoModel()
    private val _photo = MutableLiveData(noPhoto)
    val photo: LiveData<PhotoModel>
        get() = _photo
    fun singUp(login: String, passwd: String, name: String) {
        viewModelScope.launch {
            _state.postValue(StateModel(loading = true))
            try {


                val response = usersApiService.regUser(
                    login = login.toRequestBody("text/plain".toMediaType()),
                    passwd = passwd.toRequestBody("text/plain".toMediaType()),
                    name = name.toRequestBody("text/plain".toMediaType()),
                    photo.value?.uri?.toFile()?.let{
                        val upload = ImageUpload(it)

                        MultipartBody.Part.createFormData(
                            name = "file",
                            upload.file.name,
                            upload.file.asRequestBody()
                        )
                    }

                )
                if (!response.isSuccessful) {
                    throw ApiError(response.message())
                }
                _state.postValue(StateModel())
                val body = response.body() ?: throw ApiError(response.message())
                data.value = Token(body.id, body.token)
                _state.postValue(StateModel())
            }
            catch (e: IOException) {
                _state.postValue(StateModel(error = true))
            }
            catch (e: Exception) {
                throw UnknownError()
            }
        }
        _photo.value = noPhoto
    }

    fun setPhoto(uri: Uri?) {
        _photo.value = PhotoModel(uri)
    }
    fun clearPhoto() {
        _photo.value = noPhoto
    }
}