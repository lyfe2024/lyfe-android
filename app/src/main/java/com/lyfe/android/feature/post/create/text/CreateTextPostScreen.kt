package com.lyfe.android.feature.post.create.text

import android.content.Context
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.component.LyfeTextField
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.theme.Body2
import com.lyfe.android.core.common.ui.theme.Button1
import com.lyfe.android.core.common.ui.theme.Caption3
import com.lyfe.android.core.common.ui.theme.DEFAULT
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.theme.Grey400
import com.lyfe.android.core.common.ui.theme.Grey800
import com.lyfe.android.core.common.ui.theme.H3
import com.lyfe.android.core.common.ui.theme.Title2
import com.lyfe.android.core.common.ui.util.noRippleClickable
import com.lyfe.android.core.common.ui.util.pxToDp
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Event 처리 해야함 - 추후 기획 메세지 정해지면 진행
 */
@Composable
fun CreateTextPostRouter(
	navigator: LyfeNavigator,
	viewModel: CreateTextPostViewModel = hiltViewModel()
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()

	DisposableEffect(uiState.event) {
		when (val event = uiState.event) {
			is CreateTextPostUiEvent.CreateFail -> {}

			is CreateTextPostUiEvent.CreateSuccess -> {
				navigator.navigate("${LyfeScreens.FeedDetail.name}/${event.boardId}")
			}

			CreateTextPostUiEvent.MoveToSelectAlbum -> {}
			else -> {}
		}

		onDispose {
			viewModel.clearEvent()
		}
	}
	CreateTextPostScreen(
		uiState = uiState,
		navigateUp = navigator::navigateUp,
		onTitleChanged = viewModel::setTitle,
		onContentChanged = viewModel::setContent,
		clickPostBtn = viewModel::createTextPost
	)
}

@Composable
fun CreateTextPostScreen(
	uiState: CreateTextPostUiState = CreateTextPostUiState(),
	scrollState: ScrollState = rememberScrollState(),
	keyboardHeight: Int = WindowInsets.ime.getBottom(LocalDensity.current),
	coroutineScope: CoroutineScope = rememberCoroutineScope(),
	context: Context = LocalContext.current,
	onTitleChanged: (String) -> Unit = {},
	onContentChanged: (String) -> Unit = {},
	navigateUp: () -> Unit = {},
	clickPostBtn: () -> Unit
) {
	LaunchedEffect(keyboardHeight) {
		coroutineScope.launch {
			scrollState.scrollBy(keyboardHeight.toFloat().pxToDp(context))
		}
	}

	Column(
		modifier = Modifier
			.fillMaxSize()
			.verticalScroll(scrollState)
	) {
		CreateTextPostTopBar(
			navigateUp = navigateUp
		)

		TypingPostBox(
			title = stringResource(id = R.string.create_text_post_title_textfield_title),
			hint = stringResource(id = R.string.create_text_post_title_textfield_hint),
			text = uiState.title,
			textMaxCnt = 20,
			maxLines = 1,
			onTextChanged = onTitleChanged
		)

		Spacer(modifier = Modifier.height(16.dp))

		TypingPostBox(
			title = stringResource(id = R.string.create_text_post_content_textfield_title),
			hint = stringResource(id = R.string.create_text_post_content_textfield_hint),
			text = uiState.content,
			textMaxCnt = 500,
			textBoxHeightDp = 176.dp,
			onTextChanged = onContentChanged
		)

		LyfeButton(
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 24.dp, start = 20.dp, end = 20.dp),
			text = stringResource(id = R.string.create_photo_post_btn_text),
			buttonType = if (uiState.isPostValidation()) {
				LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT
			} else {
				LyfeButtonType.TC_GREY500_BG_GREY50_SC_TRANSPARENT
			},
			verticalPadding = 12.dp,
			horizontalPadding = 24.dp,
			isClearIconShow = false,
			textStyle = Button1,
			onClick = clickPostBtn
		)
	}
}

@Composable
private fun CreateTextPostTopBar(
	modifier: Modifier = Modifier,
	navigateUp: () -> Unit
) {
	Column(
		modifier = modifier
			.padding(vertical = 16.dp, horizontal = 20.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		Icon(
			modifier = Modifier
				.noRippleClickable { navigateUp() },
			painter = painterResource(id = R.drawable.ic_arrow_back),
			contentDescription = "post_back_btn",
			tint = Color.Black
		)

		Text(
			text = stringResource(id = R.string.create_text_post_top_bar_text),
			color = Color.Black,
			style = H3
		)
	}
}

@Composable
private fun TypingPostBox(
	modifier: Modifier = Modifier,
	title: String,
	hint: String,
	text: String,
	textMaxCnt: Int,
	maxLines: Int = Int.MAX_VALUE,
	textBoxHeightDp: Dp = 0.dp,
	onTextChanged: (String) -> Unit
) {
	Column(
		modifier = modifier
			.padding(vertical = 24.dp, horizontal = 20.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp)
	) {
		Text(
			modifier = Modifier,
			text = title,
			style = Title2,
			color = Color.Black
		)

		LyfeTextField(
			text = text,
			onTextChange = onTextChanged,
			hintText = hint,
			hintTextStyle = Body2,
			hintTextColor = Grey200,
			borderIdleColor = Grey200,
			borderFocusedColor = DEFAULT,
			borderWidth = 1.dp,
			cornerRadius = 8.dp,
			verticalPadding = 12.dp,
			horizontalPadding = 12.dp,
			maxLines = maxLines,
			textBoxHeightDp = textBoxHeightDp
		)

		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.End
		) {
			Text(
				text = "${text.length}",
				style = Caption3,
				color = if (title.isNotEmpty()) Grey800 else Grey200
			)

			Text(
				text = "/$textMaxCnt",
				style = Caption3,
				color = Grey400
			)
		}
	}
}