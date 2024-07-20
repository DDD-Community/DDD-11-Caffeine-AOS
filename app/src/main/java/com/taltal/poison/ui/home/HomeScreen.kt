package com.taltal.poison.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taltal.poison.R
import com.taltal.poison.ui.theme.body_14md
import com.taltal.poison.ui.theme.taltal_neutral_5
import com.taltal.poison.ui.theme.taltal_neutral_50
import com.taltal.poison.ui.theme.taltal_neutral_60
import com.taltal.poison.ui.theme.taltal_red_60
import com.taltal.poison.ui.theme.taltal_yellow_50
import com.taltal.poison.ui.theme.taltal_yellow_60
import com.taltal.poison.ui.theme.title_12bd
import com.taltal.poison.ui.theme.title_16sb
import com.taltal.poison.ui.theme.title_24bd

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun HomeScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { HomeTopBar {} },
        bottomBar = { HomeBottomBar {} }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            TodayPoisonStatus(
                currentPoison = 6,
                poisonPurpose = 4,
                poisonUnit = "샷",
                description = "카페인 과다섭취는 두통, 불면증, 행동 불안,\n혈압 상승 등을 일으킬 수 있대요!",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp),
                painter = painterResource(id = R.drawable.ic_coffee_44),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(20.dp))
            PoisonAddButton(text = "+ 커피 한 샷", onClick = { /*TODO*/ })
            Spacer(modifier = Modifier.height(36.dp))
        }
    }
}

@Composable
private fun HomeTopBar(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(end = 16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PoisonTypeButton(onClick)
    }
}

@Composable
private fun HomeBottomBar(onHomeClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .background(taltal_neutral_5)
                .height(2.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 2.dp, bottom = 6.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HomeBottomNavItem(
                title = "홈",
                imageResId = R.drawable.ic_home_24,
                onClick = onHomeClick
            )
        }
    }
}

@Composable
private fun HomeBottomNavItem(
    title: String,
    imageResId: Int,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .clickable { onClick.invoke() }
            .padding(top = 12.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.padding(horizontal = 12.dp)) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = title, style = title_12bd, color = Color.Black)
    }
}

@Composable
private fun PoisonTypeButton(
    onClick: () -> Unit,
) {
    Button(
        colors = ButtonDefaults.buttonColors().copy(containerColor = taltal_neutral_5),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        onClick = onClick
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_coffee_44),
            contentDescription = ""
        )
        Image(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = R.drawable.ic_arrow_drop_down),
            contentDescription = ""
        )
    }
}

@Composable
private fun TodayPoisonStatus(
    currentPoison: Int,
    poisonPurpose: Int,
    poisonUnit: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TodayPoisonStatus(
            currentPoison = currentPoison,
            poisonPurpose = poisonPurpose,
            poisonUnit = poisonUnit
        )
        Spacer(modifier = Modifier.height(12.dp))
        TodayPoisonStatusProgressIndicator(
            progress = 0.5f,
            progressColor = taltal_yellow_50,
            backgroundColor = taltal_neutral_5
        )
        Spacer(modifier = Modifier.height(12.dp))
        TodayPoisonStatusDescription(description)
    }
}

@Composable
private fun TodayPoisonStatus(
    currentPoison: Int,
    poisonPurpose: Int,
    poisonUnit: String,
) {
    fun getCurrentPoisonColor(): Color =
        if (currentPoison > poisonPurpose) taltal_red_60 else taltal_yellow_60

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "오늘 섭취량", color = Color.Black, style = title_16sb)
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = currentPoison.toString(),
            color = getCurrentPoisonColor(),
            style = title_24bd
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "/ $poisonPurpose$poisonUnit", color = taltal_neutral_60)
    }
}

@Composable
private fun TodayPoisonStatusProgressIndicator(
    progress: Float,
    progressColor: Color,
    backgroundColor: Color,
) {
    Box {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp),
            progress = { 100f },
            color = backgroundColor,
            strokeCap = StrokeCap.Round
        )
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp),
            progress = { progress },
            color = progressColor,
            trackColor = Color.Transparent,
            strokeCap = StrokeCap.Round
        )
    }
}

@Composable
private fun TodayPoisonStatusDescription(description: String) {
    Text(
        text = description,
        style = body_14md,
        color = taltal_neutral_50,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun PoisonAddButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        modifier = modifier,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(vertical = 12.dp, horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.height(44.dp),
                painter = painterResource(id = R.drawable.ic_coffee_44),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text, style = title_16sb)
        }
    }
}
