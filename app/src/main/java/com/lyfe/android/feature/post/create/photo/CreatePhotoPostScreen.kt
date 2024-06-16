package com.lyfe.android.feature.post.create.photo

import android.content.Context
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.component.LyfeTextField
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.common.ui.permission.NeededPermission
import com.lyfe.android.core.common.ui.permission.PermissionAlertDialogs
import com.lyfe.android.core.common.ui.permission.PermissionsCheckScreen
import com.lyfe.android.core.common.ui.theme.Body2
import com.lyfe.android.core.common.ui.theme.BtnLightGrayColor
import com.lyfe.android.core.common.ui.theme.Button1
import com.lyfe.android.core.common.ui.theme.Caption3
import com.lyfe.android.core.common.ui.theme.Color_121219
import com.lyfe.android.core.common.ui.theme.DEFAULT
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.theme.Grey400
import com.lyfe.android.core.common.ui.theme.Grey800
import com.lyfe.android.core.common.ui.theme.H3
import com.lyfe.android.core.common.ui.theme.Title2
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.common.ui.util.noRippleClickable
import com.lyfe.android.core.common.ui.util.pxToDp
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.feature.album.SelectImageKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CreatePhotoPostRouter(
	navigator: LyfeNavigator,
	navHostController: NavHostController,
	viewModel: CreatePhotoPostViewModel = hiltViewModel()
) {

	val uiState by viewModel.uiState.collectAsStateWithLifecycle()

	LaunchedEffect(navHostController) {
		val selectedImage = navHostController.getBackStackEntry(LyfeScreens.CreatePhotoPost.name)
			.savedStateHandle.get<String>(SelectImageKey) ?: ""

		viewModel.saveSelectedImage(selectedImage)
	}

	DisposableEffect(uiState.event) {
		if (uiState.event is CreatePhotoPostUiEvent.MoveToSelectAlbum) {
			navigator.navigate(LyfeScreens.SelectAlbum.name)
		}

		onDispose {
			viewModel.setUiEventIdle()
		}
	}

	CreatePhotoPostScreen(
		uiState = uiState,
		neededPermissions = viewModel.neededPermissions,
		onTextChanged = viewModel::savePostTitle,
		checkPermissionResult = viewModel::checkPermissionResult,
		onPermissionSuccess = viewModel::addAllowedPermissions,
		onPermissionAlertDialogDismiss = viewModel::setUiEventIdle,
		navigateUp = navigator::navigateUp,
		clickUploadingBox = viewModel::checkPermission,
		registerData = viewModel::registerBoard,
		navigateToSelectAlbum = { navigator.navigate(LyfeScreens.SelectAlbum.name) }
	)
}

@Composable
fun CreatePhotoPostScreen(
	uiState: CreatePhotoPostUiState = CreatePhotoPostUiState(),
	neededPermissions: Array<String>,
	onTextChanged: (String) -> Unit,
	onPermissionSuccess: (List<NeededPermission>) -> Unit,
	checkPermissionResult: (List<NeededPermission>, List<NeededPermission>) -> Unit,
	onPermissionAlertDialogDismiss: () -> Unit,
	clickUploadingBox: () -> Unit,
	navigateToSelectAlbum: () -> Unit,
	registerData: () -> Unit,
	navigateUp: () -> Unit
) {
	CreatePhotoPostContent(
		selectedImage = uiState.selectedImage,
		postTitle = uiState.title,
		onTextChanged = onTextChanged,
		navigateUp = navigateUp,
		clickUploadingBox = clickUploadingBox,
		isBtnClickable = uiState.isAvailableToRegisterData(),
		clickPostBtn = registerData
	)

	HandleUiEvent(
		event = uiState.event,
		neededPermissions = neededPermissions,
		checkPermissionResult = checkPermissionResult,
		onPermissionSuccess = onPermissionSuccess,
		onPermissionAlertDialogDismiss = onPermissionAlertDialogDismiss,
		navigateToSelectAlbum = navigateToSelectAlbum
	)
}

