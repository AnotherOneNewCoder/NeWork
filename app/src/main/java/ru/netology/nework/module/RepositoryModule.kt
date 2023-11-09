package ru.netology.nework.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.netology.nework.repository.CalendarNoteRepository
import ru.netology.nework.repository.CalendarNoteRepositoryImpl
import ru.netology.nework.repository.EventsRepository
import ru.netology.nework.repository.EventsRepositoryImpl
import ru.netology.nework.repository.JobsRepository
import ru.netology.nework.repository.JobsRepositoryImpl
import ru.netology.nework.repository.PostsRepository
import ru.netology.nework.repository.PostsRepositoryImpl
import ru.netology.nework.repository.UsersRepository
import ru.netology.nework.repository.UsersRepositoryImpl
import ru.netology.nework.repository.WallRepository
import ru.netology.nework.repository.WallRepositoryImpl
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @Singleton
    @Binds
    fun bindsUserRepository(impl: UsersRepositoryImpl): UsersRepository

    @Singleton
    @Binds
    fun bindsEventsRepository(impl: EventsRepositoryImpl): EventsRepository

    @Singleton
    @Binds
    fun bindPostsRepository(impl: PostsRepositoryImpl): PostsRepository

    @Singleton
    @Binds
    fun bindJobRepository(impl: JobsRepositoryImpl): JobsRepository


    @Singleton
    @Binds
    fun bindWallRepository(impl: WallRepositoryImpl): WallRepository

    @Singleton
    @Binds
    fun bindCalendarNoteRepository(impl: CalendarNoteRepositoryImpl): CalendarNoteRepository
}