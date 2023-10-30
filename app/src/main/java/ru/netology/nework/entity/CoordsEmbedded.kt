package ru.netology.nework.entity

import ru.netology.nework.dto.Coordinates

data class CoordsEmbedded(
    val latitude: Double?,
    val longitude: Double?,
) {
    fun toDto() = Coordinates(latitude, longitude)

    companion object {
        fun fromDto(dto: Coordinates?) = dto?.let {
            CoordsEmbedded(latitude = it.lat, longitude = it.long)
        }
    }
}