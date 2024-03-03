package com.lyfe.android.feature.terms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeSnackBarIconType
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.feature.policy.PolicyUiState
import com.lyfe.android.feature.policy.PolicyViewModel
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun ServiceTermsScreen(
	navigator: LyfeNavigator,
	viewModel: ServiceTermsViewModel = hiltViewModel(),
	onShowSnackBar: (LyfeSnackBarIconType, String) -> Unit
) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(horizontal = 20.dp)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(vertical = 16.dp)
		) {
			Image(
				modifier = Modifier
					.size(24.dp)
					.clickableSingle {
						navigator.navigateUp()
					},
				painter = painterResource(id = R.drawable.ic_arrow_back),
				contentDescription = "뒤로 가기"
			)

			Spacer(modifier = Modifier.width(12.dp))

			Text(
				text = stringResource(R.string.service_terms),
				style = TextStyle(
					fontSize = 18.sp,
					lineHeight = 28.sp,
					fontWeight = FontWeight(weight = 700),
					color = Color.Black
				)
			)
		}

		val uiState by viewModel.uiState.collectAsState()

		when (uiState) {
			is TermsUiState.Success -> {
				val state = (uiState as TermsUiState.Success)

				MarkdownText(markdown = state.content)
			}
			is TermsUiState.Failure -> {
				// 토스트 매세지 띄우기
				val state = (uiState as TermsUiState.Failure)
				onShowSnackBar(LyfeSnackBarIconType.ERROR, state.errorMessage)
			}
			TermsUiState.Loading -> {
				// TODO 로딩창 띄우기
			}
		}
	}
}