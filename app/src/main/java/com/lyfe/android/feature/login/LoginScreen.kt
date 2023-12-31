package com.lyfe.android.feature.login

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.SNSLoginButton
import com.lyfe.android.core.common.ui.definition.SNSLoginButtonType
import com.lyfe.android.core.common.ui.util.LogUtil
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.core.common.ui.theme.DEFAULT
import com.lyfe.android.core.common.ui.theme.Main500

@Composable
fun LoginScreen(
	navigator: LyfeNavigator,
	viewModel: LoginViewModel = hiltViewModel()
) {
	Column(
		modifier = Modifier
			.padding(vertical = 16.dp, horizontal = 24.dp)
			.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Image(
			modifier = Modifier.weight(1f),
			painter = painterResource(id = R.drawable.ic_main_logo),
			contentDescription = "앱 메인 로고"
		)

		LoginButtonArea(viewModel = viewModel)

		Spacer(modifier = Modifier.height(48.dp))

		ServicePolicyText()

		if (viewModel.uiState == LoginUiState.Success) {
			navigator.navigate(LyfeScreens.Nickname.name)
			viewModel.updateUiState(LoginUiState.IDLE)
		}
	}
}

@Composable
fun LoginButtonArea(
	viewModel: LoginViewModel
) {
	val context = LocalContext.current
	val modifier = Modifier.fillMaxWidth()

	Column(
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		SNSLoginButton(
			modifier = modifier,
			buttonType = SNSLoginButtonType.Kakao,
			onClick = kakaoLogin(context = context, viewModel = viewModel)
		)

		Spacer(modifier = Modifier.height(16.dp))

		SNSLoginButton(
			modifier = modifier,
			buttonType = SNSLoginButtonType.Google,
			onClick = googleLogin(context = context, viewModel = viewModel)
		)

		Spacer(modifier = Modifier.height(16.dp))

		SNSLoginButton(
			modifier = modifier,
			buttonType = SNSLoginButtonType.Apple,
			onClick = appleLogin(context = context, viewModel = viewModel)
		)
	}
}

@Composable
fun ServicePolicyText() {
	val annotatedString = buildAnnotatedString {
		append("소셜로그인으로 서비스를 시작할 경우,\n")

		pushStringAnnotation(tag = "서비스 약관", annotation = "https://google.com/policy")
		withStyle(
			style = SpanStyle(
				color = Main500,
				textDecoration = TextDecoration.Underline,
				fontWeight = FontWeight.W700
			)
		) {
			append("서비스 약관")
		}
		pop()

		append(" 및 ")

		pushStringAnnotation(
			tag = "개인정보 처리방침",
			annotation = "https://google.com/terms"
		)
		withStyle(
			style = SpanStyle(
				color = Main500,
				textDecoration = TextDecoration.Underline,
				fontWeight = FontWeight.W700
			)
		) {
			append("개인정보 처리방침")
		}
		pop()

		append("에 동의한 것으로 간주합니다.")
	}

	val uriHandler = LocalUriHandler.current

	ClickableText(
		modifier = Modifier.padding(bottom = 32.dp),
		text = annotatedString,
		style = TextStyle(
			color = DEFAULT,
			textAlign = TextAlign.Center
		),
		onClick = { offset ->
			annotatedString.getStringAnnotations(tag = "서비스 약관", start = offset, end = offset).firstOrNull()?.let {
				uriHandler.openUri(it.item)
			}
			annotatedString.getStringAnnotations(tag = "개인정보 처리방침", start = offset, end = offset).firstOrNull()?.let {
				uriHandler.openUri(it.item)
			}
		}
	)
}

private fun kakaoLogin(
	context: Context,
	viewModel: LoginViewModel
): () -> Unit {
	return {
		if (viewModel.uiState != LoginUiState.Loading) {
			viewModel.updateUiState(LoginUiState.Loading)
			KakaoLoginManager.startKakaoLogin(
				context = context,
				onTokenReceived = { viewModel.updateUiState(LoginUiState.Success) },
				onFailure = { viewModel.updateUiState(LoginUiState.Failure(it?.message ?: "에러메세지가 존재하지 않습니다.")) }
			)
		}
	}
}

@Composable
private fun googleLogin(
	context: Context,
	viewModel: LoginViewModel
): () -> Unit {
	val launcher = rememberGoogleSignInLauncher(
		onSignInComplete = {
			val idToken = GoogleLoginManager.handleSignInResult(it)
			if (idToken == null) {
				LogUtil.e("onSignInFailure", "ID Token is null")
			} else {
				Toast.makeText(context, "구글 로그인이 완료되었습니다!", Toast.LENGTH_SHORT).show()
				viewModel.updateUiState(LoginUiState.Success)
			}
		},
		onSignInFailure = {
			LogUtil.e("onSignInFailure", "Error Code is $it")
			viewModel.updateUiState(LoginUiState.Failure(it.toString()))
		},
		onError = {
			viewModel.updateUiState(LoginUiState.Failure(it.message ?: "에러메세지가 존재하지 않습니다."))
			throw it
		}
	)

	val onClick = {
		if (viewModel.uiState != LoginUiState.Loading) {
			viewModel.updateUiState(LoginUiState.Loading)
			launcher.launch(GoogleLoginManager.createSignInIntent(context))
		}
	}
	return onClick
}

private fun appleLogin(
	context: Context,
	viewModel: LoginViewModel
): () -> Unit {
	return {
		if (viewModel.uiState != LoginUiState.Loading) {
			viewModel.updateUiState(LoginUiState.Loading)
			AppleLoginManager.signIn(
				context = context,
				onResult = { LogUtil.d("Apple Login Success", "activitySignIn:onSuccess:${it.credential}") },
				onError = { exception ->
					val errorMessage = exception.message ?: "에러메세지가 존재하지 않습니다."
					LogUtil.e("Apple Login Failure", "error is: $errorMessage")
					viewModel.updateUiState(LoginUiState.Failure(errorMessage))
				}
			)
		}
	}
}

@Composable
private fun rememberGoogleSignInLauncher(
	onSignInComplete: (Task<GoogleSignInAccount>) -> Unit,
	onSignInFailure: (Int) -> Unit,
	onError: (ApiException) -> Unit
): ManagedActivityResultLauncher<Intent, ActivityResult> {
	return rememberLauncherForActivityResult(
		contract = ActivityResultContracts.StartActivityForResult(),
		onResult = { result ->
			try {
				if (result.resultCode != RESULT_OK) {
					onSignInFailure(result.resultCode)
				} else {
					val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
					onSignInComplete(task)
				}
			} catch (e: ApiException) {
				onError(e)
			}
		}
	)
}