package com.taltal.poison.ui.onboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.taltal.poison.ui.designsystem.ConfirmButton
import com.taltal.poison.ui.designsystem.OptionSelection
import com.taltal.poison.ui.theme.body_14md
import com.taltal.poison.ui.theme.body_16md
import com.taltal.poison.ui.theme.taltal_neutral_10
import com.taltal.poison.ui.theme.taltal_neutral_5
import com.taltal.poison.ui.theme.taltal_neutral_60
import com.taltal.poison.ui.theme.taltal_yellow_10
import com.taltal.poison.ui.theme.taltal_yellow_60
import com.taltal.poison.util.SharedPrefManager

@Composable
fun PurposeSection(viewModel: OnBoardingViewModel = hiltViewModel()) {
    val purpose = viewModel.chosenPurpose.collectAsState()
    Column {
        DescriptionCharacter(
            message = messageList[viewModel.pagerState.currentPage],
            poeType = HAPPY_POE,
            modifier =
                Modifier
                    .padding(horizontal = 28.dp)
                    .padding(top = 16.dp, bottom = 12.dp),
        )
        Text(
            text = "목표 설정",
            style = body_14md,
            modifier = Modifier.padding(start = 28.dp, bottom = 8.dp),
        )
        OptionSelection(
            currentOption = purpose.value,
            options = listOf("카페인 줄이기", "카페인 기록"),
            updateSelectedOption = { viewModel.updateTargetPurpose(it) },
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Spacer(modifier = Modifier.weight(1f))
        ConfirmButton(
            text = "목표 추천받기",
            isEnabled = purpose.value.isNotEmpty(),
            onClick = {
                viewModel.moveToNextPage()
            },
        )
    }
}

@Composable
fun GoalSection(viewModel: OnBoardingViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val sharedPrefManager: SharedPrefManager by lazy {
        SharedPrefManager(context)
    }
    val recommendedGoal = viewModel.dailyGoalNumber.collectAsState()
    var selectedGoal by remember { mutableStateOf("") }

    Column {
        DescriptionCharacter(
            message = messageList[viewModel.pagerState.currentPage],
            poeType = STAR_POE,
            modifier =
                Modifier
                    .padding(horizontal = 28.dp)
                    .padding(top = 16.dp, bottom = 12.dp),
        )
        NumberPickerComponent(
            title = "매일 단위",
            currentGoal = recommendedGoal.value,
            description = "하루 최대 샷 수",
            isSelected = selectedGoal == "매일 단위",
            updateGoal = { selectedGoal = it },
            updateGoalNumber = { viewModel.updateDailyGoalNumber(it) },
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Spacer(modifier = Modifier.weight(1f))
        ConfirmButton(
            text = "완료",
            isEnabled = selectedGoal.isNotEmpty(),
            onClick = {
                viewModel.uploadUserData(sharedPrefManager)
            },
        )
    }
}

@Composable
fun NumberPickerComponent(
    modifier: Modifier = Modifier,
    title: String,
    currentGoal: Int,
    description: String,
    isSelected: Boolean,
    updateGoal: (String) -> Unit = {},
    updateGoalNumber: (Int) -> Unit = {},
) {
    var number by remember { mutableIntStateOf(currentGoal) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            modifier
                .border(
                    1.dp,
                    if (isSelected) taltal_yellow_60 else taltal_neutral_10,
                    shape = RoundedCornerShape(12.dp),
                ).clickable { updateGoal(title) },
    ) {
        Text(
            text = title,
            style = body_16md,
            color = taltal_neutral_60,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(
                        color = if (isSelected) taltal_yellow_10 else taltal_neutral_5,
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                    ).border(
                        1.dp,
                        color = if (isSelected) taltal_yellow_60 else taltal_neutral_10,
                        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                    ).wrapContentHeight(align = Alignment.CenterVertically),
            textAlign = TextAlign.Center,
        )
        Text(
            text = description,
            style = body_14md,
            color = taltal_neutral_60,
            modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 12.dp)
                    .border(
                        1.dp,
                        taltal_neutral_10,
                        shape = RoundedCornerShape(12.dp),
                    ),
        ) {
            IconButton(
                onClick = {
                    if (number > 0) number--
                    updateGoalNumber(number)
                },
                modifier =
                    Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp)),
            ) {
                Text("-", fontSize = 24.sp, color = Color(0xFF888888))
            }

            Text(
                text = "$number",
                fontSize = 24.sp,
                color = Color.Black,
                modifier =
                    Modifier
                        .padding(horizontal = 16.dp)
                        .padding(vertical = 8.dp, horizontal = 32.dp),
                textAlign = TextAlign.Center,
            )

            IconButton(
                onClick = {
                    number++
                    updateGoalNumber(number)
                },
                modifier =
                    Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp)),
            ) {
                Text("+", fontSize = 24.sp, color = Color(0xFF888888))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PurposeSectionPreview() {
    PurposeSection()
}

@Preview(showBackground = true)
@Composable
private fun NumberPickerComponentPreview() {
    NumberPickerComponent(
        title = "매일 단위",
        description = "하루 최대 샷 수",
        isSelected = false,
        modifier = Modifier.padding(8.dp),
        currentGoal = 2
    )
}

@Preview(showBackground = true)
@Composable
private fun NumberPickerComponentPreview2() {
    NumberPickerComponent(
        title = "매일 단위",
        description = "하루 최대 샷 수",
        isSelected = true,
        modifier = Modifier.padding(8.dp),
        currentGoal = 2
    )
}

@Preview(showBackground = true)
@Composable
private fun GoalSectionPreview() {
    GoalSection()
}