@Composable
private fun CreatePhotoPostContent(
	selectedImage: String = "",
	postTitle: String,
	onTextChanged: (String) -> Unit,
	navigateUp: () -> Unit,
	isBtnClickable: Boolean = false,
	titleMaxCnt: Int = 20,
	textFieldRequestFocus: Boolean = false,
	scrollState: ScrollState = rememberScrollState(),
	keyboardHeight: Int = WindowInsets.ime.getBottom(LocalDensity.current),
	coroutineScope: CoroutineScope = rememberCoroutineScope(),
	context: Context = LocalContext.current,
	clickUploadingBox: () -> Unit,
	clickPostBtn: () -> Unit
) {

	LaunchedEffect(key1 = keyboardHeight) {
		coroutineScope.launch {
			scrollState.scrollBy(keyboardHeight.toFloat().pxToDp(context))
		}
	}

	Column(
		modifier = Modifier.fillMaxSize()
	) {
		CreatePhotoPostTopBar(
			navigateUp = navigateUp
		)

		Column(
			modifier = Modifier
				.weight(1f)
				.verticalScroll(scrollState)
		) {
			SelectPhotoBox(
				selectedImage = selectedImage,
				clickPhotoBox = clickUploadingBox
			)

			TypingPostTitleBox(
				modifier = Modifier,
				postTitle = postTitle,
				titleMaxCnt = titleMaxCnt,
				onTextChanged = onTextChanged
			)
		}

		LyfeButton(
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 24.dp, start = 20.dp, end = 20.dp),
			text = stringResource(id = R.string.create_photo_post_btn_text),
			buttonType = if (isBtnClickable) {
				LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT
			} else {
				LyfeButtonType.TC_GREY500_BG_GREY50_SC_TRANSPARENT
			},
			verticalPadding = 12.dp,
			horizontalPadding = 24.dp,
			isClearIconShow = false,
			textStyle = Button1,
			onClick = {
				Log.e("Test@@@", "버튼 클릭")
				clickPostBtn()
			}
		)
	}
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun SelectPhotoBox(
	modifier: Modifier = Modifier,
	selectedImage: String,
	clickPhotoBox: () -> Unit
) {
	Box(
		modifier = modifier
			.padding(top = 0.dp, start = 20.dp, end = 20.dp, bottom = 24.dp)
			.clickableSingle { clickPhotoBox() }
	) {
		if (selectedImage.isEmpty()) {
			UploadingPhotoBox()
		} else {
			GlideImage(
				modifier = Modifier
					.fillMaxWidth()
					.aspectRatio(1f)
					.clip(RoundedCornerShape(10.dp)),
				model = selectedImage,
				contentDescription = "uploading_image"
			) {
				it.centerCrop()
			}
		}
	}
}

@Composable
private fun HandleUiEvent(
	event: CreatePhotoPostUiEvent,
	neededPermissions: Array<String>,
	checkPermissionResult: (
		passedPermissionList: List<NeededPermission>,
		failedPermissionList: List<NeededPermission>
	) -> Unit,
	onPermissionSuccess: (permissionList: List<NeededPermission>) -> Unit,
	onPermissionAlertDialogDismiss: () -> Unit,
	navigateToSelectAlbum: () -> Unit
) {

	when (event) {
		is CreatePhotoPostUiEvent.CheckPermission -> {
			PermissionsCheckScreen(
				neededPermissions = neededPermissions
			) { passedPermissionList, failedPermissionList ->
				checkPermissionResult(passedPermissionList, failedPermissionList)
			}
		}

		is CreatePhotoPostUiEvent.ShowPermissionAlertDialog -> {
			PermissionAlertDialogs(
				failedPermissionList = event.failedPermissionList,
				permissionSuccess = onPermissionSuccess,
				onDismiss = onPermissionAlertDialogDismiss
			)
		}

		is CreatePhotoPostUiEvent.MoveToSelectAlbum -> {
			Log.e("Test@@@", "HandleUi $event")
//			navigateToSelectAlbum()
		}

		else -> {}
	}
}

@Composable
private fun CreatePhotoPostTopBar(
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
			text = stringResource(id = R.string.create_photo_post_title),
			color = Color.Black,
			style = H3
		)
	}
}

@Composable
private fun UploadingPhotoBox(
	modifier: Modifier = Modifier
) {
	Column(
		modifier = modifier
			.fillMaxWidth()
			.height(158.dp)
			.background(
				color = BtnLightGrayColor,
				shape = RoundedCornerShape(size = 10.dp)
			),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Icon(
			modifier = Modifier.size(44.dp),
			painter = painterResource(id = R.drawable.ic_plus_black),
			contentDescription = "ic_plus_black",
			tint = Color_121219
		)

		Spacer(modifier = Modifier.height(8.dp))

		Text(
			text = stringResource(id = R.string.create_photo_post_uploading_box_text),
			style = Button1,
			color = Color.Black
		)
	}
}

@Composable
private fun TypingPostTitleBox(
	modifier: Modifier = Modifier,
	postTitle: String,
	titleMaxCnt: Int,
	onTextChanged: (String) -> Unit
) {
	Column(
		modifier = modifier
			.padding(vertical = 24.dp, horizontal = 20.dp),
		verticalArrangement = Arrangement.spacedBy(8.dp)
	) {
		Text(
			modifier = Modifier,
			text = stringResource(id = R.string.create_photo_post_typing_box_title),
			style = Title2,
			color = Color.Black
		)

		LyfeTextField(
			text = postTitle,
			onTextChange = onTextChanged,
			hintText = stringResource(id = R.string.create_photo_post_typing_box_hint),
			hintTextStyle = Body2,
			hintTextColor = Grey200,
			borderIdleColor = Grey200,
			borderFocusedColor = DEFAULT,
			borderWidth = 1.dp,
			cornerRadius = 8.dp,
			verticalPadding = 12.dp,
			horizontalPadding = 12.dp,
		)

		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.End
		) {

			Text(
				text = "${postTitle.length}",
				style = Caption3,
				color = if (postTitle.isNotEmpty()) Grey800 else Grey200
			)

			Text(
				text = "/${titleMaxCnt}",
				style = Caption3,
				color = Grey400
			)
		}
	}
}