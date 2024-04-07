package com.lyfe.android.feature.terms

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeSnackBarIconType
import com.lyfe.android.core.common.ui.theme.Grey100
import com.lyfe.android.core.common.ui.theme.H5
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun PersonalInfoAgreementsScreen(
	navigator: LyfeNavigator,
	viewModel: PersonalInfoTermsViewModel = hiltViewModel(),
	onShowSnackBar: (LyfeSnackBarIconType, String) -> Unit
) {
	Column(
		modifier = Modifier.fillMaxSize()
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(vertical = 16.dp, horizontal = 20.dp)
		) {
			Icon(
				modifier = Modifier
					.size(24.dp)
					.clickableSingle {
						navigator.navigateUp()
					},
				painter = painterResource(id = R.drawable.ic_arrow_back),
				contentDescription = "뒤로 가기",
				tint = Color.Black
			)

			Spacer(modifier = Modifier.width(16.dp))

			Text(
				text = stringResource(R.string.personal_info_terms),
				style = H5,
				color = Color.Black
			)
		}

		Spacer(Modifier.fillMaxWidth().height(1.dp).background(Grey100))

		when (val uiState = viewModel.uiState) {
			is TermsUiState.Success -> {
				MarkdownText(
					modifier = Modifier
						.fillMaxWidth()
						.padding(vertical = 10.dp, horizontal = 20.dp),
					markdown = uiState.content
				)
			}
			is TermsUiState.Failure -> {
				// 토스트 매세지 띄우기
				onShowSnackBar(LyfeSnackBarIconType.ERROR, uiState.errorMessage)
			}
			TermsUiState.Loading -> {
				// TODO 로딩창 띄우기
			}
		}
	}
}