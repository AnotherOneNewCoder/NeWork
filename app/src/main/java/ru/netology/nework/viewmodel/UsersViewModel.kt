package ru.netology.nework.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nework.repository.UsersRepository
import ru.netology.nework.repository.UsersRepositoryImpl

class UsersViewModel: ViewModel() {
    private val repository: UsersRepository = UsersRepositoryImpl()
    val data = repository.getAll()
}