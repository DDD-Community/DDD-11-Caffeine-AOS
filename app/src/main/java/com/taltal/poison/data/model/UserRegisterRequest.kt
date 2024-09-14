package com.taltal.poison.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRegisterRequest(
    val nickname: String,
    val height: Int,
    val weight: Int,
    val purpose: String,
    val birth: String,
    val gender: String,
    val target: String,
    val targetNums: Int
)