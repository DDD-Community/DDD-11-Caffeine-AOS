package com.taltal.poison.ui.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.taltal.poison.R
import com.taltal.poison.ui.designsystem.CharacterMessage
import com.taltal.poison.ui.designsystem.MESSAGE_TAIL_BOTTOM

@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier) {
    val viewModel: OnBoardingViewModel = hiltViewModel()
    val pagerState = viewModel.pagerState

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            OnBoardingHeader(
                onClickBackButton = { viewModel.moveToPreviousPage() }, pagerState = pagerState
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        OnBoardingViewPager(
            viewModel = viewModel,
            pagerState = pagerState,
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
        )
    }
}

@Composable
fun OnBoardingHeader(
    onClickBackButton: () -> Unit = { },
    pagerState: PagerState
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        if (pagerState.currentPage != NICKNAME_PAGE) {
            IconButton(onClick = onClickBackButton) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back_24),
                    contentDescription = null
                )
            }
        } else {
            Spacer(modifier = Modifier.size(48.dp))
        }
    }
}

@Composable
fun OnBoardingViewPager(
    viewModel: OnBoardingViewModel = hiltViewModel(),
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        state = pagerState,
        userScrollEnabled = false
    ) { page ->
        when (page) {
            NICKNAME_PAGE -> NicknameSection(
                viewModel = viewModel
            )

            PROFILE_PAGE -> ProfileSection(viewModel = viewModel)
            PURPOSE_PAGE -> PurposeSection(viewModel = viewModel)
            LOADING_PAGE -> LoadingScreen(viewModel = viewModel)
            GOAL_PAGE -> GoalSection()
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier, viewModel: OnBoardingViewModel = hiltViewModel()) {
    viewModel.delayAndMoveToNextPage()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 92.dp)
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CharacterMessage(text = messageList[3], tailPosition = MESSAGE_TAIL_BOTTOM)
        Image(
            painter = painterResource(id = R.drawable.happy_poe),
            contentDescription = null,
            modifier = Modifier.size(width = 100.dp, height = 138.dp)
        )
    }
}

@Preview
@Composable
private fun OnBoardingPreview() {
    OnBoardingScreen()
}

const val NICKNAME_PAGE = 0
const val PROFILE_PAGE = 1
const val PURPOSE_PAGE = 2
const val LOADING_PAGE = 3
const val GOAL_PAGE = 4