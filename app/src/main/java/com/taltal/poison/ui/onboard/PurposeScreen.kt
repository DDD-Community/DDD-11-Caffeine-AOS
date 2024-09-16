package com.taltal.poison.ui.onboard

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.taltal.poison.R
import com.taltal.poison.ui.designsystem.ConfirmButton
import com.taltal.poison.ui.designsystem.OptionColumnSelection
import com.taltal.poison.ui.theme.body_14md
import com.taltal.poison.ui.theme.caption_12rg
import com.taltal.poison.ui.theme.taltal_neutral_10
import com.taltal.poison.ui.theme.taltal_neutral_50
import com.taltal.poison.ui.theme.taltal_neutral_60
import com.taltal.poison.ui.theme.taltal_neutral_70
import com.taltal.poison.ui.theme.taltal_yellow_5
import com.taltal.poison.ui.theme.taltal_yellow_60
import com.taltal.poison.ui.theme.title_14bd
import com.taltal.poison.ui.theme.title_18sb
import com.taltal.poison.util.SharedPrefManager
import kotlinx.coroutines.launch

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
        OptionColumnSelection(
            currentOption = purpose.value,
            options = listOf("카페인 줄이기", "카페인 관리하기"),
            description = listOf("카페인 섭취량을 줄이고 싶어요", "카페인 섭취량이 더 늘어나지 않게 관리하고 싶어요"),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalSection(viewModel: OnBoardingViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val sharedPrefManager: SharedPrefManager by lazy {
        SharedPrefManager(context)
    }
    val recommendedGoal = viewModel.dailyGoalNumber.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "카페인 섭취 목표는 어떻게 추천되나요?",
                    style = title_18sb,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "작성해주신 정보들을 바탕으로 *카페인 권장량을 계산해요.\n그 후, 앱 사용 목적에 따라 목표 잔 수를 추천해드려요.",
                    style = body_14md.copy(color = taltal_neutral_70),
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                )
                Text(
                    text = "(카페인 줄이기: 권장량보다 적게, 카페인 관리하기: 권장량 만큼)",
                    style = body_14md.copy(color = taltal_neutral_50)
                )
                Text(
                    text = "*일일 카페인 섭취 권장량",
                    style = title_14bd,
                    modifier = Modifier.padding(bottom = 4.dp, top = 20.dp)
                )
                Text(
                    text = "성인의 경우 400mg 이하, 여성은 권장량의 90%,\n" +
                            "어린이‧청소년은 체중 1kg당 2.5~3.0mg 이하로,\n" +
                            "65세 이상은 300mg 이하로 권장돼요.",
                    style = body_14md.copy(color = taltal_neutral_70),
                    modifier = Modifier.padding(bottom = 40.dp)
                )
            }
        }
    }
    Column {
        DescriptionCharacter(
            message = messageList[viewModel.pagerState.currentPage],
            poeType = STAR_POE,
            modifier =
                Modifier
                    .padding(horizontal = 28.dp)
                    .padding(top = 16.dp, bottom = 12.dp),
        )
        Text(
            text = "하루 목표 설정",
            style = body_14md,
            modifier = Modifier.padding(start = 28.dp, top = 8.dp, bottom = 8.dp),
        )
        NumberPickerComponent(
            currentGoal = recommendedGoal.value,
            updateGoalNumber = { viewModel.updateDailyGoalNumber(it) },
        )
        Text(
            text = "커피 1잔은 2샷을 기준으로 해요",
            style = caption_12rg.copy(taltal_neutral_60),
            modifier = Modifier.padding(top = 4.dp, start = 32.dp),
        )
        Row(
            modifier =
                Modifier
                    .padding(top = 32.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        taltal_yellow_5,
                        shape =
                            RoundedCornerShape(
                                16.dp,
                            ),
                    ).border(
                        1.dp,
                        taltal_yellow_60,
                        RoundedCornerShape(
                            16.dp,
                        ),
                    ).clickable {
                        showBottomSheet = true
                    },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_help_20),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )
            Text(
                text = "목표는 어떻게 추천되나요?",
                style = title_14bd,
                modifier = Modifier.padding(start = 8.dp),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        ConfirmButton(
            text = "완료",
            isEnabled = true,
            onClick = {
                viewModel.uploadUserData(sharedPrefManager)
            },
        )
    }
}

@Composable
fun NumberPickerComponent(
    currentGoal: Int,
    updateGoalNumber: (Int) -> Unit = {},
) {
    var number by remember { mutableIntStateOf(currentGoal) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier =
            Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp)
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

@Preview(showBackground = true)
@Composable
private fun PurposeSectionPreview() {
    PurposeSection()
}

@Preview(showBackground = true)
@Composable
private fun NumberPickerComponentPreview() {
    NumberPickerComponent(
        currentGoal = 2,
    )
}

@Preview(showBackground = true)
@Composable
private fun NumberPickerComponentPreview2() {
    NumberPickerComponent(
        currentGoal = 2,
    )
}

@Preview(showBackground = true)
@Composable
private fun GoalSectionPreview() {
    GoalSection()
}
