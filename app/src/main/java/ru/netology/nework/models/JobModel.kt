package ru.netology.nework.models

import ru.netology.nework.dto.Job

data class JobModel(
    val data: List<Job> = emptyList(),
    val empty: Boolean = false,
)
