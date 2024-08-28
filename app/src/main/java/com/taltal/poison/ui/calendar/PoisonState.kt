package com.taltal.poison.ui.calendar

import com.taltal.poison.R

enum class PoisonState {
    None {
        override fun getCalendarDayBackgrountResId() = error("일로오면 안됨용!!")
        override fun getDailyCoffeeImageResId() = error("일로오면 안됨용!!")
    },
    Empty {
        override fun getCalendarDayBackgrountResId() = R.drawable.poe_calendar_empty
        override fun getDailyCoffeeImageResId() = R.drawable.img_coffee_clear
    },
    Success {
        override fun getCalendarDayBackgrountResId() = R.drawable.poe_calendar_success
        override fun getDailyCoffeeImageResId() = R.drawable.img_coffee_success
    },
    Fail {
        override fun getCalendarDayBackgrountResId() = R.drawable.poe_calendar_fail
        override fun getDailyCoffeeImageResId() = R.drawable.img_coffee_fail
    };

    abstract fun getCalendarDayBackgrountResId(): Int
    abstract fun getDailyCoffeeImageResId(): Int
}