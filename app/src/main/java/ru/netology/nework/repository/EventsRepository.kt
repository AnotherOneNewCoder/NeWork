package ru.netology.nework.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.nework.dto.Event

interface EventsRepository {
    fun getAll(): Flow<List<Event>>
}