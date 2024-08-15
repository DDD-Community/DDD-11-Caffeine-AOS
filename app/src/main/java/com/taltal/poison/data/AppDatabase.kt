package com.taltal.poison.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.taltal.poison.data.api.CoffeeLogDao
import com.taltal.poison.data.model.CoffeeLog

@Database(entities = [CoffeeLog::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coffeeLogDao(): CoffeeLogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "poison_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}