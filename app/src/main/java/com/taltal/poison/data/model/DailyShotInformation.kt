package com.taltal.poison.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DailyShotInformation(
    val shot: Int,
    val poisonRecord: List<PoisonRecord>
)

@Serializable
data class PoisonRecord(
    val dateTime: String,
    val shot: Int
)