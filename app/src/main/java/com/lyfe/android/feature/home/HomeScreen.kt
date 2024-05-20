package com.lyfe.android.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
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
import com.lyfe.android.core.common.ui.theme.Title3
import com.lyfe.android.core.common.ui.theme.think
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.feature.home.model.HomeFeedType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val DATE_TEXT_ALPHA = 0.05f
private const val DATE_TEXT_FORMAT = "MM.dd."

@Composable
fun HomeScreen(
	navigator: LyfeNavigator,
	scrollState: ScrollState = rememberScrollState(),
	onScroll: (Boolean) -> Unit
) {
	LaunchedEffect(scrollState) {
		snapshotFlow { scrollState.isScrollInProgress }
			.collect {
				onScroll(it)
			}
	}

	Box(
		modifier = Modifier
			.fillMaxSize()
			.padding(vertical = 16.dp)
	) {
		Text(
			modifier = Modifier
				.align(Alignment.TopEnd)
				.padding(top = 4.dp, end = 20.dp)
				.alpha(DATE_TEXT_ALPHA),
			text = LocalDateTime.now().format(
				DateTimeFormatter.ofPattern(DATE_TEXT_FORMAT)
			),
			style = TextStyle(
				color = Color.Black,
				fontFamily = think,
				fontWeight = FontWeight.Normal,
				fontSize = 80.sp,
				lineHeight = 72.sp
			)
		)

		Column(
			modifier = Modifier
				.fillMaxSize()
				.verticalScroll(scrollState)
				.padding(bottom = 56.dp)
		) {
			Image(
				modifier = Modifier
					.height(24.dp)
					.padding(horizontal = 20.dp),
				painter = painterResource(id = R.drawable.ic_logo),
				contentDescription = "app logo"
			)

			Spacer(modifier = Modifier.height(16.dp))

			HomeTodayTopicScreen(navigator = navigator)
		}
	}
}