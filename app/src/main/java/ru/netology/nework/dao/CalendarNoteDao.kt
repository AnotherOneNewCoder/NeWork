package ru.netology.nework.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.nework.entity.EntityCalendarNote



@Dao
interface CalendarNoteDao {
    @Query("SELECT * FROM EntityCalendarNote ORDER BY id DESC")
    fun getAllCalendarNotes(): Flow<List<EntityCalendarNote>>

    @Query("SELECT * FROM EntityCalendarNote WHERE currentUserId= :id ORDER BY id DESC")
    fun getAllCurrentUserCalendarNotes(id: Long): Flow<List<EntityCalendarNote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCalendarNote(calendarNote: EntityCalendarNote)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListCalendarNotes(events: List<EntityCalendarNote>)

    suspend fun saveCalendarNote(entityCalendarNote: EntityCalendarNote) = insertCalendarNote(entityCalendarNote)

    @Query("DELETE FROM EntityCalendarNote WHERE id = :id")
    suspend fun deleteCalendarNoteById(id: Long)
}