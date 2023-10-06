package ru.netology.nework.application

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import ru.netology.nework.BuildConfig


class App: Application() {
    override fun onCreate() {
        MapKitFactory.setApiKey(BuildConfig.MAPS_API_KEY)
        super.onCreate()
    }
}