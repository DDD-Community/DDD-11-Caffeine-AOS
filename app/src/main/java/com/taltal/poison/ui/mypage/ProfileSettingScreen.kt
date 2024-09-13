package com.taltal.poison.ui.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.taltal.poison.ui.designsystem.BirthdayVisualTransformation
import com.taltal.poison.ui.designsystem.ConfirmButton
import com.taltal.poison.ui.designsystem.OptionSelection
import com.taltal.poison.ui.designsystem.RoundedTextField
import com.taltal.poison.ui.theme.body_14md
import com.taltal.poison.ui.theme.body_16rg

@Composable
fun ProfileSection(viewModel: MyPageViewModel) {
    val gender = viewModel.gender.collectAsState()
    val birthday = viewModel.birth.collectAsState()
    val height = viewModel.height.collectAsState()
    val weight = viewModel.weight.collectAsState()

    val isProfileFullFilled by viewModel.isProfileFullFilled.collectAsState()
    Column {
        Text(
            text = "성별",
            style = body_14md,
            modifier = Modifier.padding(start = 28.dp, bottom = 8.dp),
        )
        OptionSelection(
            currentOption = gender.value,
            options = listOf("여성", "남성"),
            updateSelectedOption = { viewModel.updateGenderOption(it) },
            modifier = Modifier.padding(16.dp),
        )

        Text(
            text = "생년월일",
            style = body_14md,
            modifier = Modifier.padding(start = 28.dp, top = 24.dp, bottom = 8.dp),
        )
        RoundedTextField(
            text = birthday.value,
            textHint = "YYYY.MM.DD",
            modifier = Modifier.padding(horizontal = 16.dp),
            keyboardType = KeyboardType.Number,
            onValueChanged = { viewModel.updateBirth(it) },
            visualTransformation = BirthdayVisualTransformation(),
        )
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "키",
                    style = body_14md,
                    modifier = Modifier.padding(start = 28.dp, top = 24.dp, bottom = 8.dp),
                )
                Row(modifier = Modifier.padding(end = 8.dp)) {
                    RoundedTextField(
                        text = height.value,
                        textHint = "",
                        modifier = Modifier.fillMaxWidth(0.8f),
                        keyboardType = KeyboardType.Number,
                        onValueChanged = { viewModel.updateHeight(it) },
                    )
                    Text(
                        text = "cm",
                        style = body_16rg,
                        modifier =
                            Modifier
                                .padding(start = 4.dp)
                                .weight(1f)
                                .align(Alignment.CenterVertically),
                    )
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "몸무게",
                    style = body_14md,
                    modifier = Modifier.padding(start = 12.dp, top = 24.dp, bottom = 8.dp),
                )
                Row(modifier = Modifier.padding(end = 8.dp)) {
                    RoundedTextField(
                        text = weight.value,
                        textHint = "",
                        modifier = Modifier.fillMaxWidth(0.8f),
                        keyboardType = KeyboardType.Number,
                        onValueChanged = { viewModel.updateWeight(it) },
                    )
                    Text(
                        text = "kg",
                        style = body_16rg,
                        modifier =
                            Modifier
                                .padding(start = 4.dp)
                                .weight(1f)
                                .align(Alignment.CenterVertically),
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        ConfirmButton(
            text = "다음",
            isEnabled = isProfileFullFilled,
            onClick = {
            },
        )
    }
}
