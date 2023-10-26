package com.lyfe.android.feature.profile_edit

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lyfe.android.R
import androidx.compose.runtime.remember

@Composable
fun ProfileEditScreen(
	viewModel: ProfileEditViewModel
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
			val thumbnail = profileData.thumbnail ?: "https://firebasestorage.googleapis.com/v0/b/grap-b9116.appspot.com/o/profile_default%2Fprofile_traveler.png?alt=media&token=38b417dd-836c-4107-8ae5-8432a00ae610&_gl=1*1lqvtm2*_ga*ODI2NTUwODkxLjE2ODgxMDE2Mjc.*_ga_CW55HF8NVT*MTY5Nzk2MDI2Ni40LjEuMTY5Nzk2MDMzMi41Ni4wLjA."
			val nickname = profileData.nickname

			ProfileEditContent(thumbnail, nickname)

		}

		is ProfileEditUiState.Failure -> {
//			val dataLoadingFailureMsg = context.getString(R.string.data_loading_failure)
			ProfileEditContent(null, "")
		}

		is ProfileEditUiState.Loading -> {
			// 로딩하는 동안 Progressbar 보여주기
		}
	}
}

@Composable
private fun ProfileEditContent(
	thumbnail: String?,
	nickname: String
) {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 24.dp, bottom = 12.dp),
	) {
		Column(
			modifier = Modifier.fillMaxWidth(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			ProfileEditThumbnailContent(
				thumbnail = thumbnail
			)

			Spacer(modifier = Modifier.height(40.dp))

			ProfileEditNicknameBox(
				nickname = nickname
			)
		}
	}
}

@Composable
private fun ProfileEditThumbnailContent(
	thumbnail: String?
) {
	// 썸네일 변경하는 부분
	LocalContext.current
//	val thumbnailUrl = remember { mutableStateOf(thumbnail) }
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

		AsyncImage(
			model = ImageRequest.Builder(LocalContext.current)
				.data(imageUri.value)
				.build(),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileEditNicknameBox(
	nickname: String
) {
	// 닉네임 변경하는 부분
	val nicknameState = remember { mutableStateOf(nickname) }
	val nicknameFormState = remember { mutableStateOf("") }
	var isEnableState by remember { mutableStateOf(false) }
	Column(
		modifier = Modifier.fillMaxHeight(),
		verticalArrangement = Arrangement.SpaceBetween
	) {
		Column {
			Box(
				modifier = Modifier
					.clip(RoundedCornerShape(8.dp))
					.border(width = 1.dp, color = Color(0xFF363636), shape = RoundedCornerShape(8.dp))
					.padding(horizontal = 4.dp),
				contentAlignment = Alignment.CenterStart
			) {
				Row(
					modifier = Modifier.fillMaxWidth(),
					verticalAlignment = Alignment.CenterVertically
				) {
					TextField(
						modifier = Modifier.weight(1f),
						value = nicknameState.value,
						placeholder = { Text(text = "변경할 닉네임을 입력해주세요.") },
						colors = TextFieldDefaults.textFieldColors(
							containerColor = Color.Transparent,
							focusedIndicatorColor = Color.Transparent,
							unfocusedIndicatorColor = Color.Transparent,
							disabledIndicatorColor = Color.Transparent
						),
						onValueChange = {
							val checkNicknameFormState = checkNicknameForm(it)
							nicknameState.value = it
							nicknameFormState.value = checkNicknameFormState.content
							isEnableState = checkNicknameFormState == NicknameFormState.CORRECT && it != nickname
						}
					)

					IconButton(
						modifier = Modifier
							.size(32.dp)
							.padding(6.dp),
						onClick = { nicknameState.value = "" },
					) {
						Icon(
							imageVector = Icons.Filled.Close,
							contentDescription = null
						)
					}

				}
			}

			Spacer(modifier = Modifier.height(10.dp))

			Text(
				modifier = Modifier.align(Alignment.Start),
				text = nicknameFormState.value,
				color = Color.Red,
				fontSize = 11.sp
			)

		}

		Spacer(modifier = Modifier.weight(1f))

		val context = LocalContext.current
		// 완료 버튼
		OutlinedButton(
			modifier = Modifier
				.height(48.dp)
				.fillMaxWidth()
				.background(
					color = if (isEnableState) Color(0xff202124) else Color(0xfff2f3f4),
					shape = RoundedCornerShape(24.dp)
				)
				.clip(RoundedCornerShape(10.dp)),
			onClick = {
			  	// 닉네임 중복 여부 확인 (서버)
				Toast.makeText(context, "닉네임 중복 여부 체크", Toast.LENGTH_SHORT).show()
			},
		) {
			Text(
				modifier = Modifier.wrapContentSize(),
				text = "완료",
				color = if (isEnableState) Color.White else Color(0xff8c8c8c),
				textAlign = TextAlign.Center,
				fontSize = 16.sp
			)
		}
	}
}


private fun checkNicknameForm(text: String): NicknameFormState {
	val regex = Regex("^[\\s가-힣a-zA-Z0-9]{1,10}$")
	val specialCharRegex = Regex("[,=':;><?/~`_.!@#^&*]|\\\\[|\\\\]")

	return if (text.matches(regex = regex)) {
		NicknameFormState.CORRECT
	} else if (text.length > 10) {
		NicknameFormState.NICKNAME_FORM_TOO_LONG
	} else if (text != text.replace(specialCharRegex, "")) {
		NicknameFormState.CONTAIN_SPECIAL_CHAR
	} else {
		NicknameFormState.NEED_LETTER_NUMBER_COMBINATION
	}
}

