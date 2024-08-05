package com.taltal.poison.data.model

import kotlinx.serialization.Serializable

@Serializable
open class BaseResponse<T>(
    val status: String,
    val code: String,
    val message: String,
    val result: T
)
