package com.lyfe.android.feature.policy

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.theme.Grey100
import com.lyfe.android.core.common.ui.theme.H5
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.core.navigation.navigator.LyfeNavigatorImpl
import com.mukesh.MarkDown

@Composable
fun ServicePolicyScreen(
	navigator: LyfeNavigator = LyfeNavigatorImpl(),
	viewModel: ServicePolicyViewModel = hiltViewModel()
) {
	val servicePolicyUiState by viewModel.servicePolicyUiState.collectAsStateWithLifecycle()

	Box(
		modifier = Modifier.fillMaxSize()
			.background(Color.White)
	) {
		when (servicePolicyUiState) {
			is ServicePolicyUiState.Success -> {
				ServicePolicyContent(
					title = (servicePolicyUiState as ServicePolicyUiState.Success).title,
					content = (servicePolicyUiState as ServicePolicyUiState.Success).content,
					onNavigateUp = navigator::navigateUp
				)
			}
			is ServicePolicyUiState.Loading -> {

			}

			is ServicePolicyUiState.Failure -> {

			}
		}
	}
}

@Composable
private fun ServicePolicyContent(
	title: String,
	content: String,
	onNavigateUp: () -> Unit
) {
	Column {
		ServicePolicyContentTopBar(
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
private fun ServicePolicyContentTopBar(
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
				modifier = Modifier.size(24.dp)
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