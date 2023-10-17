package ru.netology.nework.entity

import ru.netology.nework.dto.Event
import ru.netology.nework.dto.TypeEvent

data class EventTypeEmbedded(
    val type: String,
) {
    fun toDto() = TypeEvent.valueOf(type)

    companion object{
        fun fromDto(dto: TypeEvent) = EventTypeEmbedded(dto.name)
    }
}