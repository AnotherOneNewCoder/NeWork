package ru.netology.nework.dto

data class Job(
    val id: Long = 0,
    val name: String = "",
    val position: String = "",
    val start: String = "",
    val finish: String? = null,
    val link: String? = null,
//    val ownedByMe: Boolean = false,
)
