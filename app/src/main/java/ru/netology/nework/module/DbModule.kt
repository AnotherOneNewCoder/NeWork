package ru.netology.nework.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.netology.nework.db.AppDb
import javax.inject.Singleton

@InstallIn(Singleton::class)
@Module
object DbModule {
    @Singleton
    @Provides
    fun providedb(
        @ApplicationContext
        context: Context
    ): AppDb = Room.databaseBuilder(context, AppDb::class.java, "app.bd")
        .fallbackToDestructiveMigration()
        .build()

}