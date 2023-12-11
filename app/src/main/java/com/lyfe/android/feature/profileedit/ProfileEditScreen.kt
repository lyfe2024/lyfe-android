@file:OptIn(ExperimentalGlideComposeApi::class)

package com.lyfe.android.feature.profileedit

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.component.LyfeTextField
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.definition.LyfeTextFieldType

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
	// ViewModel uiState 에 따라서 화면 표시 여부 달라짐
	when (viewModel.uiState) {
		is ProfileEditUiState.Success -> {
			val profileData = viewModel.uiState as ProfileEditUiState.Success
			val nickname = profileData.nickname

			ProfileEditContent(viewModel = viewModel, nickname = nickname)
		}

		is ProfileEditUiState.Failure -> {
			// val dataLoadingFailureMsg = context.getString(R.string.data_loading_failure)
			ProfileEditContent(viewModel = viewModel, nickname = "")
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
	val isNicknameEnable = remember { mutableStateOf(false) }
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 24.dp, bottom = 12.dp)
	) {
		Column(
			modifier = Modifier.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			ProfileEditThumbnailContent()

			Spacer(modifier = Modifier.height(40.dp))

			ProfileEditNicknameTextField(
				viewModel = viewModel,
				nickname = nickname,
				isNicknameEnableStateChanged = { isNicknameEnable.value = it }
			)

			Spacer(modifier = Modifier.weight(1f))

			ProfileEditCompleteButton(isNicknameEnable.value)
		}
	}
}

@Composable
private fun ProfileEditThumbnailContent() {
	// 썸네일 변경하는 부분
	// val thumbnailUrl = remember { mutableStateOf(thumbnail) }
	val imageUri = remember { mutableStateOf<Uri?>(null) }
	Box(
		modifier = Modifier.size(80.dp)
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
			modifier = Modifier
				.align(Center)
				.fillMaxSize()
				.clip(CircleShape)
				.clickable {
					// 클릭하면 앨범으로 이동
					onClick()
				}
		)

		Image(
			painter = painterResource(id = R.drawable.ic_post_add),
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
	viewModel: ProfileEditViewModel,
	nickname: String,
	isNicknameEnableStateChanged: (Boolean) -> Unit
) {
	val nicknameState = remember { mutableStateOf(nickname) }
	val nicknameFormState = remember { mutableStateOf("") }
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
				nicknameFormState.value = ""
				isNicknameEnableStateChanged(false)
			},
			onTextChange = {
				nicknameState.value = it
				// 유용한 닉네임 여부 확인
				val nicknameInvalidReasons = viewModel.getNicknameInvalidReason(it)
				isNicknameEnableStateChanged(nicknameInvalidReasons.isEmpty())
				// 닉네임 오류 내용 표시
				val message = if (nicknameInvalidReasons.isEmpty()) {
					NicknameFormState.CORRECT.message
				} else {
					nicknameInvalidReasons[0].message
				}
				nicknameFormState.value = message
			}
		)

		Spacer(modifier = Modifier.height(10.dp))

		Text(
			modifier = Modifier.align(Alignment.Start),
			text = nicknameFormState.value,
			color = Color.Red,
			fontSize = 11.sp
		)
	}
}

@Composable
private fun ProfileEditCompleteButton(
	isEnableState: Boolean
) {
	val context = LocalContext.current

	LyfeButton(
		modifier = Modifier.height(48.dp)
			.fillMaxWidth(),
		cornerSize = 10.dp,
		isClearIconShow = false,
		buttonType = if (isEnableState) {
			LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT
		} else {
			LyfeButtonType.TC_GREY500_BG_GREY50_SC_TRANSPARENT
		},
		text = "완료",
		onClick = {
			Toast.makeText(context, "현재 닉네임 가능 여부: $isEnableState", Toast.LENGTH_SHORT).show()
		}
	)
}