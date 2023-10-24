package ru.netology.nework.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.netology.nework.repository.EventsRepository
import ru.netology.nework.repository.EventsRepositoryImpl
import ru.netology.nework.repository.PostsRepository
import ru.netology.nework.repository.PostsRepositoryImpl
import ru.netology.nework.repository.UsersRepository
import ru.netology.nework.repository.UsersRepositoryImpl
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
}