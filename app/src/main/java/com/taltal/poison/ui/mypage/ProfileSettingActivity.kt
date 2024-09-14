package com.taltal.poison.ui.mypage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.taltal.poison.R
import com.taltal.poison.ui.theme.title_18sb
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileSettingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProfileSettingScreen(
                onBackPressed = { finish() },
            )
        }
    }
}

@Composable
fun ProfileSettingScreen(onBackPressed: () -> Unit) {
    val viewModel = hiltViewModel<MyPageViewModel>()
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
        ) {
            ProfileHeaderSection(onBackPressed = onBackPressed)
            MyProfileSection(viewModel)
        }
    }
}

@Composable
fun ProfileHeaderSection(onBackPressed: () -> Unit) {
    Row(modifier = Modifier.height(56.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back_24),
            contentDescription = "back",
            modifier = Modifier.clickable { onBackPressed() }.padding(horizontal = 16.dp, vertical = 16.dp),
        )

        Text(
            text = "내 정보 수정",
            modifier = Modifier.padding( top = 16.dp),
            style = title_18sb,
        )
    }
}
