package ru.netology.nework.repository

import kotlinx.coroutines.flow.Flow
import ru.netology.nework.dto.CalendarNote

interface CalendarNoteRepository {
    val data: Flow<List<CalendarNote>>

    suspend fun getAll()

    suspend fun getAllCurrentUserCalendarNotes(id: Long)

    suspend fun saveCalendarNote(calendarNote: CalendarNote)

    suspend fun removeCalendarNoteById(id: Long)
}