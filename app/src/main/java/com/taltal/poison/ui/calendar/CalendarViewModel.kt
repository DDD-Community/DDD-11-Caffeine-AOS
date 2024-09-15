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
import com.taltal.poison.data.model.DailyShotInformation
import com.taltal.poison.data.repository.PoisonCoffeeRepository
import com.taltal.poison.ui.calendar.model.PoisonState
import com.taltal.poison.util.SharedPrefManager
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
class CalendarViewModel
    @Inject
    constructor(
        private val repository: PoisonCoffeeRepository,
        private val prefManager: SharedPrefManager,
    ) : ViewModel() {
        private val dailyGoal = prefManager.getGoalNumber()

        val currentMonth = MutableStateFlow(YearMonth.now())
        val selectedDay = MutableStateFlow(CalendarDay(LocalDate.now(), DayPosition.MonthDate))
        val dailyDetail = MutableStateFlow<DailyPoisonDetail>(DailyPoisonDetail.Loading)
        val calendar = MutableStateFlow<Calendar>(Calendar.Loading)

        init {
            getCalendar()
            getDailyPoison(selectedDay.value)
        }

        fun getCalendar() {
            println("getCalendar")
            viewModelScope.launch {
                calendar.value = Calendar.Loading
                calendar.value = fetchCalendar()
            }
        }

        fun getDailyPoison(day: CalendarDay) {
            viewModelScope.launch {
                selectedDay.value = day
                dailyDetail.value = DailyPoisonDetail.Loading
                dailyDetail.value = fetchDailyPoison(day)
            }
        }

        private suspend fun fetchCalendar(): Calendar.Success {
            val data = repository.getCalendarState()
            return getCalendarStateDummy(data)
        }

        private suspend fun fetchDailyPoison(day: CalendarDay): DailyPoisonDetail.Success {
            val data =
                repository.getDailyShotInformation(
                    year = day.date.year,
                    month = day.date.monthValue,
                    day = day.date.dayOfMonth,
                )
            val (shot, poisonState, records) = getDailyDetailDummy(data)
            return mapToDailyPoison(day, poisonState, shot, records)
        }

        private fun mapToDailyPoison(
            day: CalendarDay,
            poisonState: PoisonState,
            shot: Int,
            records: List<PoisonRecord>,
        ) = DailyPoisonDetail.Success(
            title = getDailyDetailTitle(day),
            subTitle = getDailyDetailSubTitle(poisonState, shot),
            imageResId = poisonState.getDailyCoffeeImageResId(),
            description = getDailyDetailRecord(records),
            poisonState = poisonState,
        )

        private fun getDailyDetailSubTitle(
            poisonState: PoisonState,
            shot: Int,
        ) = buildAnnotatedString {
            if (shot == 0) {
                withStyle(SpanStyle(color = poisonState.getColor())) { append("한 잔도") }
                append("안 마셨어요!")
            } else {
                append("총 ")
                withStyle(SpanStyle(color = poisonState.getColor())) { append("${shot}잔 ") }
                append("마셨어요")
            }
        }

        private fun getDailyDetailTitle(day: CalendarDay) =
            "${day.date.year}년 ${day.date.monthValue}월 ${day.date.dayOfMonth}일(${
                day.date.dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    Locale.KOREAN,
                )
            })"

        private fun getFormattedRecord(record: PoisonRecord): String =
            "${
                record.dateTime.format(
                    DateTimeFormatter.ofPattern(
                        "a h시 mm분",
                        Locale.KOREAN,
                    ),
                )
            } +${record.shot}잔"

        private fun getDailyDetailRecord(records: List<PoisonRecord>): String {
            val formattedRecords = records.take(3).joinToString("\n") { getFormattedRecord(it) }
            return if (records.size > 3) "$formattedRecords\n···" else formattedRecords
        }

        private fun getPoisonState(value: String): PoisonState =
            when (value) {
                "NONE" -> PoisonState.Empty
                "EMPTY" -> PoisonState.Empty
                "SUCCESS" -> PoisonState.Success
                "FAIL" -> PoisonState.Fail
                else -> throw IllegalArgumentException("Unknown poison state: $value")
            }

        private fun getCalendarStateDummy(data: Map<String, String>): Calendar.Success {
            val calendarStateMap =
                data
                    .mapKeys { LocalDate.parse(it.key) }
                    .mapValues { getPoisonState(it.value) }
            return Calendar.Success(calendarStateMap)
        }

        private fun getDailyDetailDummy(dailyShotInformation: DailyShotInformation): Triple<Int, PoisonState, List<PoisonRecord>> {
            // 로직체크 API 연동전에 안함 --> 로컬에 저장할 필요 없을듯
            // 총 4잔
            val shot = dailyShotInformation.shot
            // PoisonState
            val poisonState =
                when {
                    shot == 0 -> {
                        PoisonState.Empty
                    }

                    shot < dailyGoal -> {
                        PoisonState.Success
                    }

                    else -> {
                        PoisonState.Fail
                    }
                }
            // 기록 LocalDateTime, 2잔
            val records =
                dailyShotInformation.poisonRecord.map {
                    val dateTimeString = it.dateTime.replace("Z", "")

                    // LocalDateTime 포맷 정의
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                    val localDateTime = LocalDateTime.parse(dateTimeString, formatter)

                    PoisonRecord(
                        dateTime = localDateTime,
                        shot = it.shot,
                    )
                }
            println("records: $records shot: $shot poisonState: $poisonState")
            return Triple(shot, poisonState, records)
        }
    }

@Immutable
sealed interface Calendar {
    data class Success(
        val calendarStateMap: Map<LocalDate, PoisonState>,
    ) : Calendar

    data object Loading : Calendar
}

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
