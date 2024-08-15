package com.taltal.poison.ui.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.taltal.poison.R
import com.taltal.poison.ui.designsystem.CharacterMessage
import com.taltal.poison.ui.designsystem.ConfirmButton
import com.taltal.poison.ui.designsystem.RoundedTextField

@Composable
fun DescriptionCharacter(
    message: String,
    poeType: Int = NORMAL_POE,
    modifier: Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        CharacterMessage(message)
        Image(
            painter = painterResource(
                id = when (poeType) {
                    HAPPY_POE -> R.drawable.happy_poe
                    STAR_POE -> R.drawable.star_poe
                    else -> R.drawable.normal_poe
                }
            ),
            contentDescription = null,
            modifier = Modifier.size(width = 100.dp, height = 138.dp)
        )
    }
}

@Composable
fun NicknameSection(
    viewModel: OnBoardingViewModel = hiltViewModel()
) {
    val nickname by viewModel.nickname.collectAsState(initial = "")
    val isNicknameValid by viewModel.isNicknameValid.collectAsState(initial = false)

    Column {
        DescriptionCharacter(
            message = messageList[viewModel.pagerState.currentPage],
            modifier = Modifier
                .padding(horizontal = 28.dp)
                .padding(top = 16.dp, bottom = 12.dp)
        )
        RoundedTextField(
            textHint = "닉네임을 입력해 주세요",
            onValueChanged = { viewModel.updateNickname(it) },
            checkError = { !viewModel.checkNickNameValidation(it) },
            needClearButton = true,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(horizontal = 16.dp)
        )
        NicknameHints(
            isError = !isNicknameValid,
            isInputEmpty = nickname.isEmpty(),
            errorText = viewModel.errorText.value,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        ConfirmButton(
            text = "다음",
            isEnabled = isNicknameValid,
            onClick = {
                viewModel.checkUserNicknameDuplicate()
            }
        )
    }
}

@Composable
fun NicknameHints(
    isError: Boolean,
    errorText: String,
    isInputEmpty: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "한글, 알파벳, 숫자 8글자 이하로 설정할 수 있어요",
            fontSize = 12.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(start = 16.dp)
        )
        if (isError && !isInputEmpty) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Error",
                    tint = Color.Red,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = errorText,
                    fontSize = 12.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DescriptionPreview() {
    DescriptionCharacter("닉네임을\n입력해주세요", modifier = Modifier)
}

@Preview(showBackground = true)
@Composable
private fun NicknameSectionPreview() {
    NicknameSection()
}

const val NORMAL_POE = 0
const val HAPPY_POE = 1
const val STAR_POE = 2