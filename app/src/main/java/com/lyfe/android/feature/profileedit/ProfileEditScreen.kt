package com.lyfe.android.feature.profileedit

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.component.LyfeSnackBarIconType
import com.lyfe.android.core.common.ui.component.LyfeTextField
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.definition.LyfeTextFieldType
import com.lyfe.android.core.common.ui.theme.Green50
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.theme.Red50
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun ProfileEditScreen(
	navigator: LyfeNavigator,
	viewModel: ProfileEditViewModel = hiltViewModel(),
	onShowSnackBar: (LyfeSnackBarIconType, String) -> Unit
) {
	Column(
		modifier = Modifier
			.padding(top = 40.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
			.fillMaxSize()
	) {
		Text(
			text = stringResource(R.string.profile_edit_title),
			style = TextStyle(
				fontSize = 24.sp,
				lineHeight = 36.sp,
				fontWeight = FontWeight.W700,
				color = Color.Black
			)
		)

		Spacer(modifier = Modifier.height(21.dp))

		ProfileEditContentArea(
			navigator = navigator,
			viewModel = viewModel,
			onShowSnackBar = onShowSnackBar
		)
	}
}

@Composable
private fun ProfileEditContentArea(
	navigator: LyfeNavigator,
	viewModel: ProfileEditViewModel,
	onShowSnackBar: (LyfeSnackBarIconType, String) -> Unit
) {
	// ViewModel uiState 에 따라서 화면 표시 여부 달라짐
	when (viewModel.uiState) {
		is ProfileEditUiState.IDLE -> {
			// 처음 화면에 보일 닉네임은 로컬 저장소에서 가져옴.
			ProfileEditContent(
				viewModel = viewModel,
				nickname = viewModel.nickname
			)
		}
		is ProfileEditUiState.Success -> {
			// 프로필 변경 완료하면 토스트 메세지 띄우고 이전 화면으로
			onShowSnackBar(
				LyfeSnackBarIconType.SUCCESS,
				stringResource(id = R.string.edit_nickname_complete)
			)
			navigator.navigateUp()
		}
		is ProfileEditUiState.Failure -> {
			val error = viewModel.uiState as ProfileEditUiState.Failure
			onShowSnackBar(
				LyfeSnackBarIconType.ERROR,
				error.message
			)
			ProfileEditContent(
				viewModel = viewModel,
				nickname = viewModel.nickname
			)
		}
		is ProfileEditUiState.Loading -> {
			// 로딩하는 동안 Progressbar 보여주기
		}
	}
}

@Composable
private fun ProfileEditContent(
	viewModel: ProfileEditViewModel,
	nickname: String
) {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 12.dp, bottom = 12.dp)
	) {
		Column(
			modifier = Modifier.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			ProfileEditThumbnailContent(viewModel)

			Spacer(modifier = Modifier.height(40.dp))

			ProfileEditNicknameTextField(
				nickname = nickname,
				onNicknameChanged = { viewModel.setNickname(it) }
			)

			Spacer(modifier = Modifier.height(8.dp))

			ProfileEditNicknameConditionTextArea(viewModel = viewModel)

			Spacer(modifier = Modifier.weight(1f))

			ProfileEditCompleteButton(viewModel = viewModel)
		}
	}
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ProfileEditThumbnailContent(
	viewModel: ProfileEditViewModel
) {
	val context = LocalContext.current
	// 프로필 이미지 변경하는 부분
	Box(
		modifier = Modifier.size(92.dp)
	) {
		// Gallery Launcher
		val galleryLauncher = rememberLauncherForActivityResult(
			contract = ActivityResultContracts.GetContent(),
			onResult = {
				if (it == null) {
					return@rememberLauncherForActivityResult
				}
				val cursor = context.contentResolver.query(it, null, null, null, null)
				if (cursor?.moveToNext() == true) {
					val path = cursor.getString(cursor.getColumnIndexOrThrow("_data"))
					viewModel.updateProfileImageFilePath(path)
				}
				cursor?.close()
			}
		)
		// 클릭하면 앨범으로 이동
		val onClick = { galleryLauncher.launch("image/*") }

		GlideImage(
			model = if (viewModel.imagePath == null) {
				viewModel.profileImage
			} else {
				viewModel.imagePath
			},
			contentDescription = "프로필 이미지",
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.align(Center)
				.size(80.dp)
				.clip(CircleShape)
				.border(width = 1.dp, Grey200, CircleShape),
			failure = placeholder(painterResource(id = R.drawable.ic_profile_default))
		)

		Image(
			painter = painterResource(id = R.drawable.ic_add_circle_fill),
			contentDescription = "프로필 변경",
			modifier = Modifier
				.size(24.dp)
				.clip(CircleShape)
				.align(BottomEnd)
				.clickable { onClick() }
		)
	}
}

