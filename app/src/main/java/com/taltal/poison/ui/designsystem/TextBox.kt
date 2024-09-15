package com.taltal.poison.ui.designsystem

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taltal.poison.ui.theme.dialogue_14rg
import com.taltal.poison.ui.theme.taltal_neutral_80

@Composable
fun ChatBubbleWithTriangle(text: String) {
    Box(modifier = Modifier.padding(8.dp)) {
        // 삼각형
        Canvas(
            modifier =
                Modifier
                    .size(20.dp)
                    .align(Alignment.CenterStart),
        ) {
            val path =
                Path().apply {
                    moveTo(0f, size.height / 2)
                    lineTo(size.width, size.height)
                    lineTo(size.width, 0f)
                    close()
                }
            drawPath(path, color = Color(0xFF3A3A3A), style = Fill)
        }

        // 대화 상자
        Box(
            modifier =
                Modifier
                    .padding(start = 12.dp)
                    .background(taltal_neutral_80, shape = RoundedCornerShape(8.dp)),
        ) {
            Text(
                text = text,
                style = dialogue_14rg.copy(color = Color.White),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                lineHeight = 24.sp,
            )
        }
    }
}

@Composable
fun PreviewChatBubbleWithTriangle() {
    ChatBubbleWithTriangle("오후 3시 08분 +1잔")
}

@Preview
@Composable
private fun Alignment() {
    PreviewChatBubbleWithTriangle()
}
