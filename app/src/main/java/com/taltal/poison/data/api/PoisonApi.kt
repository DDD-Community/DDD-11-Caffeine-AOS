package com.taltal.poison.data.api

import com.taltal.poison.data.model.BaseResponse
import com.taltal.poison.data.model.DailyShotInformation
import com.taltal.poison.data.model.TodayResponse
import com.taltal.poison.data.model.UpdatePoisonStatusRequest
import com.taltal.poison.data.model.UserRegisterRequest
import com.taltal.poison.data.model.UserRegisterResponse
import com.taltal.poison.data.model.YearShotInformation
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PoisonApi {
    @GET("api/v1/today")
    suspend fun getToday(
        @Query("userId") userId: String,
    ): BaseResponse<TodayResponse>

    @POST("api/v1/record")
    suspend fun updatePoisonStatus(
        @Body updatePoisonStatusRequest: UpdatePoisonStatusRequest,
    ): BaseResponse<TodayResponse>

    @POST("api/v1/onboarding")
    suspend fun uploadUserStatus(
        @Body userRegisterRequest: UserRegisterRequest,
    ): BaseResponse<UserRegisterResponse>

    @GET("api/v1/recommend")
    suspend fun getRecommendCaffeineIntake(
        @Query("gender") gender: String,
        @Query("birth") birth: Int,
        @Query("height") height: Int,
        @Query("weight") weight: Int,
    ): BaseResponse<Int>

    @GET("api/v1/enter")
    suspend fun enter(
        @Query("userId") userId: String,
    ): BaseResponse<String>

    @GET("api/v1/calendar/day")
    suspend fun getDailyShotInformation(
        @Query("userId") userId: String,
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("day") day: Int,
    ): BaseResponse<DailyShotInformation>

    @GET("api/v1/calendar/month")
    suspend fun getCalendarState(
        @Query("userId") userId: String,
    ): YearShotInformation
}
