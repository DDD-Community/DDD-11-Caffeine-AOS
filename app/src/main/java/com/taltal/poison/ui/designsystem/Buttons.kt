package com.taltal.poison.ui.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taltal.poison.R
import com.taltal.poison.ui.theme.TalTal_Neutral_10
import com.taltal.poison.ui.theme.TalTal_Neutral_60
import com.taltal.poison.ui.theme.TalTal_Neutral_90
import com.taltal.poison.ui.theme.TalTal_Yellow_10
import com.taltal.poison.ui.theme.TalTal_Yellow_60
import com.taltal.poison.ui.theme.body_16md

@Composable
fun SegmentedButton(
    label: String,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    leftCurved: Boolean = false,
    rightCurved: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(12.dp)
            .clip(
                RoundedCornerShape(
                    topStart = if (leftCurved) 12.dp else 0.dp,
                    topEnd = if (rightCurved) 12.dp else 0.dp,
                    bottomStart = if (leftCurved) 12.dp else 0.dp,
                    bottomEnd = if (rightCurved) 12.dp else 0.dp
                )
            )
            .background(if (isChecked) TalTal_Yellow_10 else Color.White)
            .border(1.dp, if (isChecked) TalTal_Yellow_60 else TalTal_Neutral_10)
            .clickable { onCheckedChange.invoke(isChecked.not()) }
    ) {
        Image(painter = painterResource(id = R.drawable.ic_check_24), contentDescription = null)
        Text(
            text = label,
            style = body_16md,
            color = if (isChecked) TalTal_Neutral_90 else TalTal_Neutral_60
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SegmentedButtonPreview() {
    SegmentedButton(label = "쉬에트", isChecked = true, leftCurved = true, rightCurved = true)
}