@Composable
private fun ProfileEditNicknameTextField(
	nickname: String,
	onNicknameChanged: (String) -> Unit
) {
	var nicknameText by remember { mutableStateOf(nickname) }
	Column {
		LyfeTextField(
			singleLine = true,
			text = nicknameText,
			textFieldType = if (nicknameText.isEmpty()) {
				LyfeTextFieldType.TC_GREY200_BG_TRANSPARENT_SC_GREY200
			} else {
				LyfeTextFieldType.TC_DEFAULT_BG_TRANSPARENT_SC_DEFAULT
			},
			onTextClear = {
				nicknameText = ""
				onNicknameChanged("")
			},
			onTextChange = {
				nicknameText = it
				onNicknameChanged(nicknameText)
			}
		)
	}
}

@Composable
private fun ProfileEditNicknameConditionTextArea(
	viewModel: ProfileEditViewModel
) {
	val nicknameValidationUiState by viewModel.nicknameValidationUiState.collectAsStateWithLifecycle()
	val validTextWithNum = nicknameValidationUiState.checkTextWithNum()
	val validSpecialLetter = nicknameValidationUiState.checkNotSpecialLetter()
	val validLength = nicknameValidationUiState.checkNotExceedMaxLength()

	Column(
		modifier = Modifier.fillMaxWidth(),
		verticalArrangement = Arrangement.spacedBy(4.dp)
	) {
		NicknameConditionText(
			text = if (validTextWithNum) {
				stringResource(id = R.string.nickname_comb_correct_text)
			} else {
				stringResource(id = R.string.nickname_comb_incorrect_text)
			},
			isValid = validTextWithNum
		)

		NicknameConditionText(
			text = if (validSpecialLetter) {
				stringResource(id = R.string.nickname_special_letter_correct_text)
			} else {
				stringResource(id = R.string.nickname_special_letter_incorrect_text)
			},
			isValid = validSpecialLetter
		)

		NicknameConditionText(
			text = if (validLength) {
				stringResource(R.string.nickname_length_correct_text)
			} else {
				stringResource(R.string.nickname_length_incorrect_text)
			},
			isValid = validLength
		)
	}
}

@Composable
private fun NicknameConditionText(
	text: String,
	isValid: Boolean
) {
	Row {
		Image(
			painter = if (isValid) {
				painterResource(id = R.drawable.ic_check_green)
			} else {
				painterResource(id = R.drawable.ic_check_red)
			},
			contentDescription = "만족하면 녹색 아니면 회색"
		)

		Spacer(modifier = Modifier.width(4.dp))

		Text(
			text = text,
			color = if (isValid) {
				Green50
			} else {
				Red50
			},
			fontSize = 14.sp
		)
	}
}

@Composable
private fun ProfileEditCompleteButton(
	viewModel: ProfileEditViewModel
) {
	LyfeButton(
		modifier = Modifier
			.height(48.dp)
			.fillMaxWidth(),
		cornerSize = 10.dp,
		isClearIconShow = false,
		buttonType = if (viewModel.isValidNickname()) {
			LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT
		} else {
			LyfeButtonType.TC_GREY500_BG_GREY50_SC_TRANSPARENT
		},
		text = stringResource(id = R.string.complete),
		onClick = {
			viewModel.checkNicknameDuplicate()
		}
	)
}