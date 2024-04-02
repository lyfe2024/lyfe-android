package com.lyfe.android.feature.nickname

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.component.LyfeTextField
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.definition.LyfeTextFieldType
import com.lyfe.android.core.common.ui.theme.Body3
import com.lyfe.android.core.common.ui.theme.Green50
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.theme.H3
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun CreateNicknameScreen(
	navigator: LyfeNavigator,
	viewModel: CreateNicknameViewModel = hiltViewModel()
) {
	val createNicknameUiState by viewModel.createNicknameUiState.collectAsStateWithLifecycle()
	val nicknameValidationUiState by viewModel.nicknameValidationUiState.collectAsStateWithLifecycle()

	LaunchedEffect(createNicknameUiState) {
		if (createNicknameUiState is CreateNicknameUiState.Success) {
			navigator.navigate(LyfeScreens.Policy.name)
			viewModel.clear()
		} else if (createNicknameUiState is CreateNicknameUiState.Loading) {
			// Snackbar 필요
		}
	}

	/**
	 * TODO 구현 필요 사항
	 * 1. Loading
	 * 2. Duplicated일 떄 Snack노출
	 */

	Column(
		modifier = Modifier.fillMaxSize()
	) {
		NicknameTopTitleBar(
			onNavigateUp = navigator::navigateUp
		)

		Spacer(modifier = Modifier.height(40.dp))

		NicknameEnterContent(
			modifier = Modifier.weight(1f),
			inputNickName = nicknameValidationUiState.nickName,
			containsTextWithNum = nicknameValidationUiState.checkTextWithNum(),
			containsSpecialLetter = nicknameValidationUiState.checkNotSpecialLetter(),
			notExceedMaxLength = nicknameValidationUiState.checkNotExceedMaxLength(),
			isDuplicated = createNicknameUiState is CreateNicknameUiState.Duplicated,
			setNickName = viewModel::setNickName
		)

		NicknameBottomContent(
			checkDuplication = viewModel::checkNicknameDuplicate,
			checkValidation = nicknameValidationUiState.checkValidationSuccess()
		)
	}
}

@Composable
private fun NicknameTopTitleBar(
	onNavigateUp: () -> Unit
) {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 20.dp, vertical = 16.dp)
	) {
		Icon(
			modifier = Modifier
				.size(24.dp)
				.clickableSingle { onNavigateUp() },
			painter = painterResource(id = R.drawable.ic_arrow_back),
			contentDescription = "arrow_back",
			tint = Color.Black
		)

		Spacer(modifier = Modifier.height(16.dp))

		Text(
			text = stringResource(R.string.nickname_screen_title),
			style = H3,
			color = Color.Black
		)

		Spacer(modifier = Modifier.height(8.dp))

		Text(
			text = stringResource(R.string.nickname_screen_sub_title),
			style = Body3,
			color = Color.Black
		)
	}
}

@Composable
private fun NicknameBottomContent(
	checkDuplication: () -> Unit,
	checkValidation: Boolean = false
) {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 24.dp)
	) {
		LyfeButton(
			modifier = Modifier.fillMaxWidth(),
			text = stringResource(id = R.string.nickname_button_text),
			isClearIconShow = false,
			verticalPadding = 12.dp,
			horizontalPadding = 24.dp,
			onClick = { if (checkValidation) checkDuplication() },
			buttonType = if (checkValidation) {
				LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT
			} else {
				LyfeButtonType.TC_GREY500_BG_GREY50_SC_TRANSPARENT
			}
		)
	}
}

@Composable
private fun NicknameEnterContent(
	modifier: Modifier = Modifier,
	inputNickName: String,
	containsTextWithNum: Boolean = false,
	containsSpecialLetter: Boolean = false,
	notExceedMaxLength: Boolean = false,
	isDuplicated: Boolean = false,
	setNickName: (text: String) -> Unit
) {
	Column(
		modifier = modifier
			.fillMaxWidth()
			.padding(horizontal = 20.dp)
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			NicknameTextField(
				inputNickName = inputNickName,
				onNicknameChanged = setNickName,
				isDuplicated = isDuplicated
			)

			Spacer(modifier = Modifier.height(10.dp))

			NicknameConditionTextArea(
				containsTextWithNum = containsTextWithNum,
				containsSpecialLetter = containsSpecialLetter,
				notExceedMaxLength = notExceedMaxLength
			)
		}
	}
}

@Composable
private fun NicknameTextField(
	inputNickName: String,
	isDuplicated: Boolean = false,
	onNicknameChanged: (String) -> Unit
) {
	LyfeTextField(
		singleLine = true,
		text = inputNickName,
		hintText = stringResource(id = R.string.nickname_screen_textfield_hint),
		textFieldType = if (isDuplicated) {
			LyfeTextFieldType.TC_ERROR_BG_TRANSPARENT_SC_ERROR
		} else if (inputNickName.isEmpty()) {
			LyfeTextFieldType.TC_GREY200_BG_TRANSPARENT_SC_GREY200
		} else {
			LyfeTextFieldType.TC_DEFAULT_BG_TRANSPARENT_SC_DEFAULT
		},
		isActivateCloseIcon = inputNickName.isNotEmpty(),
		onTextClear = {
			onNicknameChanged("")
		},
		onTextChange = onNicknameChanged
	)
}

@Composable
private fun NicknameConditionTextArea(
	containsTextWithNum: Boolean = false,
	containsSpecialLetter: Boolean = false,
	notExceedMaxLength: Boolean = false
) {
	/**
	 * TODO 기획에 따라 String 값 변경해야 함.
	 */
	Column(
		modifier = Modifier.fillMaxWidth(),
		verticalArrangement = Arrangement.spacedBy(4.dp)
	) {
		NicknameConditionText(
			text = stringResource(R.string.nickname_comb_empty_text),
			checkValidation = containsTextWithNum
		)

		NicknameConditionText(
			text = stringResource(R.string.nickname_special_letter_incorrect_text),
			checkValidation = containsSpecialLetter
		)

		NicknameConditionText(
			text = stringResource(R.string.nickname_length_incorrect_text),
			checkValidation = notExceedMaxLength
		)
	}
}

@Composable
private fun NicknameConditionText(
	text: String,
	checkValidation: Boolean
) {
	val validationColor = if (checkValidation) Green50 else Grey200
	Row {
		Icon(
			modifier = Modifier.size(16.dp),
			painter = painterResource(id = R.drawable.ic_check_gray),
			contentDescription = "check_icon",
			tint = validationColor
		)

		Spacer(modifier = Modifier.width(6.dp))

		Text(
			modifier = Modifier.weight(1f),
			text = text,
			color = validationColor,
			style = Body3
		)
	}
}