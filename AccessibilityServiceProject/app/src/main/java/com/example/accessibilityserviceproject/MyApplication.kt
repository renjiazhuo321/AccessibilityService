package com.example.accessibilityserviceproject

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication:Application() {
    companion object {
        lateinit var instance: MyApplication
    }

    fun getInstance(): MyApplication {
        return instance
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        XLog.init(LogLevel.ALL)
    }


}
// SettingsDataStore.kt
object DataStore {
    // 创建DataStore
    private val MyApplication.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "grabTickets"
    )
    // 对外开放的DataStore变量
    val dataStore = MyApplication.instance.dataStore
}
