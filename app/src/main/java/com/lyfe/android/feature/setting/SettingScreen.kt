package com.lyfe.android.feature.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.component.LyfeSwitch
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.ui.theme.Default
import com.lyfe.android.ui.theme.Main500

@Composable
fun SettingScreen(
    navigator: LyfeNavigator,
    viewModel: SettingViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 24.dp)
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = "설정",
            style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight.W700,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        val list = listOf(
            Setting.ALARM,
            Setting.SCRAP,
            Setting.USER_EXPERIENCE,
            Setting.PRIVACY_POLICY,
            Setting.DELETE_ACCOUNT
        )

        SettingContent(navigator, list)
    }
}

@Composable
fun SettingContent(
    navigator: LyfeNavigator,
    list: List<Setting>
) {
    Column {
        list.forEach { setting ->
            when(setting) {
                Setting.ALARM -> { SettingSwitchRow(setting = setting) }
                Setting.SCRAP -> {
                    SettingButtonRow(setting = setting) {
                        // TODO
                    }
                }
                Setting.USER_EXPERIENCE -> SettingButtonRow(setting = setting) {
                    navigator.navigate(LyfeScreens.UserExperience.route)
                }
                Setting.PRIVACY_POLICY -> SettingButtonRow(setting = setting) {
                    // TODO
                }
                Setting.DELETE_ACCOUNT -> SettingButtonRow(setting = setting) {
                    // TODO
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // 로그아웃 버튼
        LyfeButton(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalPadding = 12.dp,
            buttonType = LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT,
            text = "로그아웃",
            isClearIconShow = false
        ) {
            // TODO 로그아웃
        }
    }
}

@Composable
fun SettingSwitchRow(setting: Setting) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = setting.title,
            fontSize = 16.sp,
            color = Default,
            fontWeight = FontWeight.W500
        )

        Spacer(modifier = Modifier.weight(1f))

        var checkedState by remember { mutableStateOf(false) }

        LyfeSwitch(
            checkedTrackColor = Main500,
            onCheckedChange = { checkedState = it }
        )
    }
}

@Composable
fun SettingButtonRow(setting: Setting, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = setting.title,
            fontSize = 16.sp,
            color = Default,
            fontWeight = FontWeight.W500
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.ic_arrow_next),
            contentDescription = "다음으로 넘어가는 버튼 이미지"
        )
    }
}