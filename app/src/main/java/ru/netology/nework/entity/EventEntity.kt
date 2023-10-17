package ru.netology.nework.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nework.dto.Event

import ru.netology.nework.dto.TypeEvent
@Entity
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val authorId: Long,
    val author: String,
    val authorAvatar: String? = null,
    val authorJob: String?,
    val content: String,
    val datetime: String,
    val published: String,
    @Embedded
    val type: EventTypeEmbedded,
    val likeOwnerIds: Set<Long> = emptySet(),
    val likedByMe: Boolean = false,
    val speakersIds: Set<Long> = emptySet(),
    val participantsIds: Set<Long> = emptySet(),
    val link: String?,
    val ownedByMe: Boolean = false,
    val participatedByMe: Boolean = false,
    @Embedded
    val coords: CoordsEmbedded? = null,
    @Embedded
    val attachment: AttachmentEmbedded? = null,

) {
    fun toDto() = Event(
        id = id,
        authorId = authorId,
        author = author,
        authorAvatar = authorAvatar,
        authorJob = authorJob,
        content = content,
        datetime = datetime,
        published = published,
        type = type.toDto(),
        likeOwnerIds = likeOwnerIds,
        likedByMe = likedByMe,
        speakersIds = speakersIds,
        participatedByMe = participatedByMe,
        participantsIds = participantsIds,
        link = link,
        ownedByMe = ownedByMe,
        coords = coords?.toDto(),
        attachment = attachment?.toDto()
    )

    companion object{
        fun fromDto(dto: Event) = EventEntity(
            id = dto.id,
            authorId = dto.authorId,
            author = dto.author,
            authorAvatar = dto.authorAvatar,
            authorJob = dto.authorJob,
            content = dto.content,
            datetime = dto.datetime,
            published = dto.published,
            type = EventTypeEmbedded.fromDto(dto.type),
            likeOwnerIds = dto.likeOwnerIds,
            likedByMe = dto.likedByMe,
            speakersIds = dto.speakersIds,
            participatedByMe = dto.participatedByMe,
            participantsIds = dto.participantsIds,
            link = dto.link,
            ownedByMe = dto.ownedByMe,
            coords = CoordsEmbedded.fromDto(dto.coords),
            attachment = AttachmentEmbedded.fromDto(dto.attachment),
        )
    }
}

fun List<EventEntity>.toEvent() = map(EventEntity::toDto)
fun List<Event>.toEventEntity() = map(EventEntity::fromDto)