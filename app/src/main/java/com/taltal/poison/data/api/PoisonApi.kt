package com.taltal.poison.data.api

import com.taltal.poison.data.model.BaseResponse
import com.taltal.poison.data.model.TodayResponse
import retrofit2.http.GET

interface PoisonApi {
    @GET("/today")
    suspend fun getToday(): BaseResponse<TodayResponse>
}
