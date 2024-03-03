package com.lyfe.android.feature.policy

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.component.LyfeSnackBarIconType
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.theme.Grey900
import com.lyfe.android.core.common.ui.theme.Main500
import com.lyfe.android.core.common.ui.theme.pretenard
import com.lyfe.android.core.common.ui.util.LogUtil
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun PolicyScreen(
	navigator: LyfeNavigator,
	viewModel: PolicyViewModel = hiltViewModel(),
	onShowSnackBar: (LyfeSnackBarIconType, String) -> Unit
) {
	Column(
		modifier = Modifier
			.padding(top = 56.dp, bottom = 24.dp, start = 20.dp, end = 20.dp)
			.fillMaxSize()
	) {
		Text(
			text = stringResource(R.string.policy_screen_title),
			style = TextStyle(
				fontSize = 24.sp,
				lineHeight = 36.sp,
				fontWeight = FontWeight(weight = 700),
				color = Color.Black
			)
		)

		Spacer(modifier = Modifier.height(8.dp))

		Text(
			text = stringResource(R.string.policy_screen_sub_title),
			style = TextStyle(
				fontSize = 14.sp,
				fontWeight = FontWeight(weight = 600),
				color = Color.Black
			)
		)

		PolicyAgreeContent(navigator)
	}

	when (viewModel.uiState) {
		PolicyUiState.Success -> {
			navigator.navigate(LyfeScreens.LoginComplete.name)
		}
		is PolicyUiState.Failure -> {
			// 토스트 매세지 띄우기
			val message = (viewModel.uiState as PolicyUiState.Failure).errorMessage
			onShowSnackBar(LyfeSnackBarIconType.ERROR, message)
		}
		PolicyUiState.IDLE -> {}
		PolicyUiState.Loading -> {
			// TODO 로딩창 띄우기
		}
	}
}

@Composable
private fun PolicyAgreeContent(
	navigator: LyfeNavigator,
	viewModel: PolicyViewModel = hiltViewModel()
) {
	var allChecked by remember { mutableStateOf(false) }
	var firstChecked by remember { mutableStateOf(false) }
	var secondChecked by remember { mutableStateOf(false) }

	Column(modifier = Modifier.padding(top = 56.dp)) {
		PolicyAgreeHeaderRow(
			checked = allChecked,
			onCheckedChange = {
				allChecked = !allChecked
				firstChecked = allChecked
				secondChecked = allChecked
			}
		)

		PolicyAgreeChildRow(
			text = stringResource(id = R.string.policy_screen_agree_to_service_rule),
			checked = firstChecked,
			onCheckedChange = {
				firstChecked = !firstChecked
				allChecked = firstChecked && secondChecked
			},
			onClick = { navigator.navigate(LyfeScreens.ServiceTerms.name) }
		)

		PolicyAgreeChildRow(
			text = stringResource(R.string.policy_screen_agree_to_privacy_rule),
			checked = secondChecked,
			onCheckedChange = {
				secondChecked = !secondChecked
				allChecked = firstChecked && secondChecked
			},
			onClick = { navigator.navigate(LyfeScreens.PersonalInfoAgreementsScreen.name) }
		)

		Spacer(modifier = Modifier.weight(1f))

		PolicyCompleteButton(allChecked) {
			// 회원가입
			viewModel.postUser("nickname")
		}
	}
}

@Composable
private fun PolicyAgreeHeaderRow(
	checked: Boolean,
	onCheckedChange: (Boolean) -> Unit
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.clip(RoundedCornerShape(8.dp))
			.border(
				width = 1.dp,
				color = if (checked) Main500 else Grey200,
				shape = RoundedCornerShape(8.dp)
			)
			.padding(start = 10.dp, end = 12.dp, top = 7.dp, bottom = 7.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		PolicyCheckbox(
			modifier = Modifier
				.size(32.dp)
				.padding(6.dp),
			checked = checked,
			onCheckedChange = onCheckedChange
		)

		Spacer(modifier = Modifier.width(8.dp))

		Text(
			text = stringResource(R.string.policy_screen_agree_all),
			style = TextStyle(
				fontSize = 16.sp,
				fontWeight = FontWeight.W600,
				color = Color.Black,
				fontFamily = pretenard
			)
		)
	}
}

@Composable
private fun PolicyAgreeChildRow(
	text: String,
	checked: Boolean,
	onCheckedChange: (Boolean) -> Unit,
	onClick: () -> Unit
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(start = 10.dp, end = 12.dp, top = 7.dp, bottom = 7.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		PolicyCheckbox(
			modifier = Modifier
				.size(32.dp)
				.padding(6.dp),
			checked = checked,
			onCheckedChange = onCheckedChange
		)

		Spacer(modifier = Modifier.width(8.dp))

		Text(
			modifier = Modifier.clickableSingle { onClick() },
			text = AnnotatedString(text),
			style = TextStyle(
				fontSize = 14.sp,
				fontWeight = FontWeight.W400,
				color = Grey900,
				fontFamily = pretenard,
				textDecoration = TextDecoration.Underline
			)
		)
	}
}

@Composable
private fun PolicyCompleteButton(
	allChecked: Boolean,
	onClick: () -> Unit
) {
	LyfeButton(
		modifier = Modifier
			.fillMaxWidth()
			.height(48.dp),
		cornerSize = 10.dp,
		isClearIconShow = false,
		buttonType = if (allChecked) {
			LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT
		} else {
			LyfeButtonType.TC_GREY500_BG_GREY50_SC_TRANSPARENT
		},
		text = stringResource(id = R.string.complete),
		onClick = onClick
	)
}

@Composable
private fun PolicyCheckbox(
	modifier: Modifier,
	checked: Boolean = false,
	onCheckedChange: (Boolean) -> Unit
) {
	var isChecked by remember { mutableStateOf(checked) }

	Image(
		modifier = modifier
			.clip(RoundedCornerShape(4.dp))
			.clickable {
				isChecked = !isChecked
				onCheckedChange(isChecked)
			},
		painter = if (checked) {
			painterResource(id = R.drawable.ic_checkbox_checked)
		} else {
			painterResource(id = R.drawable.ic_checkbox_unchecked)
		},
		contentDescription = ""
	)
}