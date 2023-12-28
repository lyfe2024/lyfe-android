package com.lyfe.android.feature.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.model.TabItem
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.theme.Grey300
import com.lyfe.android.core.common.ui.theme.Grey500
import com.lyfe.android.core.common.ui.theme.Grey900
import com.lyfe.android.core.common.ui.theme.Main500
import com.lyfe.android.core.common.ui.theme.pretenard
import com.lyfe.android.core.model.UserInfo
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun ProfileScreen(
	viewModel: ProfileViewModel = hiltViewModel(),
	navigator: LyfeNavigator
) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(top = 16.dp, bottom = 0.dp)
	) {
		ClickableText(
			modifier = Modifier
				.align(Alignment.End)
				.padding(horizontal = 20.dp),
			text = AnnotatedString(stringResource(id = R.string.setting_screen_title)),
			style = TextStyle(
				fontSize = 16.sp,
				fontWeight = FontWeight.W600,
				color = Color.Black
			),
			onClick = { navigator.navigate(LyfeScreens.Setting.route) }
		)

		Spacer(modifier = Modifier.height(16.dp))

		ProfileContentArea(viewModel, navigator)
	}
}

@Composable
private fun ProfileContentArea(
	viewModel: ProfileViewModel,
	navigator: LyfeNavigator
) {
	when (viewModel.uiState) {
		is ProfileUiState.GuestSuccess,
		is ProfileUiState.UserSuccess -> {
			val isGuest = viewModel.uiState is ProfileUiState.GuestSuccess
			ProfileUserInfo(
				navigator = navigator,
				userInfo = viewModel.userInfo
			)

			Spacer(modifier = Modifier.height(16.dp))

			ProfileUserPostTabContent(
				viewModel = viewModel,
				isGuest = isGuest,
				navigator = navigator
			)
		}
		is ProfileUiState.Failure -> {
			// 로딩 실패
		}
		is ProfileUiState.Loading -> {
			// 로딩 중 표시
		}
	}
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ProfileUserInfo(
	navigator: LyfeNavigator,
	userInfo: UserInfo
) {
	Row(
		modifier = Modifier
			.height(48.dp)
			.padding(horizontal = 20.dp)
	) {
		GlideImage(
			modifier = Modifier
				.fillMaxHeight()
				.aspectRatio(1.0f)
				.clip(CircleShape),
			model = userInfo.profileImage,
			failure = placeholder(R.drawable.ic_profile_default),
			contentDescription = "profile image"
		)

		Spacer(modifier = Modifier.width(16.dp))

		Column(
			modifier = Modifier
				.fillMaxHeight(),
			verticalArrangement = Arrangement.Center
		) {
			Text(
				text = userInfo.name,
				fontSize = 20.sp,
				fontWeight = FontWeight.W700,
				lineHeight = 32.sp,
				color = Color.Black
			)

			if (userInfo.id <= 0) {
				ClickableText(
					text = AnnotatedString(stringResource(id = R.string.profile_edit_title)),
					style = TextStyle(
						fontSize = 12.sp,
						color = Grey300
					),
					onClick = { navigator.navigate(LyfeScreens.ProfileEdit.route) }
				)
			}
		}
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProfileUserPostTabContent(
	viewModel: ProfileViewModel,
	isGuest: Boolean = true,
	navigator: LyfeNavigator
) {
	val pages = listOf(
		TabItem(stringResource(R.string.profile_screen_image_feeds)),
		TabItem(stringResource(R.string.profile_screen_text_feeds))
	)
	val pagerState = rememberPagerState { pages.size }
	var tabIdx by remember { mutableIntStateOf(0) }

	LaunchedEffect(pagerState.currentPage) {
		snapshotFlow { pagerState.currentPage }
			.collect { currentPage ->
				tabIdx = currentPage
				pagerState.animateScrollToPage(currentPage)
			}
	}

	LaunchedEffect(tabIdx) {
		snapshotFlow { tabIdx }
			.collect { currentPage ->
				pagerState.animateScrollToPage(currentPage)
			}
	}

	Column(
		modifier = Modifier.fillMaxSize()
	) {
		ProfileTab(
			pages = pages,
			pagerState = pagerState,
			onTabClick = { tabIdx = it }
		)

		if (isGuest) {
			// 게스트는 로그인 유도창 띄우기
			ProfileGuestLoginView(
				viewModel = viewModel,
				navigator = navigator
			)
		}

		ProfileUserPostPager(
			viewModel = viewModel,
			isGuest = isGuest,
			pagerState = pagerState
		)
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProfileTab(
	pages: List<TabItem>,
	pagerState: PagerState,
	onTabClick: (Int) -> Unit
) {
	TabRow(
		selectedTabIndex = pagerState.currentPage,
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 20.dp),
		containerColor = Color.Transparent,
		indicator = {
			TabRowDefaults.Indicator(
				modifier = Modifier.tabIndicatorOffset(it[pagerState.currentPage]),
				color = Main500
			)
		},
		divider = {}
	) {
		pages.forEachIndexed { index, tabItem ->
			Tab(
				text = {
					Text(
						text = tabItem.text,
						style = getTabTextStyle(pagerState.currentPage, index)
					)
				},
				selected = isCurrentTab(pagerState.currentPage, index),
				onClick = { onTabClick(index) }
			)
		}
	}
}

@Composable
private fun ProfileGuestLoginView(
	viewModel: ProfileViewModel,
	navigator: LyfeNavigator
) {
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = CenterHorizontally
	) {
		Spacer(modifier = Modifier.weight(1f))

		Text(
			text = stringResource(R.string.profile_screen_guest_login_title),
			fontSize = 18.sp,
			fontWeight = FontWeight.W700,
			lineHeight = 28.sp,
			color = Grey900,
			textAlign = TextAlign.Center
		)

		Spacer(modifier = Modifier.height(40.dp))

		LyfeButton(
			modifier = Modifier.align(CenterHorizontally),
			isClearIconShow = false,
			text = stringResource(R.string.profile_screen_guest_login_btn_text),
			buttonType = LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT,
			verticalPadding = 12.dp,
			horizontalPadding = 24.dp
		) {
			// TODO 로그인 화면으로
// 			navigator.navigate(route = LyfeScreens.Login.route)
			viewModel.updateToUserMode()
		}

		Spacer(modifier = Modifier.height(8.dp))

		ClickableText(
			modifier = Modifier.align(CenterHorizontally),
			text = AnnotatedString(stringResource(R.string.profile_screen_guest_login_message)),
			style = TextStyle(
				fontSize = 14.sp,
				fontWeight = FontWeight.W400,
				color = Grey500,
				textAlign = TextAlign.Center
			)
		) {
			// TODO 로그인 화면으로
			navigator.navigate(route = LyfeScreens.Login.route)
		}

		Spacer(modifier = Modifier.weight(2f))
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProfileUserPostPager(
	viewModel: ProfileViewModel,
	isGuest: Boolean,
	pagerState: PagerState
) {
	HorizontalPager(
		modifier = Modifier.fillMaxSize(),
		state = pagerState
	) { page ->
		if (isGuest) return@HorizontalPager

		when (page) {
			0 -> {
				// 신청 사진 리스트
				ProfileImageFeedScreen(
					viewModel = viewModel
				)
			}
			1 -> {
				// 고민 글
				ProfileTextFeedScreen(
					viewModel = viewModel
				)
			}
		}
	}
}

private fun getTabTextStyle(
	currentPage: Int,
	tabIdx: Int
) = if (isCurrentTab(currentPage, tabIdx)) {
	TextStyle(
		color = Main500,
		fontSize = 18.sp,
		fontWeight = FontWeight.W700,
		fontFamily = pretenard,
		lineHeight = 28.sp
	)
} else {
	TextStyle(
		color = Grey200,
		fontSize = 18.sp,
		fontWeight = FontWeight.W500,
		fontFamily = pretenard,
		lineHeight = 28.sp
	)
}

private fun isCurrentTab(currentPage: Int, tabIdx: Int) = currentPage == tabIdx