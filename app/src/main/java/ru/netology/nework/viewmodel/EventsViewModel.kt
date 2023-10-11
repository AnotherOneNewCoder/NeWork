package ru.netology.nework.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nework.repository.EventsRepository
import ru.netology.nework.repository.EventsRepositoryImpl

class EventsViewModel : ViewModel() {

    private val repository: EventsRepository = EventsRepositoryImpl()
    val data = repository.getAll()
}