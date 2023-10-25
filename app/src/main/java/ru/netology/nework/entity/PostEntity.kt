package ru.netology.nework.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nework.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val authorId: Long,
    val author: String,
    val authorAvatar: String?,
    val authorJob: String?,
    val content: String,
    val published: String,
    val link: String?,
    val likeOwnersId: Set<Long> = emptySet(),
    val mentionId: Set<Long> = emptySet(),
    val mentionedMe: Boolean = false,
    val likedByMe: Boolean = false,
    val ownedByMe: Boolean = false,
    @Embedded
    val attachment: AttachmentEmbedded?,
    @Embedded
    val coords: CoordsEmbedded?,

) {
    fun toDto() = Post(
        id = id,
        authorId = authorId,
        author = author,
        authorAvatar = authorAvatar,
        authorJob = authorJob,
        content = content,
        published = published,
        link = link,
        likeOwnerIds = likeOwnersId,
        mentionIds = mentionId,
        mentionedMe = mentionedMe,
        likedByMe = likedByMe,
        ownedByMe = ownedByMe,
        attachment = attachment?.toDto(),
        coords = coords?.toDto()
    )

    companion object {
        fun fromDto(dto: Post) = PostEntity(
            id = dto.id,
            authorId = dto.authorId,
            author = dto.author,
            authorAvatar = dto.authorAvatar,
            authorJob = dto.authorJob,
            content = dto.content,
            published = dto.published,
            link = dto.link,
            likeOwnersId = dto.likeOwnerIds,
            mentionId = dto.mentionIds,
            mentionedMe = dto.mentionedMe,
            likedByMe = dto.likedByMe,
            ownedByMe = dto.ownedByMe,
            attachment = AttachmentEmbedded.fromDto(dto.attachment),
            coords = CoordsEmbedded.fromDto(dto.coords)
        )
    }
}

fun List<PostEntity>.toPost() = map(PostEntity::toDto)
fun List<Post>.toPostEntity() = map(PostEntity::fromDto)