package com.taltal.poison.util

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(
    context: Context,
) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyApp", Context.MODE_PRIVATE)

    fun getLoginStatus(): Boolean = sharedPreferences.getBoolean(IS_LOGINED, false)

    fun setIsOnBoardingFinished() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_onboarding_finished", true)
        editor.apply()
    }

    companion object {
        private const val IS_LOGINED = "is_logined"
    }
}
