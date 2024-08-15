package com.taltal.poison

import android.app.Application
import com.taltal.poison.data.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TalTalApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}
