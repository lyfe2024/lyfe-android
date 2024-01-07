package com.lyfe.android.feature.policy

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.theme.Grey900
import com.lyfe.android.core.common.ui.theme.Main500
import com.lyfe.android.core.common.ui.theme.pretenard

@Composable
fun PolicyScreen() {
	Box(modifier = Modifier.fillMaxSize()) {
		var showWebView by remember { mutableStateOf(false) }
		var url by remember { mutableStateOf("https://www.naver.com") }

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

			PolicyAgreeContent {
				showWebView = true
				url = it
			}
		}

		if (showWebView) {
			PolicyWebView(url)
		}

		BackHandler(enabled = showWebView) {
			showWebView = false
		}
	}
}

@Composable
private fun PolicyAgreeContent(
	onLinkClicked: (String) -> Unit
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
			text = stringResource(R.string.policy_screen_agree_to_service_rule),
			checked = secondChecked,
			onCheckedChange = {
				secondChecked = !secondChecked
				allChecked = firstChecked && secondChecked
			},
			onLinkClicked = { onLinkClicked("https://www.naver.com") }
		)

		PolicyAgreeChildRow(
			text = stringResource(R.string.policy_screen_agree_to_privacy_rule),
			checked = secondChecked,
			onCheckedChange = {
				secondChecked = !secondChecked
				allChecked = firstChecked && secondChecked
			},
			onLinkClicked = { onLinkClicked("https://www.google.com") }
		)

		Spacer(modifier = Modifier.weight(1f))

		PolicyCompleteButton(allChecked)
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
		LyfeCheckbox(
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
	onLinkClicked: (Int) -> Unit
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(start = 10.dp, end = 12.dp, top = 7.dp, bottom = 7.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		LyfeCheckbox(
			modifier = Modifier
				.size(32.dp)
				.padding(6.dp),
			checked = checked,
			onCheckedChange = onCheckedChange
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
			onClick = onLinkClicked
		)
	}
}

@Composable
private fun PolicyWebView(
	url: String
) {
	val webViewState = rememberWebViewState(url)
	val webViewClient = AccompanistWebViewClient()
	val webChromeClient = AccompanistWebChromeClient()

	WebView(
		state = webViewState,
		client = webViewClient,
		chromeClient = webChromeClient,
		onCreated = { webView ->
			with(webView) {
				settings.run {
					javaScriptEnabled = false
					domStorageEnabled = true
					javaScriptCanOpenWindowsAutomatically = false
				}
			}
		}
	)
}

@Composable
private fun PolicyCompleteButton(
	allChecked: Boolean
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
		onClick = {
			// 회원가입 절차 시작 (API 연동)
		}
	)
}

@Composable
private fun LyfeCheckbox(
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