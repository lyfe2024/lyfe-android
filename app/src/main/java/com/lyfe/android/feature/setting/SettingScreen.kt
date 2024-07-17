package com.lyfe.android.feature.setting

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
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
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.component.LyfeModal
import com.lyfe.android.core.common.ui.component.LyfeSnackBarIconType
import com.lyfe.android.core.common.ui.component.LyfeSwitch
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.permission.PermissionAlertDialogs
import com.lyfe.android.core.common.ui.permission.PermissionsCheckScreen
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
	val context = LocalContext.current
	var showModal by remember { mutableStateOf(false) }
	var isNotificationAllowed by remember { mutableStateOf(isNotificationPermissionAllowed(context)) }

	Column(
		modifier = Modifier
			.padding(top = 16.dp, bottom = 24.dp)
			.fillMaxSize()
	) {
		SettingTopArea {
			navigator.navigateUp()
		}

		Spacer(modifier = Modifier.height(16.dp))

		SettingContent(
			navigator = navigator,
			menuList = viewModel.menuList,
			isGuest = viewModel.isGuest,
			socialType = viewModel.socialType,
			showModal = showModal,
			isNotificationAllowed = isNotificationAllowed,
			onNotificationToggle = { allowed ->
				if (allowed) {
					viewModel.checkPermission()
				} else {
					viewModel.denyPermission()
				}
			},
			onLogoutSuccess = {
				viewModel.logout()
			},
			onLogoutFailure = { throwable ->
				viewModel.emitEvent(SettingUiEvent.Failure(throwable?.message))
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
	}

	val event = viewModel.event.collectAsStateWithLifecycle(initialValue = SettingUiEvent.IDLE)
	when (event.value) {
		SettingUiEvent.IDLE -> {}
		SettingUiEvent.DeleteAccountSuccess -> {
			onShowSnackBar(
				LyfeSnackBarIconType.SUCCESS,
				stringResource(R.string.setting_delete_account_success)
			)
		}
		SettingUiEvent.LogoutSuccess -> {
			onShowSnackBar(
				LyfeSnackBarIconType.SUCCESS,
				stringResource(R.string.setting_logout_success)
			)
			navigator.navigateAndroidClearBackStack(LyfeScreens.Login.name)
		}
		is SettingUiEvent.Failure -> {
			val message = (event.value as? SettingUiEvent.Failure)?.message
			onShowSnackBar(
				LyfeSnackBarIconType.ERROR,
				message.orEmpty()
			)
		}
		SettingUiEvent.CheckPermission -> {
			val notificationPermission = viewModel.notificationPermission?.permission ?: return
			PermissionsCheckScreen(
				neededPermissions = arrayOf(notificationPermission)
			) { passedPermissions, _ ->
				viewModel.checkPermissionResult(passedPermissions.getOrNull(0))
			}
		}
		SettingUiEvent.ShowPermissionAlertDialog -> {
			val permission = viewModel.notificationPermission ?: return
			PermissionAlertDialogs(
				failedPermissionList = listOf(permission),
				permissionSuccess = {
					isNotificationAllowed = true
				},
				onDismiss = {
					isNotificationAllowed = isNotificationPermissionAllowed(context)
				}
			)
		}
		SettingUiEvent.NotificationAllowed -> {
			isNotificationAllowed = true
		}

		SettingUiEvent.Loading -> {
			// TODO 로딩창 보여주기
		}
	}
}

@Composable
private fun SettingTopArea(
	onBack: () -> Unit
) {
	Column(
		modifier = Modifier.padding(start = 20.dp)
	) {
		Icon(
			modifier = Modifier
				.size(24.dp)
				.clickableSingle { onBack() },
			painter = painterResource(id = R.drawable.ic_arrow_back),
			contentDescription = "ic_arrow_back",
			tint = Color.Black
		)

		Spacer(modifier = Modifier.height(16.dp))

		Text(
			text = stringResource(R.string.setting_screen_title),
			color = Color.Black,
			style = H3
		)
	}
}

@Composable
private fun SettingContent(
	navigator: LyfeNavigator,
	menuList: List<Setting>,
	isGuest: Boolean,
	socialType: String,
	showModal: Boolean,
	isNotificationAllowed: Boolean,
	onNotificationToggle: (Boolean) -> Unit,
	onLogoutSuccess: () -> Unit,
	onLogoutFailure: (Throwable?) -> Unit,
	onDeleteAccount: () -> Unit,
	onModalVisibilityChanged: (Boolean) -> Unit
) {
	SettingMenuList(
		list = menuList,
		isGuest = isGuest,
		socialType = socialType,
		isNotificationAllowed = isNotificationAllowed,
		onMenuClick = { menu ->
			when (menu) {
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
		onNotificationToggle = onNotificationToggle,
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

@Composable
private fun SettingMenuList(
	list: List<Setting>,
	isGuest: Boolean,
	socialType: String,
	isNotificationAllowed: Boolean,
	onMenuClick: (Setting) -> Unit,
	onNotificationToggle: (Boolean) -> Unit,
	onLogoutSuccess: () -> Unit,
	onLogoutFailure: (Throwable?) -> Unit
) {
	val context = LocalContext.current

	Box(
		modifier = Modifier.fillMaxSize()
	) {
		Column {
			list.forEach { setting ->
				when (setting) {
					Setting.NOTIFICATION -> {
						SettingSwitchRow(
							title = stringResource(setting.content),
							isNotificationAllowed = isNotificationAllowed,
							onNotificationToggle = onNotificationToggle
						)
					}
					else -> {
						SettingButtonRow(
							title = stringResource(id = setting.content)
						) {
							onMenuClick(setting)
						}
					}
				}
			}

			Spacer(modifier = Modifier.weight(1f))

			if (isGuest.not()) {
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
}

@Composable
fun SettingSwitchRow(
	title: String,
	isNotificationAllowed: Boolean,
	onNotificationToggle: (Boolean) -> Unit
) {
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

		LyfeSwitch(
			checked = isNotificationAllowed,
			checkedTrackColor = Main500,
			onCheckedChange = onNotificationToggle
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
			color = DEFAULT
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

private fun isNotificationPermissionAllowed(
	context: Context
): Boolean {
	return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
		ContextCompat.checkSelfPermission(
			context,
			android.Manifest.permission.POST_NOTIFICATIONS
		) == PackageManager.PERMISSION_GRANTED
	} else {
		NotificationManagerCompat.from(context).areNotificationsEnabled()
	}
}