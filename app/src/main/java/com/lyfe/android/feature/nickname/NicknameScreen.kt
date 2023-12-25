package com.lyfe.android.feature.nickname

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.component.LyfeTextField
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.definition.LyfeTextFieldType
import com.lyfe.android.feature.profileedit.NicknameInvalidState
import com.lyfe.android.feature.profileedit.ProfileEditUiState
import com.lyfe.android.feature.profileedit.ProfileEditViewModel

@Composable
fun NicknameScreen(
	viewModel: ProfileEditViewModel = hiltViewModel()
) {
	val context = LocalContext.current

	Column(
		modifier = Modifier
			.padding(top = 40.dp, bottom = 16.dp, start = 24.dp, end = 24.dp)
			.fillMaxSize()
	) {
		Text(
			text = stringResource(R.string.nickname_screen_title),
			style = TextStyle(
				fontSize = 24.sp,
				lineHeight = 36.sp,
				fontWeight = FontWeight(weight = 700),
				color = Color.Black
			)
		)

		Spacer(modifier = Modifier.height(16.dp))

		Text(
			text = stringResource(R.string.set_user_nickname),
			style = TextStyle(
				fontSize = 14.sp,
				fontWeight = FontWeight(weight = 600),
				color = Color.Black
			)
		)

		Spacer(modifier = Modifier.height(40.dp))

		NicknameEnterContent(viewModel)

		when (viewModel.uiState) {
			is ProfileEditUiState.IDLE -> { }

			is ProfileEditUiState.Success -> {
				Toast.makeText(context, stringResource(R.string.available_nickname), Toast.LENGTH_SHORT).show()
				// 중복 없는거 확인되면 바로 회원가입 완료? 아니면 추가 확인작업 있는지 물어봐야 함.
			}

			is ProfileEditUiState.Failure -> {
				// val dataLoadingFailureMsg = context.getString(R.string.data_loading_failure)
				val error = viewModel.uiState as ProfileEditUiState.Failure
				Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
			}

			is ProfileEditUiState.Loading -> {
				// 로딩하는 동안 Progressbar 보여주기
			}
		}
	}
}

@Composable
private fun NicknameEnterContent(
	viewModel: ProfileEditViewModel
) {
	val nicknameState = remember { mutableStateOf("") }

	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 24.dp, bottom = 12.dp)
	) {
		Column(
			modifier = Modifier.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			NicknameTextField(
				onNicknameChanged = { nicknameState.value = it }
			)

			Spacer(modifier = Modifier.height(10.dp))

			NicknameConditionTextArea(
				viewModel = viewModel,
				nickname = nicknameState.value
			)

			Spacer(modifier = Modifier.weight(1f))

			NicknameCompleteButton(
				viewModel = viewModel,
				nickname = nicknameState.value
			)
		}
	}
}

@Composable
private fun NicknameTextField(
	onNicknameChanged: (String) -> Unit
) {
	// 닉네임 변경하는 부분
	val nicknameState = remember { mutableStateOf("") }

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

@Composable
private fun NicknameConditionTextArea(
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
				NicknameInvalidState.EMPTY -> stringResource(R.string.nickname_comb_empty_text)
				NicknameInvalidState.INCORRECT -> stringResource(R.string.nickname_comb_incorrect_text)
				else -> stringResource(R.string.nickname_comb_correct_text)
			},
			color = nicknameCombinationState.color,
			icon = nicknameCombinationState.icon
		)

		NicknameConditionText(
			text = when (nicknameSpecialLetterState) {
				NicknameInvalidState.EMPTY,
				NicknameInvalidState.INCORRECT
				-> stringResource(R.string.nickname_special_letter_incorrect_text)

				NicknameInvalidState.CORRECT -> stringResource(R.string.nickname_special_letter_correct_text)
			},
			color = nicknameSpecialLetterState.color,
			icon = nicknameSpecialLetterState.icon
		)

		NicknameConditionText(
			text = when (nicknameLengthState) {
				NicknameInvalidState.EMPTY,
				NicknameInvalidState.INCORRECT
				-> stringResource(R.string.nickname_length_incorrect_text)

				NicknameInvalidState.CORRECT -> stringResource(R.string.nickname_length_correct_text)
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
	@DrawableRes icon: Int
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
private fun NicknameCompleteButton(
	viewModel: ProfileEditViewModel,
	nickname: String
) {
	val isNicknameEnable = viewModel.isNicknameTooLong(nickname) == NicknameInvalidState.CORRECT &&
		viewModel.isNicknameHasSpecialLetter(nickname) == NicknameInvalidState.CORRECT &&
		viewModel.isNicknameCombinationWrong(nickname) == NicknameInvalidState.CORRECT

	LyfeButton(
		modifier = Modifier
			.fillMaxWidth()
			.height(48.dp),
		cornerSize = 10.dp,
		isClearIconShow = false,
		buttonType = if (isNicknameEnable) {
			LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT
		} else {
			LyfeButtonType.TC_GREY500_BG_GREY50_SC_TRANSPARENT
		},
		text = stringResource(id = R.string.next),
		onClick = {
			// 중복 닉네임 체크 API 추후 연결
			if (isNicknameEnable) {
				viewModel.checkNicknameDuplicate(nickname = nickname)
			}
		}
	)
}