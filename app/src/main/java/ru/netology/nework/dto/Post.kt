package ru.netology.nework.dto

data class Post(
    val id: Long,
    val authorId: Long,
    val author: String,
    val authorAvatar: String?,
    val authorJob: String?,
    val content: String,
    val published: String,
    val coords: Coordinates? = null,
    val link: String?,
    val likeOwnersId: Set<Long> = emptySet(),
    val mentionId: Set<Long> = emptySet(),
    val mentionedMe: Boolean = false,
    val likedByMe: Boolean = false,
    val attachment: Attachment? = null,
    val ownedByMe: Boolean = false,
)
