package com.taltal.poison.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.taltal.poison.R
import com.taltal.poison.ui.calendar.CalendarScreen
import com.taltal.poison.ui.mypage.MyPageScreen
import com.taltal.poison.ui.navigation.BottomNavItem
import com.taltal.poison.ui.theme.body_14md
import com.taltal.poison.ui.theme.taltal_neutral_30
import com.taltal.poison.ui.theme.taltal_neutral_5
import com.taltal.poison.ui.theme.taltal_neutral_50
import com.taltal.poison.ui.theme.taltal_neutral_60
import com.taltal.poison.ui.theme.taltal_neutral_90
import com.taltal.poison.ui.theme.taltal_red_50
import com.taltal.poison.ui.theme.taltal_yellow_60
import com.taltal.poison.ui.theme.taltal_yellow_70
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
    val navController = rememberNavController()
    Scaffold(
        modifier =
            Modifier
                .navigationBarsPadding()
                .statusBarsPadding(),
        bottomBar = { PoisonBottomBar(navController) },
    ) { innerPadding ->
        NavHost(
            modifier =
                Modifier
                    .padding(
                        // 탭 스크린 내부 UI 패딩 필요
                        bottom = innerPadding.calculateBottomPadding(),
                    ).background(Color.White),
            navController = navController,
            startDestination = BottomNavItem.Home.screenRoute,
        ) {
            composable(BottomNavItem.Home.screenRoute) {
                HomeLogScreen()
            }
            composable(BottomNavItem.Calendar.screenRoute) {
                CalendarScreen()
            }
            composable(BottomNavItem.MyPage.screenRoute) {
                MyPageScreen()
            }
        }
    }
}

@Composable
fun HomeLogScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var currentJson by remember { mutableStateOf(R.raw.poe_main_0) }
    val currentPoisonAnimationJson = viewModel.currentPoisonJson.collectAsStateWithLifecycle()
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(currentJson))
    var isPlaying by remember { mutableStateOf(false) }
    val progress by animateLottieCompositionAsState(
        composition = lottieComposition,
        isPlaying = isPlaying,
    )
    val context = LocalContext.current

    LaunchedEffect(currentPoisonAnimationJson.value) {
        val resId =
            context.resources.getIdentifier(
                currentPoisonAnimationJson.value.removeSuffix(".json"),
                "raw",
                context.packageName,
            )
        currentJson = resId
    }

    LaunchedEffect(key1 = progress) {
        if (!isPlaying) return@LaunchedEffect
        if (progress == 0f) isPlaying = true
        if (progress == 1f) isPlaying = false
    }

    Box(contentAlignment = Alignment.BottomCenter) {
        // TODO: header
        TodayPoisonStatus(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .align(Alignment.TopCenter)
                    .padding(top = 68.dp),
            currentPoison = uiState.currentPoison,
            poisonPurpose = uiState.purposePoison,
            description = uiState.description,
        )

        LottieAnimation(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 36.dp)
                    .align(Alignment.BottomCenter),
            composition = lottieComposition,
            progress = { progress },
        )
        PoisonAddButton(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 36.dp),
            text = "+ 커피 한 샷",
            onClick = {
                isPlaying = true
                viewModel.drink()
            },
        )
    }
}

@Composable
fun PoisonBottomBar(navController: NavHostController) {
    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    Divider(color = taltal_neutral_5, modifier = Modifier.zIndex(3f))
    Row(
        modifier =
            Modifier
                .background(Color.White)
                .fillMaxWidth()
                .height(80.dp)
                .zIndex(2f),
        verticalAlignment = Alignment.Top,
    ) {
        BottomNavButton(
            screen = BottomNavItem.Home,
            currentDestination = currentDestination,
            navController = navController,
        )
        BottomNavButton(
            screen = BottomNavItem.Calendar,
            currentDestination = currentDestination,
            navController = navController,
        )
        BottomNavButton(
            screen = BottomNavItem.MyPage,
            currentDestination = currentDestination,
            navController = navController,
        )
    }
}

@Composable
fun RowScope.BottomNavButton(
    screen: BottomNavItem,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.screenRoute } == true

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(bottom = 14.dp)
                .noRippleClickable {
                    navController.navigate(screen.screenRoute) {
                        popUpTo(navController.graph.id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }.weight(1f),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxHeight()
                    .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = if (selected) screen.selectedIcon else screen.icon),
                contentDescription = "icon",
                modifier =
                    Modifier
                        .size(24.dp),
            )
            Text(
                text = stringResource(id = screen.title),
                style = title_12bd,
                color = if (selected) taltal_neutral_90 else taltal_neutral_30,
            )
        }
    }
}

