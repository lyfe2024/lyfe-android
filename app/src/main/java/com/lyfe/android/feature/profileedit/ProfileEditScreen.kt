package com.lyfe.android.feature.profileedit

import android.net.Uri
import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.component.LyfeTextField
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.definition.LyfeTextFieldType
import com.lyfe.android.ui.theme.Grey200
import kotlinx.coroutines.launch

@Composable
fun ProfileEditScreen(
	viewModel: ProfileEditViewModel = hiltViewModel()
) {
	Column(
		modifier = Modifier
			.padding(top = 40.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
			.fillMaxSize()
	) {
		Text(
			text = "프로필 수정",
			style = TextStyle(
				fontSize = 24.sp,
				lineHeight = 36.sp,
				fontWeight = FontWeight(weight = 700),
				color = Color(color = 0xFF000000)
			)
		)

		Spacer(modifier = Modifier.height(21.dp))

		ProfileEditContentArea(viewModel)
	}
}

@Composable
private fun ProfileEditContentArea(
	viewModel: ProfileEditViewModel
) {
	val context = LocalContext.current
	// ViewModel uiState 에 따라서 화면 표시 여부 달라짐
	when (viewModel.uiState) {
		is ProfileEditUiState.IDLE -> {
			// 처음 화면에 보일 닉네임은 로컬 저장소에서 가져옴.
			ProfileEditContent(viewModel = viewModel, nickname = "Guest")
		}
		is ProfileEditUiState.Success -> {
			val profileData = viewModel.uiState as ProfileEditUiState.Success
			val nickname = profileData.nickname

			ProfileEditContent(viewModel = viewModel, nickname = nickname)

			Toast.makeText(context, "프로필 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show()
		}

		is ProfileEditUiState.Failure -> {
			// val dataLoadingFailureMsg = context.getString(R.string.data_loading_failure)
			val error = viewModel.uiState as ProfileEditUiState.Failure
			ProfileEditContent(viewModel = viewModel, nickname = "Guest")

			Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
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
	val nicknameState = remember { mutableStateOf(nickname) }
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 12.dp, bottom = 12.dp)
	) {
		Column(
			modifier = Modifier.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			ProfileEditThumbnailContent()

			Spacer(modifier = Modifier.height(40.dp))

			ProfileEditNicknameTextField(
				nickname = nickname,
				onNicknameChanged = { nicknameState.value = it }
			)

			Spacer(modifier = Modifier.height(8.dp))

			ProfileEditNicknameConditionTextArea(
				viewModel = viewModel,
				nickname = nicknameState.value
			)

			Spacer(modifier = Modifier.weight(1f))

			ProfileEditCompleteButton(
				viewModel = viewModel,
				nickname = nicknameState.value
			)
		}
	}
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ProfileEditThumbnailContent() {
	// 썸네일 변경하는 부분
	val imageUri = remember { mutableStateOf<Uri?>(null) }
	Box(
		modifier = Modifier.size(92.dp)
	) {
		// Gallery Launcher
		val galleryLauncher = rememberLauncherForActivityResult(
			contract = ActivityResultContracts.GetContent(),
			onResult = { imageUri.value = it }
		)
		val onClick = {
			// 클릭하면 앨범으로 이동
			galleryLauncher.launch("image/*")
		}

		GlideImage(
			model = imageUri.value,
			contentDescription = "프로필 이미지",
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.align(Center)
				.size(80.dp)
				.clip(CircleShape)
				.border(width = 1.dp, Grey200, CircleShape)
				.clickable {
					// 클릭하면 앨범으로 이동
					onClick()
				}
		)

		Image(
			painter = painterResource(id = R.drawable.ic_add_circle_fill),
			contentDescription = "프로필 변경",
			modifier = Modifier
				.size(24.dp)
				.clip(CircleShape)
				.align(BottomEnd)
				.clickable {
					// 클릭하면 앨범으로 이동
					onClick()
				}
		)
	}
}

@Composable
private fun ProfileEditNicknameTextField(
	nickname: String,
	onNicknameChanged: (String) -> Unit
) {
	val nicknameState = remember { mutableStateOf(nickname) }
	Column {
		LyfeTextField(
			singleLine = true,
			text = nicknameState.value,
			textFieldType = if (nicknameState.value == "") {
				LyfeTextFieldType.TC_GREY200_BG_TRANSPARENT_SC_GREY200
			} else {
				LyfeTextFieldType.TC_DEFAULT_BG_TRANSPARENT_SC_DEFAULT
			},
			onTextClear = {
				nicknameState.value = ""
				onNicknameChanged("")
			},
			onTextChange = {
				nicknameState.value = it
				onNicknameChanged(nicknameState.value)
			}
		)
	}
}

@Composable
private fun ProfileEditNicknameConditionTextArea(
	viewModel: ProfileEditViewModel,
	nickname: String
) {
	val nicknameLengthState = viewModel.isNicknameTooLong(nickname)
	val nicknameSpecialLetterState = viewModel.isNicknameHasSpecialLetter(nickname)
	val nicknameCombinationState = viewModel.isNicknameCombinationWrong(nickname)

	Column(
		modifier = Modifier.fillMaxWidth(),
		verticalArrangement = Arrangement.spacedBy(4.dp)
	) {
		NicknameConditionText(
			text = when (nicknameCombinationState) {
				NicknameInvalidState.EMPTY -> "한글/영문+숫자 조합으로 설정해주세요"
				NicknameInvalidState.INCORRECT -> "한글/영문+숫자 조합으로만 설정가능해요"
				else -> "한글/영문+숫자 조합"
			},
			color = nicknameCombinationState.color,
			icon = nicknameCombinationState.icon
		)

		NicknameConditionText(
			text = when (nicknameSpecialLetterState) {
				NicknameInvalidState.EMPTY,
				NicknameInvalidState.INCORRECT -> "특수문자는 사용할 수 없어요"
				NicknameInvalidState.CORRECT -> "특수문자 사용 X"
			},
			color = nicknameSpecialLetterState.color,
			icon = nicknameSpecialLetterState.icon
		)

		NicknameConditionText(
			text = when (nicknameLengthState) {
				NicknameInvalidState.EMPTY,
				NicknameInvalidState.INCORRECT -> "최대 10글자로 설정해주세요"
				NicknameInvalidState.CORRECT -> "최대 10글자"
			},
			color = nicknameLengthState.color,
			icon = nicknameLengthState.icon
		)
	}
}

@Composable
private fun NicknameConditionText(
	text: String,
	color: Color,
	icon: Int
) {
	Row {
		Image(
			painter = painterResource(id = icon),
			contentDescription = "만족하면 파랑 아니면 회색"
		)

		Spacer(modifier = Modifier.width(4.dp))

		Text(
			text = text,
			color = color,
			fontSize = 14.sp
		)
	}
}

@Composable
private fun ProfileEditCompleteButton(
	viewModel: ProfileEditViewModel,
	nickname: String
) {
	val isNicknameEnable = viewModel.isNicknameTooLong(nickname) == NicknameInvalidState.CORRECT &&
		viewModel.isNicknameHasSpecialLetter(nickname) == NicknameInvalidState.CORRECT &&
		viewModel.isNicknameCombinationWrong(nickname) == NicknameInvalidState.CORRECT

	LyfeButton(
		modifier = Modifier
			.height(48.dp)
			.fillMaxWidth(),
		cornerSize = 10.dp,
		isClearIconShow = false,
		buttonType = if (isNicknameEnable) {
			LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT
		} else {
			LyfeButtonType.TC_GREY500_BG_GREY50_SC_TRANSPARENT
		},
		text = "완료",
		onClick = {
			if (!isNicknameEnable) return@LyfeButton
			viewModel.viewModelScope.launch {
				viewModel.checkNicknameDuplicate(nickname = nickname)
			}
		}
	)
}