package com.taltal.poison.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coffee_log")
data class CoffeeLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int
)
