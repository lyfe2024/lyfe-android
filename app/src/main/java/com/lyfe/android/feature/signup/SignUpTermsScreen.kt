package com.lyfe.android.feature.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.component.LyfeSnackBarIconType
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.theme.Body2
import com.lyfe.android.core.common.ui.theme.Body3
import com.lyfe.android.core.common.ui.theme.Green50
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.theme.Grey50
import com.lyfe.android.core.common.ui.theme.Grey900
import com.lyfe.android.core.common.ui.theme.H3
import com.lyfe.android.core.common.ui.theme.Main500
import com.lyfe.android.core.common.ui.theme.pretenard
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun SignUpTermsPolicyScreen(
	navigator: LyfeNavigator,
	viewModel: SignUpTermsViewModel = hiltViewModel(),
	onShowSnackBar: (LyfeSnackBarIconType, String) -> Unit
) {
	val signUpTermsUiState by viewModel.signUpTermsUiState.collectAsStateWithLifecycle()
	val checkBoxUiState by viewModel.checkBoxUiState.collectAsStateWithLifecycle()

	LaunchedEffect(signUpTermsUiState) {
		if (signUpTermsUiState is SignUpTermsUiState.Success) {
			// navigator.navigate()
			viewModel.clear()
		} else if (signUpTermsUiState is SignUpTermsUiState.Failure) {
			val message = (signUpTermsUiState as SignUpTermsUiState.Failure).errorMessage
			onShowSnackBar(LyfeSnackBarIconType.ERROR, message)
		}
		// TODO Loading
	}

	Column(
		modifier = Modifier.fillMaxSize()
			.background(Color.White)
	) {
		SignUpTermsPolicyTopTitleBar(
			onNavigateUp = navigator::navigateUp
		)

		/**
		 * TODO 각 정책 이동 화면 필요
		 */
		PolicyAgreeContent(
			modifier = Modifier
				.padding(top = 40.dp, start = 20.dp, end = 20.dp)
				.weight(1f),
			servicePolicyChecked = checkBoxUiState.servicePolicy,
			userInfoPolicyChecked = checkBoxUiState.userInfoPolicy,
			toggleAllPolicyAgree = viewModel::toggleAllPolicyAgree,
			toggleServicePolicy = viewModel::toggleServicePolicy,
			toggleUserInfoPolicy = viewModel::toggleUserInfoPolicy,
			clickServiceTermsText = { navigator.navigate(LyfeScreens.ServiceTerms.name) },
			clickPersionalInfoTermsText = { navigator.navigate(LyfeScreens.PersonalInfoTermsScreen.name) }
		)

		PolicyCompleteButton(
			validation = checkBoxUiState.validation(),
			onPolicyComplete = viewModel::postUser
		)
	}
}

@Composable
private fun SignUpTermsPolicyTopTitleBar(
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
			text = stringResource(R.string.signup_terms_screen_title),
			style = H3,
			color = Color.Black
		)

		Spacer(modifier = Modifier.height(8.dp))

		Text(
			text = stringResource(R.string.signup_terms_screen_sub_title),
			style = Body3,
			color = Color.Black
		)
	}
}

@Composable
private fun PolicyAgreeContent(
	modifier: Modifier = Modifier,
	servicePolicyChecked: Boolean,
	userInfoPolicyChecked: Boolean,
	toggleAllPolicyAgree: () -> Unit,
	toggleServicePolicy: () -> Unit,
	toggleUserInfoPolicy: () -> Unit,
	clickServiceTermsText: () -> Unit,
	clickPersionalInfoTermsText: () -> Unit
) {
	Column(
		modifier = modifier
	) {
		PolicyAgreeHeaderRow(
			checked = servicePolicyChecked && userInfoPolicyChecked,
			toggleChecked = toggleAllPolicyAgree
		)

		PolicyAgreeChildRow(
			text = stringResource(id = R.string.signup_terms_screen_agree_to_service_rule),
			checked = servicePolicyChecked,
			toggleChecked = toggleServicePolicy,
			clickText = clickServiceTermsText
		)

		PolicyAgreeChildRow(
			text = stringResource(R.string.signup_terms_screen_agree_to_privacy_rule),
			checked = userInfoPolicyChecked,
			toggleChecked = toggleUserInfoPolicy,
			clickText = clickPersionalInfoTermsText
		)
	}
}

@Composable
private fun PolicyAgreeHeaderRow(
	checked: Boolean,
	toggleChecked: () -> Unit
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.background(
				color = if(checked) Grey50 else Color.White, RoundedCornerShape(8.dp)
			)
			.border(
				width = 1.dp,
				color = if (checked) Grey50 else Grey200,
				shape = RoundedCornerShape(8.dp)
			)
			.padding(vertical = 7.dp, horizontal = 10.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		PolicyCheckbox(
			modifier = Modifier
				.size(32.dp)
				.padding(6.dp),
			checked = checked,
			toggleChecked = toggleChecked
		)

		Spacer(modifier = Modifier.width(8.dp))

		Text(
			text = stringResource(R.string.signup_terms_screen_agree_all),
			style = Body2,
			color = Color.Black
		)
	}
}

@Composable
private fun PolicyAgreeChildRow(
	text: String,
	checked: Boolean,
	toggleChecked: () -> Unit,
	clickText: () -> Unit
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
			toggleChecked = toggleChecked
		)

		Spacer(modifier = Modifier.width(8.dp))

		ClickableText(
			text = AnnotatedString(text),
			style = TextStyle(
				fontSize = 14.sp,
				fontWeight = FontWeight.W400,
				color = Grey900,
				fontFamily = pretenard,
				textDecoration = TextDecoration.Underline
			),
			onClick = { _ -> clickText() }
		)
	}
}

@Composable
private fun PolicyCompleteButton(
	validation: Boolean = false,
	onPolicyComplete: () -> Unit
) {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 24.dp)
	) {
		LyfeButton(
			modifier = Modifier.fillMaxWidth(),
			cornerSize = 10.dp,
			isClearIconShow = false,
			horizontalPadding = 24.dp,
			verticalPadding = 12.dp,
			buttonType = if (validation) {
				LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT
			} else {
				LyfeButtonType.TC_GREY500_BG_GREY50_SC_TRANSPARENT
			},
			text = stringResource(id = R.string.signup_terms_screen_finish),
			onClick = onPolicyComplete
		)
	}
}

@Composable
private fun PolicyCheckbox(
	modifier: Modifier,
	checked: Boolean = false,
	toggleChecked: () -> Unit
) {
	Image(
		modifier = modifier
			.clip(RoundedCornerShape(4.dp))
			.clickableSingle {
				toggleChecked()
			},
		painter = if (checked) {
			painterResource(id = R.drawable.ic_checkbox_checked)
		} else {
			painterResource(id = R.drawable.ic_checkbox_unchecked)
		},
		contentDescription = "policy_checkbox"
	)
}