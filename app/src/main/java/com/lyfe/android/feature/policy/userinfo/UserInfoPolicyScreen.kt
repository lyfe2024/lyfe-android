package com.lyfe.android.feature.policy.userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.theme.Grey100
import com.lyfe.android.core.common.ui.theme.H5
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.core.navigation.navigator.LyfeNavigatorImpl
import com.lyfe.android.feature.policy.UserInfoPolicyUiState
import com.mukesh.MarkDown

@Composable
fun UserInfoPolicyScreen(
	navigator: LyfeNavigator = LyfeNavigatorImpl(),
	viewModel: UserInfoPolicyViewModel = hiltViewModel()
) {
	val userInfoPolicyUiState by viewModel.userInfoPolicyUiState.collectAsStateWithLifecycle()

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(Color.White)
	) {
		when (userInfoPolicyUiState) {
			is UserInfoPolicyUiState.Success -> {
				UserInfoPolicyContent(
					title = (userInfoPolicyUiState as UserInfoPolicyUiState.Success).title,
					content = (userInfoPolicyUiState as UserInfoPolicyUiState.Success).content,
					onNavigateUp = navigator::navigateUp
				)
			}

			is UserInfoPolicyUiState.Loading -> {

			}

			is UserInfoPolicyUiState.Failure -> {

			}
		}
	}
}

@Composable
private fun UserInfoPolicyContent(
	title: String,
	content: String,
	onNavigateUp: () -> Unit
) {
	Column {
		UserInfoPolicyContentTopBar(
			title = title,
			onNavigateUp = onNavigateUp
		)

		MarkDown(
			modifier = Modifier.padding(top = 20.dp),
			text = content
		)
	}
}

@Composable
private fun UserInfoPolicyContentTopBar(
	title: String,
	onNavigateUp: () -> Unit
) {
	Box(
		modifier = Modifier.fillMaxWidth()
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(vertical = 14.dp, horizontal = 20.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			Icon(
				modifier = Modifier
					.size(24.dp)
					.clickableSingle { onNavigateUp() },
				painter = painterResource(id = R.drawable.ic_arrow_back),
				contentDescription = "arrow_back",
				tint = Color.Black
			)

			Text(
				text = title,
				style = H5,
				color = Color.Black
			)
		}

		Spacer(
			Modifier
				.fillMaxWidth()
				.height(1.dp)
				.background(color = Grey100)
				.align(Alignment.BottomCenter)
		)
	}
}