fun Modifier.noRippleClickable(onClick: () -> Unit) =
    composed(
        factory = {
            this.then(
                Modifier.clickable(
                    interactionSource =
                        remember {
                            MutableInteractionSource()
                        },
                    indication = null,
                    onClick = { onClick() },
                ),
            )
        },
    )

@Composable
private fun PoisonTypeButton(onClick: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors().copy(containerColor = taltal_neutral_5),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        onClick = onClick,
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_coffee_44),
            contentDescription = "",
        )
        Image(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = R.drawable.ic_arrow_drop_down),
            contentDescription = "",
        )
    }
}

@Composable
private fun TodayPoisonStatus(
    currentPoison: Int,
    poisonPurpose: Int,
    description: String,
    modifier: Modifier = Modifier,
) {
    fun getProgress(
        currentPoison: Int,
        poisonPurpose: Int,
    ): Float =
        if (currentPoison > poisonPurpose) {
            (currentPoison - poisonPurpose).toFloat() / poisonPurpose.toFloat()
        } else {
            currentPoison.toFloat() / poisonPurpose.toFloat()
        }

    fun getProgressBackgroundColor(
        currentPoison: Int,
        poisonPurpose: Int,
    ): Color {
        val criteria = currentPoison.toFloat() / poisonPurpose.toFloat()
        return when {
            criteria <= 1 -> taltal_neutral_5
            criteria < 2 -> taltal_yellow_60
            else -> Color.Black
        }
    }

    fun getProgressColor(
        currentPoison: Int,
        poisonPurpose: Int,
    ): Color {
        val criteria = currentPoison.toFloat() / poisonPurpose.toFloat()
        return when {
            criteria <= 1 -> taltal_yellow_60
            criteria < 2 -> taltal_yellow_70
            else -> Color.Black
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TodayPoisonStatus(
            currentPoison = currentPoison,
            poisonPurpose = poisonPurpose,
        )
        Spacer(modifier = Modifier.height(12.dp))
        TodayPoisonStatusProgressIndicator(
            modifier = Modifier.padding(horizontal = 20.dp),
            progress = getProgress(currentPoison, poisonPurpose),
            progressColor = getProgressColor(currentPoison, poisonPurpose),
            backgroundColor = getProgressBackgroundColor(currentPoison, poisonPurpose),
        )
        Spacer(modifier = Modifier.height(12.dp))
        TodayPoisonStatusDescription(description)
    }
}

@Composable
private fun TodayPoisonStatus(
    currentPoison: Int,
    poisonPurpose: Int,
) {
    fun getCurrentPoisonColor(): Color = if (currentPoison > poisonPurpose) taltal_red_50 else taltal_yellow_60

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "오늘 섭취량", style = title_16sb.copy(color = Color.Black))
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = currentPoison.toString(),
            style = title_24bd.copy(color = getCurrentPoisonColor()),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "/ ${poisonPurpose}샷", color = taltal_neutral_60)
    }
}

@Composable
private fun TodayPoisonStatusProgressIndicator(
    progress: Float,
    progressColor: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    val animatedProgress by animateFloatAsState(targetValue = progress)

    Box(modifier = modifier) {
        LinearProgressIndicator(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(16.dp),
            progress = { 100f },
            color = backgroundColor,
            strokeCap = StrokeCap.Round,
        )
        LinearProgressIndicator(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(16.dp),
            progress = { animatedProgress },
            color = progressColor,
            trackColor = Color.Transparent,
            strokeCap = StrokeCap.Round,
        )
    }
}

@Composable
private fun TodayPoisonStatusDescription(description: String) {
    Text(
        text = description,
        style = body_14md,
        color = taltal_neutral_50,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun PoisonAddButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.btn_main_pressed))
    var isPlaying by remember { mutableStateOf(false) }
    val progress by animateLottieCompositionAsState(
        composition = lottieComposition,
        isPlaying = isPlaying,
    )

    LaunchedEffect(key1 = progress) {
        if (!isPlaying) return@LaunchedEffect
        if (progress == 0f) isPlaying = true
        if (progress == 1f) isPlaying = false
    }

    Box {
        ElevatedCard(
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
            modifier = modifier.align(Alignment.Center),
            onClick = {
                isPlaying = true
                onClick.invoke()
            },
        ) {
            Column(
                modifier =
                    Modifier
                        .background(Color.White)
                        .padding(vertical = 12.dp, horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    modifier = Modifier.height(44.dp),
                    painter = painterResource(id = R.drawable.ic_coffee_44),
                    contentDescription = "",
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = text, style = title_16sb.copy(color = taltal_neutral_90))
            }
        }
        LottieAnimation(
            modifier =
                Modifier
                    .height(130.dp)
                    .align(Alignment.Center),
            composition = lottieComposition,
            progress = { progress },
        )
    }
}
