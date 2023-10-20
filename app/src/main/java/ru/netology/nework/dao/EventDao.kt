package ru.netology.nework.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.nework.entity.EventEntity


@Dao
interface EventDao {
    @Query("SELECT * FROM EventEntity ORDER BY id DESC")
    fun getAllEvents(): Flow<List<EventEntity>>
    @Query("SELECT * FROM EventEntity WHERE authorId= :authorId ORDER BY id DESC")
    fun getAllUserEvents(authorId: Long): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListEvents(events: List<EventEntity>)
    // Как сделать запрос с изменением Embedded?
//    @Query("UPDATE EventEntity SET content = :content, link = :link, attachment = :attachment, coords = :coords WHERE id = :id")
//    suspend fun updateEventById(id: Long, content: String, link: String, attachment: AttachmentEmbedded)
    @Query("UPDATE EventEntity SET content = :content, link = :link, datetime = :datetime WHERE id = :id")
    suspend fun updateEventById(id: Long, content: String,datetime: String, link: String?)

    suspend fun saveEvent(eventEntity: EventEntity) =
        if (eventEntity.id == 0L) insertEvent(eventEntity)
        else updateEventById(eventEntity.id, eventEntity.content, eventEntity.datetime,eventEntity.link)

    @Query("DELETE FROM EventEntity WHERE id = :id")
    suspend fun deleteEventById(id: Long)

    @Query("UPDATE EventEntity SET likedByMe = 1 WHERE id = :id AND likedByMe = 0")
    suspend fun likeEventById(id: Long)
    @Query("UPDATE EventEntity SET likedByMe = 0 WHERE id = :id AND likedByMe = 1")
    suspend fun unlikeEventById(id: Long)
    @Query("UPDATE EventEntity SET participatedByMe = 1 WHERE id = :id AND likedByMe = 0")
    suspend fun participateEventById(id: Long)
    @Query("UPDATE EventEntity SET participatedByMe = 0 WHERE id = :id AND likedByMe = 1")
    suspend fun dontParticipateEventById(id: Long)

//    @Query("UPDATE EventEntity SET `participantsIds` = `participantsIds` + 1 WHERE id = :id")
//    suspend fun mentionById(id: Long)
}