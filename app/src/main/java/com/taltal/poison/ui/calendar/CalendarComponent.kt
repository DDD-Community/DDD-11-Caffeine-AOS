package com.taltal.poison.ui.calendar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.CalendarLayoutInfo
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.taltal.poison.ui.calendar.model.PoisonState
import com.taltal.poison.ui.designsystem.CharacterMessage
import com.taltal.poison.ui.designsystem.MESSAGE_TAIL_START
import com.taltal.poison.ui.theme.body_14md
import com.taltal.poison.ui.theme.taltal_neutral_10
import com.taltal.poison.ui.theme.taltal_neutral_30
import com.taltal.poison.ui.theme.taltal_neutral_5
import com.taltal.poison.ui.theme.taltal_neutral_60
import com.taltal.poison.ui.theme.taltal_neutral_80
import com.taltal.poison.ui.theme.taltal_neutral_90
import com.taltal.poison.ui.theme.taltal_yellow_70
import com.taltal.poison.ui.theme.title_14bd
import com.taltal.poison.ui.theme.title_16sb
import com.taltal.poison.ui.theme.title_18sb
import com.taltal.poison.ui.theme.title_20sb
import kotlinx.coroutines.flow.filterNotNull
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun CalendarHeader() {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(56.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "캘린더", style = title_18sb.copy(color = taltal_neutral_90))
    }
}

@Composable
fun MonthHeader(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN),
                textAlign = TextAlign.Center,
                style = body_14md.copy(color = taltal_neutral_60),
            )
        }
    }
}

@Composable
fun Day(
    day: CalendarDay,
    poisonState: PoisonState,
    isSelected: Boolean,
    onClick: (CalendarDay) -> Unit
) {
    if (day.position == DayPosition.MonthDate) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(6.dp)
                .clickable { onClick(day) },
            contentAlignment = Alignment.Center,
        ) {
            if (poisonState != PoisonState.None) {
                Image(
                    imageVector = ImageVector.vectorResource(id = poisonState.getCalendarDayBackgrountResId()),
                    contentDescription = ""
                )
            }
            if (poisonState in listOf(PoisonState.Empty, PoisonState.None)) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = day.date.dayOfMonth.toString(),
                    style = title_14bd.copy(color = if (poisonState == PoisonState.Empty) taltal_neutral_60 else taltal_neutral_30),
                )
            }
            if (isSelected) {
                Canvas(
                    modifier = Modifier
                        .padding(top = 2.dp, end = 2.dp)
                        .align(Alignment.TopEnd)
                        .size(6.dp)
                ) {
                    drawCircle(color = taltal_yellow_70)
                }
            }
        }
    }
}

@Composable
fun CalendarTitle(
    modifier: Modifier,
    currentMonth: YearMonth,
    arrowLeftImageResId: Int,
    arrowRightImageResId: Int,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.clickable { goToPrevious.invoke() },
            painter = painterResource(id = arrowLeftImageResId),
            contentDescription = "이전 달로 이동",
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "${currentMonth.year}년 ${currentMonth.monthValue}월",
            style = title_16sb.copy(color = taltal_neutral_90),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Image(
            modifier = Modifier.clickable { goToNext.invoke() },
            painter = painterResource(id = arrowRightImageResId),
            contentDescription = "다음 달로 이동"
        )
    }
}

@Composable
fun DayDetail(
    dailyPoisonDetail: DailyPoisonDetail.Success,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = taltal_neutral_10,
                shape = RoundedCornerShape(12.dp, 16.dp, 16.dp, 16.dp)
            )
            .padding(top = 12.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(4.dp, 16.dp, 4.dp, 16.dp),
                    color = taltal_neutral_5
                )
                .padding(horizontal = 16.dp, vertical = 4.dp),
            text = dailyPoisonDetail.title,
            style = title_14bd.copy(color = taltal_neutral_80)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = dailyPoisonDetail.subTitle, style = title_20sb.copy(color = taltal_neutral_90))
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier.size(60.dp),
                painter = painterResource(id = dailyPoisonDetail.imageResId),
                contentDescription = ""
            )
            CharacterMessage(
                text = dailyPoisonDetail.description,
                tailPosition = MESSAGE_TAIL_START
            )
        }
    }
}

@Composable
fun rememberFirstMostVisibleMonth(
    state: CalendarState,
    viewportPercent: Float = 50f,
): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.firstMostVisibleMonth(viewportPercent) }
            .filterNotNull()
            .collect { month -> visibleMonth.value = month }
    }
    return visibleMonth.value
}

private fun CalendarLayoutInfo.firstMostVisibleMonth(viewportPercent: Float = 50f): CalendarMonth? {
    return if (visibleMonthsInfo.isEmpty()) {
        null
    } else {
        val viewportSize = (viewportEndOffset + viewportStartOffset) * viewportPercent / 100f
        visibleMonthsInfo.firstOrNull { itemInfo ->
            if (itemInfo.offset < 0) {
                itemInfo.offset + itemInfo.size >= viewportSize
            } else {
                itemInfo.size - itemInfo.offset >= viewportSize
            }
        }?.month
    }
}