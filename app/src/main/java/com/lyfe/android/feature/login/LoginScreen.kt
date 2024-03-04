package com.lyfe.android.feature.login

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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

@Composable
fun LoginScreen(
	navigator: LyfeNavigator,
	viewModel: LoginViewModel = hiltViewModel()
) {
	Column(
		modifier = Modifier
			.padding(vertical = 48.dp, horizontal = 40.dp)
			.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Image(
			modifier = Modifier.weight(1f),
			painter = painterResource(id = R.drawable.ic_logo),
			contentDescription = "앱 메인 로고"
		)

		LoginButtonArea(viewModel = viewModel)

		when (viewModel.uiState) {
			is LoginUiState.Loading -> {
				// 로딩중 표시
			}
			is LoginUiState.Failure -> {
				// 실패 토스트 메세지 표시
			}
			is LoginUiState.SignedIn -> {
				// 기존 유저 로그인 -> 홈 화면으로 이동
				navigator.navigateAndroidClearBackStack(LyfeScreens.Home.name)
			}
			is LoginUiState.Success -> {
				// 회원가입 절차 진행
				navigator.navigate(LyfeScreens.Nickname.name)
				viewModel.updateUiState(LoginUiState.IDLE)
			}
			else -> {}
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
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		SNSLoginButton(
			modifier = modifier,
			buttonType = SNSLoginButtonType.Kakao,
			onClick = kakaoLogin(context = context, viewModel = viewModel)
		)

		SNSLoginButton(
			modifier = modifier,
			buttonType = SNSLoginButtonType.Google,
			onClick = googleLogin(context = context, viewModel = viewModel)
		)

		SNSLoginButton(
			modifier = modifier,
			buttonType = SNSLoginButtonType.Apple,
			onClick = appleLogin(context = context, viewModel = viewModel)
		)
	}
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
				onTokenReceived = { oAuthToken ->
					// 소셜 로그인 접근
					viewModel.authUser(
						socialType = "KAKAO",
						identityToken = oAuthToken.accessToken,
						fcmToken = ""
					)
				},
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
			val account = GoogleLoginManager.handleSignInResult(it)
			// 소셜 로그인 접근
			viewModel.authUser(
				socialType = "GOOGLE",
				authorizationCode = account.serverAuthCode.orEmpty(),
				fcmToken = ""
			)
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