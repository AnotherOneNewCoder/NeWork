package ru.netology.nework.entity

import androidx.room.Entity
import ru.netology.nework.dto.Job
@Entity
data class JobEntity(
    val id: Long,
    val name: String,
    val position: String,
    val start: String,
    val finish: String? = null,
    val link: String? = null,
) {
    fun toDto() = Job(
        id = id,
        name = name,
        position = position,
        start = start,
        finish = finish,
        link = link,
    )

    companion object{
        fun fromDto(dto: Job) = JobEntity(
            id = dto.id,
            name = dto.name,
            position = dto.position,
            start = dto.start,
            finish = dto.finish,
            link = dto.link,
        )
    }

}