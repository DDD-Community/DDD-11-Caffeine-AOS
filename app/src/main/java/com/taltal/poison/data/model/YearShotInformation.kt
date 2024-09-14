package com.taltal.poison.data.model

import kotlinx.serialization.Serializable

@Serializable
data class YearShotInformation(
    val status: String,
    val code: String,
    val message: String,
    val result: Map<String, String>,
)
