package com.taltal.poison.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TodayResponse(
    val todayCount: Int,
    val targetCount: Int,
    val description: String,
    val imageJson: String
)

@Serializable
data class ImageResponse(val description: String)

@Serializable
data class UpdatePoisonStatusRequest(
    val userId: String
)
