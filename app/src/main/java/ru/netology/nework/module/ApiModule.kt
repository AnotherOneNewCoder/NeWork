package ru.netology.nework.module

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.netology.nework.BuildConfig
import ru.netology.nework.api.EventsApiService
import ru.netology.nework.api.JobsApiService
import ru.netology.nework.api.MyWallApiService
import ru.netology.nework.api.PostsApiService
import ru.netology.nework.api.UsersApiService
import ru.netology.nework.api.WallApiService
import ru.netology.nework.auth.AppAuth
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    companion object {
        private const val BASE_URL = "${BuildConfig.BASE_URL}/api/"
    }

    @Singleton
    @Provides
    fun provideLogging(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BASIC
    }

    @Singleton
    @Provides
    fun provideOkhttp(
        logging: HttpLoggingInterceptor,
        appAuth: AppAuth,
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(logging)
        .addInterceptor{ chain ->
            appAuth.authSateFlow.value.token?.let { token->
                val newRequest = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", token)
                    .build()
                return@addInterceptor chain.proceed(newRequest)

            }
            chain.proceed(chain.request())
        }
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private var gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build()


    @Provides
    @Singleton
    fun provideEventsApiService(
        retrofit: Retrofit,
    ): EventsApiService = retrofit.create()
    @Provides
    @Singleton
    fun provideJobsApiService(
        retrofit: Retrofit,
    ): JobsApiService = retrofit.create()
    @Provides
    @Singleton
    fun provideMyWallApiService(
        retrofit: Retrofit,
    ): MyWallApiService = retrofit.create()
    @Provides
    @Singleton
    fun providePostsApiService(
        retrofit: Retrofit,
    ): PostsApiService = retrofit.create()

    @Provides
    @Singleton
    fun provideUsersApiService(
        retrofit: Retrofit,
    ): UsersApiService = retrofit.create()

    @Provides
    @Singleton
    fun provideWallApiService(
        retrofit: Retrofit,
    ): WallApiService = retrofit.create()



}