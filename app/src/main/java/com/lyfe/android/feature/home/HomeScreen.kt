package com.lyfe.android.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.theme.think
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.feature.home.model.HomeFeedType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val SCREEN_DATE_TEXT_ALPHA = 0.05f

@Composable
fun HomeScreen(
	navigator: LyfeNavigator,
	onScroll: (Boolean) -> Unit
) {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.padding(vertical = 16.dp)
	) {
		Text(
			modifier = Modifier
				.align(Alignment.TopEnd)
				.padding(top = 4.dp, end = 20.dp)
				.alpha(SCREEN_DATE_TEXT_ALPHA),
			text = LocalDateTime.now().format(
				DateTimeFormatter.ofPattern("MM.dd.")
			),
			style = TextStyle(
				color = Color.Black,
				fontSize = 80.sp,
				fontWeight = FontWeight.W400,
				lineHeight = 72.sp,
				fontFamily = think
			)
		)

		Column(
			modifier = Modifier.fillMaxSize()
		) {
			HomeTopContent()

			Spacer(modifier = Modifier.height(8.dp))

			HomeFeedArea(
				navigator = navigator,
				onScroll = onScroll
			)
		}
	}
}

@Composable
private fun HomeTopContent(
	viewModel: HomeViewModel = hiltViewModel()
) {
	Image(
		modifier = Modifier
			.height(24.dp)
			.padding(horizontal = 20.dp),
		painter = painterResource(id = R.drawable.ic_logo),
		contentDescription = "app logo"
	)

	Spacer(modifier = Modifier.height(16.dp))

	Row(
		modifier = Modifier
			.padding(horizontal = 20.dp)
			.clickableSingle {
				viewModel.changeFilterType()
			},
		verticalAlignment = Alignment.CenterVertically
	) {
		Image(
			painter = painterResource(id = R.drawable.ic_arrow_down_black),
			contentDescription = "ic_arrow_down"
		)

		Spacer(modifier = Modifier.width(10.dp))

		Text(
			text = viewModel.homeFeedType.content,
			style = TextStyle(
				color = Color.Black,
				fontSize = 14.sp,
				fontWeight = FontWeight.W700
			)
		)
	}
}

@Composable
private fun HomeFeedArea(
	viewModel: HomeViewModel = hiltViewModel(),
	navigator: LyfeNavigator,
	onScroll: (Boolean) -> Unit
) {
	// 나중에 UI State로 변경
	when (viewModel.homeFeedType) {
		HomeFeedType.TODAY_TOPIC -> {
			HomeTodayTopicScreen(
				navigator = navigator,
				onScroll = onScroll
			)
		}

		HomeFeedType.PAST_BEST -> {
			HomePastBestScreen(
				navigator = navigator,
				onScroll = onScroll
			)
		}
	}
}