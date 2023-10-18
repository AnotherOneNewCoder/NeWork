package ru.netology.nework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.netology.nework.dao.EventDao
import ru.netology.nework.dao.JobDao
import ru.netology.nework.dao.PostDao
import ru.netology.nework.dao.UserDao
import ru.netology.nework.entity.EventEntity
import ru.netology.nework.entity.JobEntity
import ru.netology.nework.entity.PostEntity
import ru.netology.nework.entity.UserEntity
import ru.netology.nework.utils.Converters

@Database(
    entities = [
        UserEntity::class,
        JobEntity::class,
        PostEntity::class,
        EventEntity::class,
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDb() : RoomDatabase() {
    abstract fun postDao(): PostDao

    abstract fun eventDao(): EventDao

    abstract fun userDao(): UserDao
    abstract fun jobDao(): JobDao

}