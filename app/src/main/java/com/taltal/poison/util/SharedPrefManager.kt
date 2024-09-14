package com.taltal.poison.util

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(
    context: Context,
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyApp", Context.MODE_PRIVATE)

    fun getLoginStatus(): Boolean = sharedPreferences.getBoolean(IS_LOGGED_IN, false)

    fun setUserData(
        id: String,
        nickname: String,
        isDailyGoal: Boolean,
        goalNumber: Int,
        forLogging: Boolean,
        gender: String,
        birthDay: String,
        height: String,
        weight: String,
    ) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_onboarding_finished", true)
        editor.putString(USER_ID, id)
        editor.putString(NICKNAME, nickname)
        editor.putBoolean(IS_DAILY_GOAL, isDailyGoal)
        editor.putBoolean(FOR_LOGGING, forLogging)
        editor.putInt(GOAL_NUMBER, goalNumber)
        editor.putBoolean(IS_LOGGED_IN, true)
        editor.putString(GENDER, gender)
        editor.putString(BIRTHDAY, birthDay)
        editor.putString(HEIGHT, height)
        editor.putString(WEIGHT, weight)
        editor.apply()
    }

    fun getNickName(): String = sharedPreferences.getString(NICKNAME, "") ?: ""

    fun getIsDailyGoal(): Boolean = sharedPreferences.getBoolean(IS_DAILY_GOAL, false)

    fun getGoalNumber(): Int = sharedPreferences.getInt(GOAL_NUMBER, 0)

    fun getForLogging(): Boolean = sharedPreferences.getBoolean(FOR_LOGGING, false)

    fun getGender(): String = sharedPreferences.getString(GENDER, "") ?: ""

    fun getBirthday(): String = sharedPreferences.getString(BIRTHDAY, "") ?: ""

    fun getHeight(): String = sharedPreferences.getString(HEIGHT, "") ?: ""

    fun getWeight(): String = sharedPreferences.getString(WEIGHT, "") ?: ""

    fun getUserId(): String = sharedPreferences.getString(USER_ID, "") ?: ""

    companion object {
        private const val USER_ID = "user_id"
        private const val IS_LOGGED_IN = "is_logged_in"
        private const val NICKNAME = "nickname"
        private const val FOR_LOGGING = "for_logging"
        private const val IS_DAILY_GOAL = "is_daily_goal"
        private const val GOAL_NUMBER = "goal_number"
        private const val GENDER = "gender"
        private const val BIRTHDAY = "birthday"
        private const val HEIGHT = "height"
        private const val WEIGHT = "weight"
    }
}
