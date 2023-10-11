package ru.netology.nework.dto

data class Event(
    val id: Long,
    val authorId: Long,
    val author: String,
    val authorAvatar: String?,
    val authorJob: String?,
    val content: String,
    val datetime: String,
    val published: String,
    val coords: Coordinates?,
    val type: TypeEvent,
    val likeOwnerIds: Set<Long> = emptySet(),
    val likedByMe: Boolean = false,
    val speakersIds: Set<Long> = emptySet(),
    val participantsIds: Set<Long> = emptySet(),
    val participatedByMe: Boolean = false,
    val attachment: Attachment? = null,
    val link: String?,
    val ownedByMe: Boolean = false,


    )




enum class TypeEvent{
    OFFLINE, ONLINE
}

