package com.taltal.poison.data.di

import android.content.Context
import androidx.room.Room
import com.taltal.poison.data.AppDatabase
import com.taltal.poison.data.api.CoffeeLogDao
import com.taltal.poison.util.SharedPrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "poison_database"
        ).build()
    }

    @Provides
    fun provideCoffeeIntakeDao(database: AppDatabase): CoffeeLogDao {
        return database.coffeeLogDao()
    }

    @Provides
    @Singleton
    fun provideSharedPrefManager(context: Context): SharedPrefManager {
        return SharedPrefManager(context)
    }
}