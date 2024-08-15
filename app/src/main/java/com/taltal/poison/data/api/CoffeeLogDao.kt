package com.taltal.poison.data.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.taltal.poison.data.model.CoffeeLog

@Dao
interface CoffeeLogDao {
    // 데이터 삽입
    @Insert
    suspend fun insert(coffeeLog: CoffeeLog)

    // 특정 월의 데이터 가져오기
    @Query("SELECT * FROM coffee_log WHERE year = :year AND month = :month")
    suspend fun getCoffeeLogByMonth(year: Int, month: Int): List<CoffeeLog>

    // 특정 일의 데이터 가져오기
    @Query("SELECT * FROM coffee_log WHERE year = :year AND month = :month AND day = :day")
    suspend fun getCoffeeLogByDay(year: Int, month: Int, day: Int): List<CoffeeLog>

    // 전체 데이터 가져오기
    @Query("SELECT * FROM coffee_log")
    suspend fun getAllCoffeeLogs(): List<CoffeeLog>
}