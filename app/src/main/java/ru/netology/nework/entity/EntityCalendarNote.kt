package ru.netology.nework.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nework.dto.CalendarNote

import ru.netology.nework.dto.TypeEvent


@Entity
data class EntityCalendarNote (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val currentUserId: Long,
    val author: String,
    val link: String?,
    val datetime: String,
    val type: TypeEvent,
) {
    fun toDto() = CalendarNote(
        id = id,
        currentUserId = currentUserId,
        author = author,
        link = link,
        datetime = datetime,
        type = type
    )

    companion object{
        fun fromDto(dto: CalendarNote) = EntityCalendarNote(
            id = dto.id,
            currentUserId = dto.currentUserId,
            author = dto.author,
            link = dto.link,
            datetime = dto.datetime,

            type = dto.type
        )
    }
}

fun List<EntityCalendarNote>.toCalendarNote() = map(EntityCalendarNote::toDto)
fun List<CalendarNote>.toEntity() = map(EntityCalendarNote::fromDto)