package ru.netology.nework.module

import android.content.Context

import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.netology.nework.dao.CalendarNoteDao
import ru.netology.nework.dao.EventDao
import ru.netology.nework.dao.JobDao
import ru.netology.nework.dao.PostDao
import ru.netology.nework.dao.UserDao
import ru.netology.nework.db.AppDb
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DbModule {
    @Singleton
    @Provides
    fun provideDb(
        @ApplicationContext
        context: Context
    ): AppDb = Room.databaseBuilder(context, AppDb::class.java, "app.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun providePostDao(
        appDb: AppDb
    ): PostDao = appDb.postDao()

    @Provides
    fun provideEventDao(
        appDb: AppDb
    ): EventDao = appDb.eventDao()

    @Provides
    fun provideUserDao(
        appDb: AppDb
    ): UserDao = appDb.userDao()

    @Provides
    fun provideJobDao(
        appDb: AppDb
    ): JobDao = appDb.jobDao()

    @Provides
    fun provideCalendarNoteDao(
        appDb: AppDb
    ): CalendarNoteDao = appDb.calendarNoteDao()

}