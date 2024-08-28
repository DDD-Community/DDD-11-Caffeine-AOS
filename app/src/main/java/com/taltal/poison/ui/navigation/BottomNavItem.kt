package com.taltal.poison.ui.navigation

import com.taltal.poison.R

sealed class BottomNavItem(
    val title: Int,
    val icon: Int,
    val selectedIcon: Int,
    val screenRoute: String,
) {
    object Home : BottomNavItem(R.string.home, R.drawable.ic_home_24, R.drawable.ic_home_filled_24, HOME)

    object Calendar : BottomNavItem(
        R.string.calendar,
        R.drawable.ic_calendar_24,
        R.drawable.ic_calendar_filled_24,
        CALENDAR,
    )

    object MyPage : BottomNavItem(
        R.string.my_page,
        R.drawable.ic_mypage_24,
        R.drawable.ic_mypage_filled_24,
        MY_PAGE,
    )

    companion object {
        const val HOME = "HOME"
        const val MY_PAGE = "MY_PAGE"
        const val CALENDAR = "CALENDAR"
    }
}