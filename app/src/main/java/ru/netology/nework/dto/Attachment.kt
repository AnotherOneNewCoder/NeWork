package ru.netology.nework.dto

data class Attachment(
    val url: String,
    //val typeAttachment: TypeAttachment,
    val type: TypeAttachment,
)

enum class TypeAttachment{
    IMAGE, VIDEO, AUDIO
}
