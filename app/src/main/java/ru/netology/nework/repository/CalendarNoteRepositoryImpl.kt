package ru.netology.nework.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

import ru.netology.nework.dao.CalendarNoteDao
import ru.netology.nework.dto.CalendarNote
import ru.netology.nework.entity.EntityCalendarNote
import ru.netology.nework.entity.toCalendarNote
import javax.inject.Inject

class CalendarNoteRepositoryImpl @Inject constructor(
    private val calendarNoteDao: CalendarNoteDao,
) : CalendarNoteRepository {
    override val data: Flow<List<CalendarNote>> = calendarNoteDao.getAllCalendarNotes().map {
        it.toCalendarNote()
    }.flowOn(Dispatchers.Default)




    override suspend fun getAll() {
        try {
            calendarNoteDao.getAllCalendarNotes()
        } catch (e: Exception) {
            e.printStackTrace()
            throw UnknownError()
        }
    }

    override suspend fun getAllCurrentUserCalendarNotes(id: Long) {
        try {
            calendarNoteDao.getAllCurrentUserCalendarNotes(id)
        } catch (e: Exception) {
            e.printStackTrace()
            throw UnknownError()
        }
    }

    override suspend fun saveCalendarNote(calendarNote: CalendarNote) {
        try {
            calendarNoteDao.saveCalendarNote(EntityCalendarNote.fromDto(calendarNote))
        } catch (e: Exception) {
            e.printStackTrace()
            throw UnknownError()
        }
    }

    override suspend fun removeCalendarNoteById(id: Long) {
        try {
            calendarNoteDao.deleteCalendarNoteById(id)
        } catch (e: Exception) {
            e.printStackTrace()
            throw UnknownError()
        }
    }
}