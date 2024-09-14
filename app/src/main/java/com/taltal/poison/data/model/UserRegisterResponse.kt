package com.taltal.poison.data.model

import kotlinx.serialization.Serializable

/**
 * "id": "66e522b6a80ea63eb069c533",
 *     "nickname": "test1",
 *     "height": 0,
 *     "weight": 0,
 *     "birth": "2024-09-14",
 *     "purpose": "CAFFEINE_REDUCE",
 *     "target": "DAILY",
 *     "targetNum": 10,
 *     "gender": "FEMALE",
 *     "notification": false,
 *     "token": null
 */
@Serializable
data class UserRegisterResponse(
    val id: String,
    val nickname: String,
    val height: Int,
    val weight: Int,
    val birth: String,
    val purpose: String,
    val targetNum: Int,
    val gender: String,
    val notification: Boolean,
    val token: String?
)