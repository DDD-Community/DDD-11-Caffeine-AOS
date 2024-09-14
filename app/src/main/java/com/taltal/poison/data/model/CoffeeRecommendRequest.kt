package com.taltal.poison.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CoffeeRecommendRequest(
    val birth: Int,
    val gender: String,
    val height: Int,
    val weight: Int,
)