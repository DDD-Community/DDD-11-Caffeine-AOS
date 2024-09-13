package com.taltal.poison.ui.designsystem

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

object PoisonTransition {
    fun slideEnterHorizontally() =
        slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(300),
        )

    fun slideExitHorizontally() =
        slideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = tween(300),
        )

    fun slidePopEnterHorizontally() =
        slideInHorizontally(
            initialOffsetX = { -it },
            animationSpec = tween(300),
        )

    fun slidePopExitHorizontally() =
        slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(300),
        )
}
