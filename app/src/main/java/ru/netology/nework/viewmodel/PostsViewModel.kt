package ru.netology.nework.viewmodel

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.netology.nework.api.PostsApiService
import ru.netology.nework.auth.AppAuth
import ru.netology.nework.dto.MediaUpload
import ru.netology.nework.dto.Post
import ru.netology.nework.dto.TypeAttachment
import ru.netology.nework.errors.UnknownError
import ru.netology.nework.models.MediaModel
import ru.netology.nework.models.StateModel
import ru.netology.nework.repository.PostsRepository
import ru.netology.nework.repository.PostsRepositoryImpl
import ru.netology.nework.utils.SingleLiveEvent
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

private val empty = Post(
    id = 0,
    authorId = 0,
    author = "",
    authorAvatar = "",
    content = "",
    published = "2023-01-27T17:00:00.000Z",
    mentionedMe = false,
    likedByMe = false,
)

private val noMedia = MediaModel()

@ExperimentalCoroutinesApi
@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsRepository: PostsRepository,
    appAuth: AppAuth,
) : ViewModel() {

    //    val data: LiveData<List<Post>> = postsRepository.data.asLiveData(Dispatchers.Default)
    val data: LiveData<List<Post>> = appAuth.authSateFlow.flatMapLatest { (myId, _) ->
        postsRepository.data.map { posts ->
            posts.map { post ->
                post.copy(
                    mentionedMe = post.mentionIds.contains(myId),
                    likedByMe = post.likeOwnerIds.contains(myId),
                    ownedByMe = post.authorId == myId,
                    )
            }
        }
    }.asLiveData(Dispatchers.Default)

    private val _state = MutableLiveData<StateModel>()
    val state: LiveData<StateModel>
        get() = _state

    val edited = MutableLiveData(empty)

    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

    private val _media = MutableLiveData(noMedia)
    val media: LiveData<MediaModel>
        get() = _media

    init {
        getAllPosts()
    }

    private fun getAllPosts() = viewModelScope.launch {
        _state.postValue(StateModel(loading = true))
        try {
            postsRepository.getAll()
            _state.postValue(StateModel())
        } catch (e: Exception) {
            _state.postValue(StateModel(loading = true))
        }
    }

    fun refreshEvents() = viewModelScope.launch {
        _state.postValue(StateModel(loading = true))
        try {
            postsRepository.getAll()
            _state.postValue(StateModel())
        } catch (e: Exception) {
            _state.postValue(StateModel(loading = true))
        }
    }

    fun savePost() {
        edited.value?.let { post ->
            viewModelScope.launch {
                _state.postValue(StateModel(loading = true))
                try {
                    when (_media.value) {
                        noMedia -> {
                            postsRepository.savePost(post)
                        }

                        else -> {
                            _media.value?.inputStream?.let {
                                MediaUpload(it)
                            }?.let {
                                postsRepository.saveWithAttach(post, it, _media.value?.type!!)
                            }
                        }
                    }
                    _state.postValue(StateModel())
                    _postCreated.value = Unit
                } catch (e: IOException) {
                    _state.postValue(StateModel(error = true))
                } catch (e: Exception) {
                    throw UnknownError()
                }
            }
        }
        edited.value = empty
        _media.value = noMedia
    }

    fun changePostContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (edited.value?.content != text) {
                edited.value = edited.value?.copy(content = text)
            }
        }
    }

    fun changeMedia(
        uri: Uri?,
        inputStream: InputStream?,
        type: TypeAttachment?,
    ) {
        _media.value = MediaModel(
            uri = uri,
            inputStream = inputStream,
            type = type,
        )
    }

    fun removeById(id: Long) = viewModelScope.launch {
        try {
            postsRepository.removeById(id)

        } catch (e: IOException) {
            _state.postValue(StateModel(error = true))
        } catch (e: Exception) {
            throw UnknownError()
        }
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun likeById(id: Long) = viewModelScope.launch {
        try {
            postsRepository.likeById(id)
        } catch (e: Exception) {
            _state.value = StateModel(error = true)
        }
    }

    fun unlikeById(id: Long) = viewModelScope.launch {
        try {
            postsRepository.unlikeById(id)
        } catch (e: Exception) {
            _state.value = StateModel(error = true)
        }
    }

    fun setMentionIds(id: Long) {
        edited.value?.let {
            if (edited.value?.mentionIds?.contains(id) == false) {
                edited.value = edited.value?.copy(
                    mentionIds = it.mentionIds.plus(id)
                )
            }
        }
    }


}