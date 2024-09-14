package com.taltal.poison.data.repository

import com.taltal.poison.data.api.CoffeeLogDao
import com.taltal.poison.data.api.PoisonApi
import com.taltal.poison.data.model.UpdatePoisonStatusRequest
import com.taltal.poison.data.model.UserRegisterRequest
import com.taltal.poison.data.model.UserRegisterResponse
import com.taltal.poison.util.SharedPrefManager
import javax.inject.Inject

class PoisonCoffeeRepository
    @Inject
    constructor(
        private val coffeeLogDao: CoffeeLogDao,
        private val poisonApi: PoisonApi,
        private val sharedPrefManager: SharedPrefManager
    ) {
        private val userId = sharedPrefManager.getUserId()

        suspend fun getCoffeeLogByMonth(
            year: Int,
            month: Int,
        ) = coffeeLogDao.getCoffeeLogByMonth(year, month)

        suspend fun getCoffeeLogByDay(
            year: Int,
            month: Int,
            day: Int,
        ) = coffeeLogDao.getCoffeeLogByDay(year, month, day)

        suspend fun checkNicknameDuplicate(nickname: String): Boolean = false

        suspend fun updatePoisonStatus() =
            poisonApi
                .updatePoisonStatus(
                    UpdatePoisonStatusRequest(userId),
                ).result

        suspend fun getToday() = poisonApi.getToday(userId).result

        suspend fun uploadUserStatus(
            nickname: String,
            height: Int,
            weight: Int,
            purpose: String,
            gender: String,
            birth: String,
            target: String,
            targetNum: Int,
        ): UserRegisterResponse =
            poisonApi
                .uploadUserStatus(
                    UserRegisterRequest(
                        nickname,
                        height,
                        weight,
                        purpose,
                        birth,
                        gender,
                        target,
                        targetNum,
                    ),
                ).result
    }
