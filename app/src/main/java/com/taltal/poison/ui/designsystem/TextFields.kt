package com.taltal.poison.ui.designsystem

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taltal.poison.ui.theme.dialogue_14rg
import kotlinx.coroutines.delay

@Composable
fun CharacterMessage(text: String) {
    var displayedText by remember { mutableStateOf("") }

    LaunchedEffect(text) {
        text.forEachIndexed { _, char ->
            delay(100) // 각 문자 사이의 지연 시간 (밀리초)
            displayedText += char
        }
    }

    Box(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(0.7f)
            .wrapContentHeight()
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val cornerRadius = 16.dp.toPx()
            val tailWidth = 10.dp.toPx()
            val tailHeight = 10.dp.toPx()

            // 말풍선 본체
            val roundRect = RoundRect(
                left = 0f,
                top = 0f,
                right = size.width - tailWidth,
                bottom = size.height,
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )
            val rectPath = Path().apply {
                addRoundRect(roundRect)
            }

            // 말풍선 꼬리
            val tailPath = Path().apply {
                moveTo(size.width - tailWidth, size.height / 2 - tailHeight / 2)
                lineTo(size.width, size.height / 2)
                lineTo(size.width - tailWidth, size.height / 2 + tailHeight / 2)
                close()
            }

            drawPath(rectPath, color = Color(0xFF3A3F47), style = Fill)
            drawPath(tailPath, color = Color(0xFF3A3F47), style = Fill)
        }

        Text(
            text = displayedText,
            style = dialogue_14rg,
            color = Color.White,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterStart)
                .background(Color.Transparent)
        )
    }
}

@Preview
@Composable
private fun CharacterMessageView() {
    CharacterMessage("닉네임을\n입력해주세요.")
}