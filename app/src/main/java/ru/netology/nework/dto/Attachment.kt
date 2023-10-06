package ru.netology.nework.dto

data class Attachment(
    val url: String,
    val typeAttachment: TypeAttachment,
)

enum class TypeAttachment{
    IMAGE, VIDE, AUDIO
}
