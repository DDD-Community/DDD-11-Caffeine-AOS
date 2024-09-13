package com.taltal.poison.ui.calendar.model

import androidx.compose.ui.graphics.Color
import com.taltal.poison.R
import com.taltal.poison.ui.theme.taltal_red_50
import com.taltal.poison.ui.theme.taltal_yellow_60

enum class PoisonState {
    None {
        override fun getCalendarDayBackgrountResId() = error("일로오면 안됨용!!")
        override fun getDailyCoffeeImageResId() = error("일로오면 안됨용!!")
        override fun getColor()  = error("일로오면 안됨용!!")
    },
    Empty {
        override fun getCalendarDayBackgrountResId() = R.drawable.poe_calendar_empty
        override fun getDailyCoffeeImageResId() = R.drawable.img_coffee_clear
        override fun getColor(): Color = taltal_yellow_60
    },
    Success {
        override fun getCalendarDayBackgrountResId() = R.drawable.poe_calendar_success
        override fun getDailyCoffeeImageResId() = R.drawable.img_coffee_success
        override fun getColor(): Color = taltal_yellow_60
    },
    Fail {
        override fun getCalendarDayBackgrountResId() = R.drawable.poe_calendar_fail
        override fun getDailyCoffeeImageResId() = R.drawable.img_coffee_fail
        override fun getColor(): Color = taltal_red_50
    };

    abstract fun getCalendarDayBackgrountResId(): Int
    abstract fun getDailyCoffeeImageResId(): Int
    abstract fun getColor(): Color
}