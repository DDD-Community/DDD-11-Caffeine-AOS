package com.taltal.poison.ui.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.taltal.poison.R
import com.taltal.poison.ui.theme.body_16md
import com.taltal.poison.ui.theme.taltal_neutral_10
import com.taltal.poison.ui.theme.taltal_neutral_20
import com.taltal.poison.ui.theme.taltal_neutral_40
import com.taltal.poison.ui.theme.taltal_neutral_60
import com.taltal.poison.ui.theme.taltal_neutral_90
import com.taltal.poison.ui.theme.taltal_yellow_10
import com.taltal.poison.ui.theme.taltal_yellow_60
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

@Composable
fun OptionSelection(
    modifier: Modifier = Modifier,
    currentOption: String = "",
    options: List<String>,
    updateSelectedOption: (String) -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OptionButton(
            text = options[0],
            isSelected = currentOption == options[0],
            onClick = { updateSelectedOption(options[0]) },
            isLeft = true,
            modifier = Modifier.weight(1f)
        )
        OptionButton(
            text = options[1],
            isSelected = currentOption == options[1],
            onClick = { updateSelectedOption(options[1]) },
            isLeft = false,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun OptionButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    isLeft: Boolean,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(
                if (isSelected) taltal_yellow_10 else Color.White,
                shape = RoundedCornerShape(
                    topStart = if (isLeft) 16.dp else 0.dp,
                    bottomStart = if (isLeft) 16.dp else 0.dp,
                    topEnd = if (isLeft) 0.dp else 16.dp,
                    bottomEnd = if (isLeft) 0.dp else 16.dp
                )
            )
            .border(
                1.dp,
                if (isSelected) taltal_yellow_60 else taltal_neutral_10,
                RoundedCornerShape(
                    topStart = if (isLeft) 16.dp else 0.dp,
                    bottomStart = if (isLeft) 16.dp else 0.dp,
                    topEnd = if (isLeft) 0.dp else 16.dp,
                    bottomEnd = if (isLeft) 0.dp else 16.dp
                )
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = taltal_yellow_60,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = text,
                color = if (isSelected) Color.Black else taltal_neutral_60,
                fontSize = 16.sp,
                style = body_16md,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
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

@Preview
@Composable
private fun OptionalButtonPreview() {
    OptionSelection(options = listOf("Option 1", "Option 2"))
}
