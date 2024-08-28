package com.taltal.poison.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.taltal.poison.ui.home.HomeScreen
import com.taltal.poison.ui.mypage.MyPageScreen
import com.taltal.poison.ui.onboard.OnBoardingScreen

@Composable
fun PoisonRootRoute(navController: NavHostController = rememberNavController()) {
    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = NavRoute.Home.direction,
    ) {
        composable(NavRoute.Onboarding.direction) {
            OnBoardingScreen(
                actionRoute = {
                    navController.navigate(it.direction)
                },
            )
        }

        composable(NavRoute.Home.direction) {
            HomeScreen()
        }

        composable(NavRoute.Calendar.direction) {
        }

        composable(NavRoute.MyPage.direction) {
            MyPageScreen()
        }
    }
}
