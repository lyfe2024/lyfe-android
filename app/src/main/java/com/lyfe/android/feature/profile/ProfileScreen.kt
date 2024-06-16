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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.component.LyfeSnackBarIconType
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.model.TabItem
import com.lyfe.android.core.common.ui.theme.Body1
import com.lyfe.android.core.common.ui.theme.Button1
import com.lyfe.android.core.common.ui.theme.Caption2
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.theme.Grey300
import com.lyfe.android.core.common.ui.theme.Grey500
import com.lyfe.android.core.common.ui.theme.Grey900
import com.lyfe.android.core.common.ui.theme.H4
import com.lyfe.android.core.common.ui.theme.H5
import com.lyfe.android.core.common.ui.theme.Main500
import com.lyfe.android.core.common.ui.theme.Title1
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.model.User
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.feature.profile.image.ProfileImageFeedScreen
import com.lyfe.android.feature.profile.text.ProfileTextFeedScreen

@Composable
fun ProfileScreen(
	viewModel: ProfileViewModel = hiltViewModel(),
	navigator: LyfeNavigator,
	onShowSnackBar: (LyfeSnackBarIconType, String) -> Unit
) {
	val uiState = viewModel.uiState.collectAsStateWithLifecycle()

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
			style = Button1,
			onClick = { navigator.navigate(LyfeScreens.Setting.route) }
		)

		Spacer(modifier = Modifier.height(16.dp))

		ProfileContentArea(
			uiState = uiState.value,
			onMoveToLogin = {
				navigator.navigate(route = LyfeScreens.Login.route)
			},
			onMoveToFeed = {
				navigator.navigate(LyfeScreens.FeedDetail.route)
			},
			onMoveToPost = {
				navigator.navigate(LyfeScreens.CreatePhotoPost.route)
			},
			onMoveToProfileEdit = {
				navigator.navigate(LyfeScreens.ProfileEdit.route)
			},
			onError = {
				onShowSnackBar(LyfeSnackBarIconType.ERROR, it ?: "에러메세지가 존재하지 않습니다.")
			}
		)
	}
}

@Composable
private fun ProfileContentArea(
	uiState: ProfileUiState,
	onMoveToLogin: () -> Unit,
	onMoveToFeed: () -> Unit,
	onMoveToPost: () -> Unit,
	onMoveToProfileEdit: () -> Unit,
	onError: (String?) -> Unit
) {
	when (uiState) {
		ProfileUiState.IDLE -> Unit
		is ProfileUiState.Guest -> {
			ProfileUserInfo()

			Spacer(modifier = Modifier.height(16.dp))

			ProfileUserPostTabContent(
				isGuest = true,
				onMoveToLogin = onMoveToLogin,
				onFeedClick = onMoveToFeed,
				onPostButtonClick = onMoveToPost
			)
		}
		is ProfileUiState.UserLoaded -> {
			ProfileUserInfo(
				user = uiState.user,
				onMoveToEdit = onMoveToProfileEdit
			)

			Spacer(modifier = Modifier.height(16.dp))

			ProfileUserPostTabContent(
				isGuest = false,
				onMoveToLogin = onMoveToLogin,
				onFeedClick = onMoveToFeed,
				onPostButtonClick = onMoveToPost
			)
		}

		is ProfileUiState.Error -> {
			onError(uiState.message)
		}
	}
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ProfileUserInfo(
	user: User? = null,
	onMoveToEdit: (() -> Unit)? = null
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
			model = user?.profileImage,
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
				text = user?.name ?: "게스트",
				color = Color.Black,
				style = H4
			)

			if (user?.id != null) {
				ClickableText(
					text = AnnotatedString(stringResource(id = R.string.profile_edit_title)),
					style = TextStyle(
						fontSize = 12.sp,
						color = Grey300
					),
					onClick = {
						onMoveToEdit?.invoke()
					}
				)
			}
		}
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProfileUserPostTabContent(
	isGuest: Boolean = true,
	onMoveToLogin: () -> Unit,
	onFeedClick: () -> Unit,
	onPostButtonClick: () -> Unit
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
				onMoveToLogin = onMoveToLogin
			)
		}

		ProfileUserPostPager(
			isGuest = isGuest,
			pagerState = pagerState,
			onFeedClick = onFeedClick,
			onPostButtonClick = onPostButtonClick
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
						color = getTabTextColor(pagerState.currentPage, index),
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
	onMoveToLogin: () -> Unit
) {
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = CenterHorizontally
	) {
		Spacer(modifier = Modifier.weight(1f))

		Text(
			text = stringResource(R.string.profile_screen_guest_login_title),
			textAlign = TextAlign.Center,
			color = Grey900,
			style = H5
		)

		Spacer(modifier = Modifier.height(40.dp))

		LyfeButton(
			modifier = Modifier.align(CenterHorizontally),
			isClearIconShow = false,
			text = stringResource(R.string.profile_screen_guest_login_btn_text),
			buttonType = LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT,
			verticalPadding = 12.dp,
			horizontalPadding = 24.dp,
			onClick = onMoveToLogin
		)

		Spacer(modifier = Modifier.height(8.dp))

		Text(
			modifier = Modifier
				.align(CenterHorizontally)
				.clickableSingle {
					onMoveToLogin()
				},
			text = AnnotatedString(stringResource(R.string.profile_screen_guest_login_message)),
			color = Grey500,
			style = Caption2
		)

		Spacer(modifier = Modifier.weight(2f))
	}
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ProfileUserPostPager(
	isGuest: Boolean,
	pagerState: PagerState,
	onScroll: (Boolean) -> Unit = {},
	onFeedClick: () -> Unit,
	onPostButtonClick: () -> Unit
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
					onScroll = onScroll,
					onFeedClick = onFeedClick,
					onPostButtonClick = onPostButtonClick
				)
			}
			1 -> {
				// 고민 글
				ProfileTextFeedScreen(
					onScroll = onScroll,
					onFeedClick = onFeedClick,
					onPostButtonClick = onPostButtonClick
				)
			}
		}
	}
}

private fun getTabTextColor(
	currentPage: Int,
	tabIdx: Int
) = if (isCurrentTab(currentPage, tabIdx)) {
	Main500
} else {
	Grey200
}

private fun getTabTextStyle(
	currentPage: Int,
	tabIdx: Int
) = if (isCurrentTab(currentPage, tabIdx)) {
	Title1
} else {
	Body1
}

private fun isCurrentTab(currentPage: Int, tabIdx: Int) = currentPage == tabIdx