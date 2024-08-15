package com.taltal.poison.data.repository

import com.taltal.poison.data.api.CoffeeLogDao
import com.taltal.poison.data.api.PoisonApi
import javax.inject.Inject

class PoisonCoffeeRepository @Inject constructor(
    private val coffeeLogDao: CoffeeLogDao,
    private val poisonApi: PoisonApi
) {
    suspend fun getCoffeeLogByMonth(year: Int, month: Int) =
        coffeeLogDao.getCoffeeLogByMonth(year, month)

    suspend fun getCoffeeLogByDay(year: Int, month: Int, day: Int) =
        coffeeLogDao.getCoffeeLogByDay(year, month, day)

    suspend fun checkNicknameDuplicate(nickname: String): Boolean = false
        //poisonApi.checkNicknameDuplicate(nickname)
}