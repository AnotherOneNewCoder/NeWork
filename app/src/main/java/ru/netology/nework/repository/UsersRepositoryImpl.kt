package ru.netology.nework.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.flow.Flow
import ru.netology.nework.dto.User

class UsersRepositoryImpl : UsersRepository {

    var usersList = listOf(
        User(
            id = 11L,
            login = "111",
            name = "Mark",
        ), User(
            id = 16L,
            login = "111",
            name = "Kostya",
            avatar = "https://yandex.ru/images/search?text=negjt+kbwj&pos=4&rpt=simage&img_url=https%3A%2F%2Fkartinkof.club%2Fuploads%2Fposts%2F2022-04%2F1649845723_1-kartinkof-club-p-rzhachnie-kartinki-glaza-1.jpg&from=tabbar&lr=213",
        ),
        User(
            id = 13L,
            login = "111",
            name = "Vera",
        ),
        User(
            id = 1112L,
            login = "111",
            name = "Nadya",
        ),
    )

    private val data = MutableLiveData(usersList)

    override fun getAll(): Flow<List<User>> = data.asFlow()
}