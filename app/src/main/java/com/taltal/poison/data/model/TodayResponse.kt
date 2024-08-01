package com.taltal.poison.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TodayResponse(
    val todayCount: Int,
    val targetCount: Int,
    val ratio: Double,
    val image: ImageResponse
)

@Serializable
data class ImageResponse(val description: String)
