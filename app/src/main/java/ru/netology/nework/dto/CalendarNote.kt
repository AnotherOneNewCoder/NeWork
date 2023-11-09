package ru.netology.nework.dto

data class CalendarNote(
    val id: Long,
    val currentUserId: Long,
    val author: String,
    val link: String?,
    val datetime: String,
    val type: TypeEvent,
)
