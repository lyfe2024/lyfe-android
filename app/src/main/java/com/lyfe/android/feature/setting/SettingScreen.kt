package com.lyfe.android.feature.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.component.LyfeModal
import com.lyfe.android.core.common.ui.component.LyfeSwitch
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.theme.DEFAULT
import com.lyfe.android.core.common.ui.theme.Main500
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun SettingScreen(
	navigator: LyfeNavigator
) {
	Column(
		modifier = Modifier
			.padding(top = 16.dp, bottom = 24.dp)
			.fillMaxSize()
	) {
		Text(
			modifier = Modifier.padding(start = 20.dp),
			text = stringResource(R.string.setting_screen_title),
			style = TextStyle(
				fontSize = 24.sp,
				lineHeight = 36.sp,
				fontWeight = FontWeight.W700,
				color = Color.Black
			)
		)

		Spacer(modifier = Modifier.height(16.dp))

		val list = listOf(
			Setting.NOTIFICATION,
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
	var showModal by remember { mutableStateOf(false) }

	Box(
		modifier = Modifier.fillMaxSize()
	) {
		Column {
			list.forEach { setting ->
				when (setting) {
					Setting.NOTIFICATION -> { SettingSwitchRow(stringResource(R.string.setting_screen_notification)) }
					Setting.SCRAP -> {
						SettingButtonRow(stringResource(R.string.setting_screen_scrap)) {
							// TODO
						}
					}
					Setting.USER_EXPERIENCE -> SettingButtonRow(stringResource(R.string.user_experience_title)) {
						navigator.navigate(LyfeScreens.UserExperience.route)
					}
					Setting.PRIVACY_POLICY -> SettingButtonRow(stringResource(R.string.setting_screen_privacy_policy)) {
						// TODO
					}
					Setting.DELETE_ACCOUNT -> SettingButtonRow(stringResource(R.string.setting_screen_delete_account)) {
						// 회원탈퇴 창 생성
						showModal = true
					}
				}
			}

			Spacer(modifier = Modifier.weight(1f))

			// 로그아웃 버튼
			LyfeButton(
				modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 20.dp),
				verticalPadding = 12.dp,
				buttonType = LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT,
				text = stringResource(R.string.setting_screen_logout),
				isClearIconShow = false
			) {
				// TODO 로그아웃
			}
		}

		if (showModal) {
			LyfeModal(
				title = stringResource(R.string.delete_dialog_title),
				message = "",
				confirmBtnText = stringResource(R.string.confirm),
				dismissBtnText = stringResource(R.string.nope),
				onConfirm = { showModal = false },
				onDismiss = { showModal = false }
			)
		}
	}
}

@Composable
fun SettingSwitchRow(title: String) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 12.dp, horizontal = 20.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(
			text = title,
			fontSize = 16.sp,
			color = DEFAULT,
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
fun SettingButtonRow(title: String, onClick: () -> Unit) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.clickable { onClick() }
			.padding(vertical = 12.dp, horizontal = 20.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(
			text = title,
			fontSize = 16.sp,
			color = DEFAULT,
			fontWeight = FontWeight.W500
		)

		Spacer(modifier = Modifier.weight(1f))

		Image(
			painter = painterResource(id = R.drawable.ic_arrow_next),
			contentDescription = "ic_next"
		)
	}
}