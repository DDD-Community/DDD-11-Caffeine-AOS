package com.taltal.poison.data.api

import com.taltal.poison.data.model.BaseResponse
import com.taltal.poison.data.model.TodayResponse
import com.taltal.poison.data.model.UpdatePoisonStatusRequest
import com.taltal.poison.data.model.UserRegisterRequest
import com.taltal.poison.data.model.UserRegisterResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PoisonApi {
    @GET("api/v1/today")
    suspend fun getToday(
        @Query("userId") userId: String
    ): BaseResponse<TodayResponse>

    @POST("api/v1/record")
    suspend fun updatePoisonStatus(
        @Body updatePoisonStatusRequest: UpdatePoisonStatusRequest
    ): BaseResponse<TodayResponse>

    @POST("api/v1/onboarding")
    suspend fun uploadUserStatus(
        @Body userRegisterRequest: UserRegisterRequest
    ): BaseResponse<UserRegisterResponse>
}
