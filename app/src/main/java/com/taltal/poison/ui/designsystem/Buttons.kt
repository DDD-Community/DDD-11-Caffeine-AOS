package com.taltal.poison.ui.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.taltal.poison.R
import com.taltal.poison.ui.theme.body_16md
import com.taltal.poison.ui.theme.taltal_neutral_10
import com.taltal.poison.ui.theme.taltal_neutral_20
import com.taltal.poison.ui.theme.taltal_neutral_40
import com.taltal.poison.ui.theme.taltal_neutral_60
import com.taltal.poison.ui.theme.taltal_neutral_90
import com.taltal.poison.ui.theme.taltal_yellow_10
import com.taltal.poison.ui.theme.taltal_yellow_60
import com.taltal.poison.ui.theme.title_16sb
import com.taltal.poison.ui.theme.title_20sb

@Composable
fun SegmentedButton(
    label: String,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    leftRadius: Dp = 0.dp,
    rightRadius: Dp = 0.dp,
    onCheckedChange: (Boolean) -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(12.dp)
            .clip(
                RoundedCornerShape(
                    topStart = leftRadius,
                    topEnd = rightRadius,
                    bottomStart = leftRadius,
                    bottomEnd = rightRadius
                )
            )
            .background(if (isChecked) taltal_yellow_10 else Color.White)
            .border(1.dp, if (isChecked) taltal_yellow_60 else taltal_neutral_10)
            .clickable { onCheckedChange.invoke(isChecked.not()) }
    ) {
        Image(painter = painterResource(id = R.drawable.ic_check_24), contentDescription = null)
        Text(
            text = label,
            style = body_16md,
            color = if (isChecked) taltal_neutral_90 else taltal_neutral_60
        )
    }
}

@Composable
fun ConfirmButton(
    text: String,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(if (isEnabled) taltal_yellow_60 else taltal_neutral_20)
            .clickable(enabled = isEnabled) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = title_20sb,
            textAlign = TextAlign.Center,
            color = if (isEnabled) taltal_neutral_90 else taltal_neutral_40
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SegmentedButtonPreview() {
    SegmentedButton(label = "쉬에트", isChecked = true, leftRadius = 12.dp, rightRadius = 12.dp)
}

@Preview
@Composable
private fun ConfirmButtonEnabledPreview() {
    ConfirmButton("다음", isEnabled = true)
}

@Preview
@Composable
private fun ConfirmButtonDisabledPreview() {
    ConfirmButton("다음", isEnabled = false)
}
