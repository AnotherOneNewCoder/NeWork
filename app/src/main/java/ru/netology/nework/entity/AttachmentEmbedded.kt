package ru.netology.nework.entity

import ru.netology.nework.dto.Attachment
import ru.netology.nework.dto.TypeAttachment

data class AttachmentEmbedded(
    val url: String,
    val typeAttachment: TypeAttachment,
) {
    fun toDto() = Attachment(
        url = url,
        type = typeAttachment,
    )

    companion object{
        fun fromDto(dto: Attachment?) = dto?.let {
            AttachmentEmbedded(
                url = dto.url,
                typeAttachment = dto.type,
            )
        }
    }
}