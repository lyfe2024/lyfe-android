package com.lyfe.android.feature.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lyfe.android.R
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

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
		Spacer(modifier = Modifier.height(200.dp))
		
		Image(
			modifier = Modifier.size(100.dp),
			painter = painterResource(id = R.drawable.ic_home),
			contentDescription = "로고 들어갈 자리"
		)

		Spacer(modifier = Modifier.height(98.dp))

		when(viewModel.uiState) {
			LoginUiState.Loading -> {
				LoginButtonArea(
					navigator = navigator,
					viewModel = viewModel,
					clickable = false
				)
			}
			else -> {
				LoginButtonArea(
					navigator = navigator,
					viewModel = viewModel,
					clickable = true
				)
			}
		}

		Spacer(modifier = Modifier.weight(1f))

		ServicePolicyText()
	}
}

@Composable
fun LoginButtonArea(
	navigator: LyfeNavigator,
	viewModel: LoginViewModel,
	clickable: Boolean
) {
	val context = LocalContext.current
	val modifier = Modifier.fillMaxWidth().height(42.dp)

	Column(
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		SocialLoginButton(
			modifier = modifier,
			buttonColor = Color(color = 0xFFFEE500),
			textColor = Color(color = 0xFF363636),
			snsName = "Kakao",
			onClick = {
				if (clickable) {
					viewModel.updateUiState(LoginUiState.Loading)
					KakaoLoginManager(context).startKakaoLogin {
						Log.d("KAKAO_REFRESH_TOKEN", it.refreshToken)
						Log.d("KAKAO_ACCESS_TOKEN", it.accessToken)
						viewModel.updateKakaoToken(it)
						// 로그인 성공하면 다음 화면으로 이동
						navigator.navigate(LyfeScreens.Nickname.name)
					}
				}
			}
		)
		
		Spacer(modifier = Modifier.height(16.dp))

		SocialLoginButton(
			modifier = modifier,
			buttonColor = Color.Black,
			textColor = Color.White,
			snsName = "Apple",
			onClick = { if (clickable) navigator.navigate(LyfeScreens.Nickname.name) }
		)

		Spacer(modifier = Modifier.height(16.dp))

		SocialLoginButton(
			modifier = modifier,
			buttonColor = Color.White,
			textColor = Color(color = 0xFF363636),
			snsName = "Google",
			onClick = { if (clickable) navigator.navigate(LyfeScreens.Nickname.name) }
		)
	}
}

@Composable
fun SocialLoginButton(
	modifier: Modifier,
	buttonColor: Color,
	textColor: Color,
	snsName: String,
	onClick: () -> Unit
) {
	val buttonText: String = when (snsName) {
		"Kakao" -> {
			"카카오로 3초만에 시작하기"
		}

		"Apple" -> {
			"Apple로 시작하기"
		}

		"Google" -> {
			"Google로 시작하기"
		}

		else -> {
			"이메일로 시작하기"
		}
	}

	Button(
		modifier = modifier,
		shape = RoundedCornerShape(size = 5.dp),
		colors = ButtonDefaults.buttonColors(
			containerColor = buttonColor,
			contentColor = textColor
		),
		onClick = onClick
	) {
		Row(
			modifier = Modifier.fillMaxSize(),
			verticalAlignment = Alignment.CenterVertically
		) {
			Icon(
				modifier = Modifier.size(20.dp),
				painter = painterResource(id = R.drawable.ic_alarm),
				contentDescription = "sns 로고"
			)

			Text(
				modifier = Modifier.fillMaxWidth(),
				text = buttonText,
				textAlign = TextAlign.Center,
				fontSize = 14.sp
			)
		}
	}
}

@Composable
fun ServicePolicyText() {
	val annotatedString = buildAnnotatedString {
		append("소셜로그인으로 서비스를 시작할 경우,\n")

		pushStringAnnotation(tag = "서비스 약관", annotation = "https://google.com/policy")
		withStyle(style = SpanStyle(color = Color(color = 0xFF5F5FF9), textDecoration = TextDecoration.Underline)) {
			append("서비스 약관")
		}
		pop()

		append(" 및 ")

		pushStringAnnotation(tag = "개인정보 처리방침", annotation = "https://google.com/terms")
		withStyle(style = SpanStyle(color = Color(color = 0xFF5F5FF9), textDecoration = TextDecoration.Underline)) {
			append("개인정보 처리방침")
		}
		pop()

		append("에 동의한 것으로 간주합니다.")
	}

	val uriHandler = LocalUriHandler.current

	ClickableText(
		text = annotatedString,
		style = TextStyle(textAlign = TextAlign.Center),
		onClick = { offset ->
			annotatedString.getStringAnnotations(tag = "서비스 약관", start = offset, end = offset).firstOrNull()?.let {
				Log.d("policy URL", it.item)
				uriHandler.openUri(it.item)
			}
			annotatedString.getStringAnnotations(tag = "개인정보 처리방침", start = offset, end = offset).firstOrNull()?.let {
				Log.d("terms URL", it.item)
				uriHandler.openUri(it.item)
			}
		}
	)
}