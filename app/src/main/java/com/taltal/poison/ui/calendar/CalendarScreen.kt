package com.taltal.poison.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.taltal.poison.R
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.YearMonth

@Composable
fun CalendarScreen(adjacentMonths: Long = 12) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(adjacentMonths) }
    val endMonth = remember { currentMonth.plusMonths(adjacentMonths) }
    var selection by remember { mutableStateOf<CalendarDay?>(null) }
    val daysOfWeek = remember { DayOfWeek.entries }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first(),
        )
        val coroutineScope = rememberCoroutineScope()
        val visibleMonth = rememberFirstMostVisibleMonth(state, viewportPercent = 90f)

        CalendarHeader()
        CalendarTitle(
            modifier = Modifier.fillMaxWidth(),
            currentMonth = visibleMonth.yearMonth,
            arrowLeftImageResId = R.drawable.arrow_left,
            arrowRightImageResId = R.drawable.arrow_right,
            goToPrevious = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                }
            },
            goToNext = {
                coroutineScope.launch {
                    state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                }
            },
        )
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalCalendar(
            modifier = Modifier.padding(horizontal = 20.dp),
            state = state,
            dayContent = { day ->
                val poisonState = remember { PoisonState.entries.random() }
                Day(
                    day = day,
                    poisonState = poisonState,
                    isSelected = selection?.equals(day) == true,
                    onClick = { clicked -> selection = clicked }
                )
            },
            monthHeader = {
                MonthHeader(daysOfWeek = daysOfWeek)
            },
        )
        Spacer(modifier = Modifier.height(20.dp))
        DayDetail(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            poisonState = PoisonState.entries.random()
        )
    }
}

@Preview
@Composable
private fun Example1Preview() {
    CalendarScreen()
}


