package ru.netology.nework.utils

import androidx.room.TypeConverter
import ru.netology.nework.dto.TypeAttachment

class Converters {
    @TypeConverter
    fun toTypeAttachment(value: String) = enumValueOf<TypeAttachment>(value)

    @TypeConverter
    fun formTypeAttachment(value: TypeAttachment) = value.name

    @TypeConverter
    fun fromSet(set: Set<Long>): String = set.joinToString("-")

    @TypeConverter
    fun toSet(data: String): Set<Long> =
        if (data.isBlank()) emptySet()
        else data.split("-").map { it.toLong() }.toSet()
}