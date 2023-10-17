package ru.netology.nework.entity

import ru.netology.nework.dto.Coordinates

data class CoordsEmbedded(
    val lat: String?,
    val long: String?,
) {
    fun toDto() = Coordinates(
        lat = lat,
        long = long,
    )
    companion object{
        fun fromDto(dto: Coordinates?) = dto?.let { CoordsEmbedded(
            lat = it.lat,
            long = it.long,
            ) }
    }
}