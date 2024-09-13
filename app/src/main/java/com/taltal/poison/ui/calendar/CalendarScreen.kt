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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.taltal.poison.R
import com.taltal.poison.ui.calendar.model.PoisonState
import kotlinx.coroutines.launch
import java.time.DayOfWeek

@Composable
fun CalendarScreen(viewModel: CalendarViewModel = hiltViewModel()) {
    val currentMonth by viewModel.currentMonth.collectAsStateWithLifecycle()
    val selectedDay by viewModel.selectedDay.collectAsStateWithLifecycle()
    val calendar by viewModel.calendar.collectAsStateWithLifecycle()
    val dailyDetail by viewModel.dailyDetail.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        val state = rememberCalendarState(
            startMonth = currentMonth.minusMonths(12),
            endMonth = currentMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = DayOfWeek.entries.first(),
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
                Day(
                    day = day,
                    poisonState = (calendar as? Calendar.Success)?.calendarStateMap?.getOrDefault(
                        day.date,
                        PoisonState.None
                    ) ?: PoisonState.None,
                    isSelected = selectedDay == day,
                    onClick = { clicked -> viewModel.getDailyPoison(clicked) }
                )
            },
            monthHeader = {
                MonthHeader(daysOfWeek = DayOfWeek.entries)
            },
        )
        Spacer(modifier = Modifier.height(20.dp))
        if (dailyDetail is DailyPoisonDetail.Success
            && (dailyDetail as? DailyPoisonDetail.Success)?.poisonState != PoisonState.None
        ) {
            DayDetail(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                dailyPoisonDetail = dailyDetail as DailyPoisonDetail.Success,
            )
        }
    }
}

@Preview
@Composable
private fun Example1Preview() {
    CalendarScreen()
}


