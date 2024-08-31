package com.taltal.poison.ui.calendar

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.taltal.poison.ui.calendar.model.PoisonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor() : ViewModel() {
    val currentMonth = MutableStateFlow(YearMonth.now())
    val selectedDay = MutableStateFlow(CalendarDay(LocalDate.now(), DayPosition.MonthDate))
    val dailyDetail = MutableStateFlow<DailyPoisonDetail>(DailyPoisonDetail.Loading)
    val calendar = MutableStateFlow(getCalendarStateDummy())

    init {
        fetchCalendar()
        getDailyPoison(selectedDay.value)
    }

    fun fetchCalendar() {
        viewModelScope.launch {
            calendar.value = getCalendarStateDummy()
        }
    }

    fun getDailyPoison(day: CalendarDay) {
        viewModelScope.launch {
            selectedDay.value = day
            dailyDetail.value = DailyPoisonDetail.Loading
            dailyDetail.value = fetchDailyPoison(day)
        }
    }

    private suspend fun fetchDailyPoison(day: CalendarDay): DailyPoisonDetail.Success {
        // TODO 주석 --> 서버에서 가져올 데이터
        // 로직체크 API 연동전에 안함 --> 로컬에 저장할 필요 없을듯
        // 총 4잔
        val shot = 4
        // PoisonState
        val poisonState = PoisonState.Success
        // 기록 LocalDateTime, 2잔
        val records =
            listOf(PoisonRecord(LocalDateTime.now(), 2), PoisonRecord(LocalDateTime.now(), 2))
        return DailyPoisonDetail.Success(
            title = getDailyDetailTitle(day),
            subTitle = getDailyDetailSubTitle(poisonState, shot),
            imageResId = poisonState.getDailyCoffeeImageResId(),
            description = getDailyDetailRecord(records),
            poisonState = poisonState
        )
    }

    private fun getDailyDetailSubTitle(poisonState: PoisonState, shot: Int) =
        buildAnnotatedString {
            if (shot == 0) {
                withStyle(SpanStyle(color = poisonState.getColor())) { append("한 잔도") }
                append("안 마셨어요!")
            } else {
                append("총 ")
                withStyle(SpanStyle(color = poisonState.getColor())) { append("${shot}샷 ") }
                append("마셨어요")
            }
        }

    private fun getDailyDetailTitle(day: CalendarDay) =
        "${day.date.year}년 ${day.date.monthValue}월 ${day.date.dayOfMonth}일(${
            day.date.dayOfWeek.getDisplayName(
                TextStyle.SHORT,
                Locale.KOREAN
            )
        })"

    private fun getDailyDetailRecord(records: List<PoisonRecord>) =
        records.map {
            "${
                it.dateTime.format(
                    DateTimeFormatter.ofPattern(
                        "a h시 mm분",
                        Locale.KOREAN
                    )
                )
            } +${it.shot}샷"
        }.joinToString("\n")

    private fun getCalendarStateDummy(): CalendarState {
        val current = LocalDate.now()
        return CalendarState(
            buildMap {
                putAll(
                    arrayOf(
                        current.minusDays(2) to PoisonState.Empty,
                        current.minusDays(1) to PoisonState.Fail,
                        current to PoisonState.Success,
                        current.plusDays(1) to PoisonState.None,
                        current.plusDays(2) to PoisonState.None,
                        current.plusDays(3) to PoisonState.None
                    )
                )
            }
        )
    }
}

@Immutable
data class CalendarState(
    val calendarStateMap: Map<LocalDate, PoisonState>
)

data class PoisonRecord(
    val dateTime: LocalDateTime,
    val shot: Int,
)

sealed interface DailyPoisonDetail {
    data class Success(
        val title: String,
        val subTitle: AnnotatedString,
        val imageResId: Int,
        val description: String,
        val poisonState: PoisonState,
    ) : DailyPoisonDetail

    data object Loading : DailyPoisonDetail
}