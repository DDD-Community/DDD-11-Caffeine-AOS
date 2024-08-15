package com.taltal.poison.data.repository

import com.taltal.poison.data.api.CoffeeLogDao
import com.taltal.poison.data.api.PoisonApi

class PoisonCoffeeRepository(
    private val coffeeLogDao: CoffeeLogDao,
    private val poisonApi: PoisonApi
) {
    suspend fun getCoffeeLogByMonth(year: Int, month: Int) =
        coffeeLogDao.getCoffeeLogByMonth(year, month)

    suspend fun getCoffeeLogByDay(year: Int, month: Int, day: Int) =
        coffeeLogDao.getCoffeeLogByDay(year, month, day)
}