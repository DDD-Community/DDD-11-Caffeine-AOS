package com.taltal.poison.ui.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.taltal.poison.R
import com.taltal.poison.ui.theme.body_16md
import com.taltal.poison.ui.theme.caption_12rg
import com.taltal.poison.ui.theme.taltal_neutral_10
import com.taltal.poison.ui.theme.taltal_neutral_5
import com.taltal.poison.ui.theme.taltal_neutral_50
import com.taltal.poison.ui.theme.taltal_yellow_20
import com.taltal.poison.ui.theme.taltal_yellow_50
import com.taltal.poison.ui.theme.taltal_yellow_70
import com.taltal.poison.ui.theme.title_12bd
import com.taltal.poison.ui.theme.title_18sb
import com.taltal.poison.util.SharedPrefManager

@Composable
fun MyPageScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val viewModel = hiltViewModel<MyPageViewModel>()

//    NavHost(navController = navController, startDestination = MYPAGE.HOME.name) {
//        MYPAGE.entries.forEach { screen ->
//            composable(
//                route = screen.name,
//                enterTransition = { PoisonTransition.slideEnterHorizontally() },
//                exitTransition = { PoisonTransition.slideExitHorizontally() },
//                popEnterTransition = { PoisonTransition.slidePopEnterHorizontally() },
//                popExitTransition = { PoisonTransition.slidePopExitHorizontally() },
//            ) {
//                createScreen(screen, viewModel)
//            }
//        }
//    }
    MyPageHome(viewModel = viewModel, navController = navController)
}

// @Composable
// fun createScreen(
//    screen: MYPAGE,
//    viewModel: MyPageViewModel,
// ) {
//    when (screen) {
//        MYPAGE.HOME -> MyPageHome(viewModel = viewModel)
//        MYPAGE.PROFILE -> MyProfileSection(viewModel = viewModel)
//    }
// }

@Composable
fun MyPageHome(
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel,
    navController: NavController,
) {
    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        Box(
            modifier = Modifier.height(52.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "마이페이지",
                style = title_18sb,
                modifier = Modifier.padding(start = 16.dp),
                textAlign = TextAlign.Center,
            )
        }
        MypageGoalSection()
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        MypageSettingSection(viewModel = viewModel, navController = navController)
    }
}

@Composable
fun MypageGoalSection() {
    val context = LocalContext.current
    val sharedPrefManager = SharedPrefManager(context)

    val nickName = sharedPrefManager.getNickName()
    val nickNameText =
        buildAnnotatedString {
            withStyle(style = title_18sb.toSpanStyle().copy(color = taltal_yellow_70)) {
                append(nickName)
            }

            append("님의 목표")
        }
    val isForLogging = sharedPrefManager.getForLogging()
    val isForLoggingText =
        buildAnnotatedString {
            append("카페인 ")

            withStyle(style = body_16md.toSpanStyle().copy(background = taltal_yellow_20)) {
                append(if (isForLogging) "기록" else "줄이기")
            }
        }

    val goalNumber = sharedPrefManager.getGoalNumber()
    val goalText =
        buildAnnotatedString {
            withStyle(style = body_16md.toSpanStyle().copy(background = taltal_yellow_20)) {
                append("매일")
            }

            append(" 최대 ")

            withStyle(style = body_16md.toSpanStyle().copy(background = taltal_yellow_20)) {
                append("$goalNumber 샷")
            }
        }

    Row(
        modifier =
            Modifier
                .height(166.dp)
                .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.my_page_poe),
            contentDescription = null,
            modifier = Modifier.size(width = 88.dp, height = 134.dp),
        )
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 18.dp, end = 16.dp)
                    .border(width = 1.dp, color = taltal_neutral_10, shape = RoundedCornerShape(12.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = nickNameText,
                style = title_18sb,
                modifier = Modifier.padding(start = 32.dp, top = 16.dp),
            )
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_checkbox_24),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp),
                )
                Text(
                    text = isForLoggingText,
                    style = body_16md,
                )
            }
            Row(
                modifier = Modifier.padding(bottom = 16.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_checkbox_24),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp),
                )
                Text(
                    text = goalText,
                    style = body_16md,
                )
            }
        }
    }
}

@Composable
fun MypageSettingSection(
    viewModel: MyPageViewModel,
    navController: NavController,
) {
    var checked by remember { mutableStateOf(false) }
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(260.dp)
                .padding(horizontal = 16.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .height(52.dp)
                    .fillMaxWidth()
                    .clickable { viewModel.navigateToProfileSetting(navController) }
                        ,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile_16),
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp),
            )
            Text(text = "내 정보 수정", style = body_16md)
        }
        HorizontalDivider(color = taltal_neutral_5)
        Row(
            modifier = Modifier.height(72.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_bell_16),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp),
                    )
                    Text(text = "알림 설정", style = body_16md)
                }
                Text(
                    text = "잊지 않고 기록하실 수 있도록 알림을 보내드려요",
                    style = caption_12rg,
                    color = taltal_neutral_50,
                )
            }

            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                },
                colors =
                    SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = taltal_yellow_50,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = taltal_neutral_10,
                        uncheckedBorderColor = Color.Transparent,
                        checkedBorderColor = Color.Transparent,
                    ),
            )
        }
        HorizontalDivider(color = taltal_neutral_5)
        Row(
            modifier = Modifier.height(52.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_question_16),
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp),
            )
            Text(text = "문의하기", style = body_16md)
        }
        HorizontalDivider(color = taltal_neutral_5)
        Row(
            modifier = Modifier.height(48.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Ver 1.0.0",
                style = caption_12rg,
                color = taltal_neutral_50,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = "이용약관",
                modifier = Modifier.padding(end = 4.dp),
                style = caption_12rg,
                color = taltal_neutral_50,
            )
            Text(text = "·")
            Text(
                text = "개인정보 처리방침",
                modifier = Modifier.padding(start = 4.dp),
                style = title_12bd,
                color = taltal_neutral_50,
            )
        }
    }
}

enum class MYPAGE {
    HOME,
//    PROFILE,
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenView() {
    val navController = rememberNavController()
    MyPageHome(viewModel = hiltViewModel(), navController = navController)
}

@Preview(showBackground = true)
@Composable
private fun SettingPreview() {
    val navController = rememberNavController()
    MypageSettingSection(
        viewModel = hiltViewModel(),
        navController = navController,
    )
}
