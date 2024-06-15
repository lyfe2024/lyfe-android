package com.lyfe.android.feature.setting

import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.component.LyfeModal
import com.lyfe.android.core.common.ui.component.LyfeSnackBarIconType
import com.lyfe.android.core.common.ui.component.LyfeSwitch
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.theme.Body2
import com.lyfe.android.core.common.ui.theme.DEFAULT
import com.lyfe.android.core.common.ui.theme.H3
import com.lyfe.android.core.common.ui.theme.Main500
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.feature.login.AppleLoginManager
import com.lyfe.android.feature.login.GoogleLoginManager
import com.lyfe.android.feature.login.KakaoLoginManager
import com.lyfe.android.feature.login.SocialType

@Composable
fun SettingScreen(
	viewModel: SettingViewModel = hiltViewModel(),
	navigator: LyfeNavigator,
	onShowSnackBar: (LyfeSnackBarIconType, String) -> Unit
) {
	var showModal by remember { mutableStateOf(false) }
	val menuList = listOf(
		Setting.NOTIFICATION,
		Setting.USER_EXPERIENCE,
		Setting.TERMS,
		Setting.PRIVACY_POLICY,
		Setting.DELETE_ACCOUNT
	)

	SettingContent(
		navigator = navigator,
		menuList = menuList,
		socialType = viewModel.socialType,
		showModal = showModal,
		onLogoutSuccess = {
			viewModel.deleteLocalData()
			viewModel.updateUiState(SettingUiState.LogoutSuccess)
		},
		onLogoutFailure = { throwable ->
			viewModel.updateUiState(SettingUiState.Failure(throwable?.message))
		},
		onDeleteAccount = {
			// 회원 탈퇴
			viewModel.deleteAccount()
			showModal = false
		},
		onModalVisibilityChanged = {
			showModal = it
		}
	)

	when (viewModel.uiState) {
		SettingUiState.DeleteAccountSuccess -> {
			onShowSnackBar(
				LyfeSnackBarIconType.SUCCESS,
				stringResource(R.string.setting_delete_account_success)
			)
		}
		SettingUiState.LogoutSuccess -> {
			onShowSnackBar(
				LyfeSnackBarIconType.SUCCESS,
				stringResource(R.string.setting_logout_success)
			)
			navigator.navigateAndroidClearBackStack(LyfeScreens.Login.name)
		}
		is SettingUiState.Failure -> {
			val message = (viewModel.uiState as SettingUiState.Failure).message
			onShowSnackBar(
				LyfeSnackBarIconType.ERROR,
				message.orEmpty()
			)
		}
		SettingUiState.IDLE -> { }
		SettingUiState.Loading -> {
			// TODO 로딩창 보여주기
		}
	}
}

@Composable
private fun SettingContent(
	navigator: LyfeNavigator,
	menuList: List<Setting>,
	socialType: String,
	showModal: Boolean,
	onLogoutSuccess: () -> Unit,
	onLogoutFailure: (Throwable?) -> Unit,
	onDeleteAccount: () -> Unit,
	onModalVisibilityChanged: (Boolean) -> Unit
) {
	Column(
		modifier = Modifier
			.padding(top = 16.dp, bottom = 24.dp)
			.fillMaxSize()
	) {
		Text(
			modifier = Modifier.padding(start = 20.dp),
			text = stringResource(R.string.setting_screen_title),
			color = Color.Black,
			style = H3
		)

		Spacer(modifier = Modifier.height(16.dp))

		SettingMenuList(
			list = menuList,
			socialType = socialType,
			onMenuClick = { menu ->
				when(menu) {
					Setting.USER_EXPERIENCE -> {
						navigator.navigate(LyfeScreens.Feedback.name)
					}
					Setting.TERMS -> {
						navigator.navigate(LyfeScreens.ServiceTerms.name)
					}
					Setting.PRIVACY_POLICY -> {
						navigator.navigate(LyfeScreens.PersonalInfoTermsScreen.name)
					}
					Setting.DELETE_ACCOUNT -> {
						// 회원탈퇴 창 생성
						onModalVisibilityChanged(true)
					}
					else -> {}
				}
			},
			onLogoutSuccess = onLogoutSuccess,
			onLogoutFailure = onLogoutFailure
		)

		SettingModal(
			showModal = showModal,
			onConfirm = onDeleteAccount,
			onDismiss = {
				onModalVisibilityChanged(false)
			}
		)
	}
}

@Composable
private fun SettingMenuList(
	list: List<Setting>,
	socialType: String,
	onMenuClick: (Setting) -> Unit,
	onLogoutSuccess: () -> Unit,
	onLogoutFailure: (Throwable?) -> Unit,
) {
	val context = LocalContext.current

	Box(
		modifier = Modifier.fillMaxSize()
	) {
		Column {
			list.forEach { setting ->
				when (setting) {
					Setting.NOTIFICATION -> {
						SettingSwitchRow(stringResource(setting.content))
					}
					else -> {
						SettingButtonRow(title = stringResource(id = setting.content)) {
							onMenuClick(setting)
						}
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
				// 로그아웃
				when (socialType) {
					SocialType.KAKAO.name ->
						KakaoLoginManager.logout(
							onFailure = onLogoutFailure,
							onSuccess = onLogoutSuccess
						)
					SocialType.GOOGLE.name ->
						GoogleLoginManager.signOut(
							context = context,
							onFailure = onLogoutFailure,
							onSuccess = onLogoutSuccess
						)
					SocialType.APPLE.name ->
						AppleLoginManager.signOut(
							onFailure = onLogoutFailure,
							onSuccess = onLogoutSuccess
						)
				}
			}
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
			color = DEFAULT,
			style = Body2
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
fun SettingButtonRow(
	title: String,
	onClick: () -> Unit
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.clickableSingle { onClick() }
			.padding(
				vertical = 12.dp,
				horizontal = 20.dp
			),
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(
			text = title,
			style = Body2,
			color = DEFAULT,
		)

		Spacer(modifier = Modifier.weight(1f))

		Image(
			painter = painterResource(id = R.drawable.ic_arrow_next),
			contentDescription = "ic_next"
		)
	}
}

@Composable
private fun SettingModal(
	showModal: Boolean,
	onConfirm: () -> Unit,
	onDismiss: () -> Unit
) {
	if (showModal) {
		LyfeModal(
			title = stringResource(R.string.delete_dialog_title),
			message = "",
			confirmBtnText = stringResource(R.string.confirm),
			dismissBtnText = stringResource(R.string.nope),
			onConfirm = onConfirm,
			onDismiss = onDismiss
		)
	}
}