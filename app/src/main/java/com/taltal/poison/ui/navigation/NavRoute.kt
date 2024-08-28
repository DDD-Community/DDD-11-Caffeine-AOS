package com.taltal.poison.ui.navigation

sealed class NavRoute(
    val direction: String,
) {
    data object Onboarding : NavRoute(
        NavDirection.ROUTE_ONBOARDING,
    )

    data object Home : NavRoute(
        NavDirection.ROUTE_HOME,
    )

    data object Calendar : NavRoute(
        NavDirection.ROUTE_CALENDAR,
    )

    data object MyPage : NavRoute(
        NavDirection.ROUTE_MY_PAGE,
    )
}
