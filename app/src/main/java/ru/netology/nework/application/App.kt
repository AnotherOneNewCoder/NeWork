package ru.netology.nework.application

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp
import okhttp3.internal.notifyAll

import ru.netology.nework.BuildConfig
import ru.netology.nework.auth.AppAuth
import javax.inject.Inject


@HiltAndroidApp

class App: Application() {


    @Inject
    lateinit var auth: AppAuth
    override fun onCreate() {
        MapKitFactory.setApiKey(BuildConfig.MAPS_API_KEY)
//        auth.notifyAll()
        super.onCreate()
    }
}


