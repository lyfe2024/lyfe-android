package com.lyfe.android.feature.album

import android.graphics.drawable.Drawable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.rememberGlidePreloadingData
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.RoundedCornerButton
import com.lyfe.android.core.common.ui.theme.Button1
import com.lyfe.android.core.common.ui.theme.DisabledBtnTextColor
import com.lyfe.android.core.common.ui.theme.Grey50
import com.lyfe.android.core.common.ui.theme.H5
import com.lyfe.android.core.common.ui.theme.Main500
import com.lyfe.android.core.common.ui.theme.Main500Transparency20
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.model.GalleryImage
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

const val SelectImageKey = "Select Image"

@Composable
fun SelectAlbumRouter(
	viewModel: SelectAlbumViewModel = hiltViewModel(),
	navigator: LyfeNavigator
) {
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()

	SelectAlbumScreen(
		uiState = uiState,
		navigateUp = navigator::navigateUp,
		navigateBackToCreatePhotoPostWithImage = {
			navigator.navigateBackWithResult(
				key = SelectImageKey,
				result = it,
				route = LyfeScreens.CreatePhotoPost.name
			)
		}
	)
}

@Composable
fun SelectAlbumScreen(
	uiState: SelectAlbumUiState = SelectAlbumUiState.Loading,
	navigateUp: () -> Unit,
	navigateBackToCreatePhotoPostWithImage: (String) -> Unit
) {
	Column(
		modifier = Modifier.fillMaxSize()
	) {
		SelectAlbumHeader(
			navigateUp = { navigateUp() }
		)

		Box(
			modifier = Modifier.fillMaxSize()
		) {
			when (uiState) {
				is SelectAlbumUiState.Success -> {
					SelectAlbumContent(
						images = uiState.images,
						navigateBackToCreatePhotoPostWithImage = navigateBackToCreatePhotoPostWithImage
					)
				}

				is SelectAlbumUiState.EmptyGalleryImages -> {
					EmptyGalleryImagesBox(
						modifier = Modifier.fillMaxSize()
					)
				}

				is SelectAlbumUiState.Loading -> {
					CircularProgressIndicator(
						modifier = Modifier
							.size(48.dp)
							.align(Alignment.Center),
						color = Main500
					)
				}

				is SelectAlbumUiState.Error -> {}
			}
		}
	}
}

@Composable
private fun SelectAlbumHeader(
	modifier: Modifier = Modifier,
	navigateUp: () -> Unit
) {
	Row(
		modifier = modifier
			.padding(horizontal = 20.dp, vertical = 14.dp),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Start
	) {
		Icon(
			modifier = Modifier
				.size(24.dp)
				.clickableSingle { navigateUp() },
			painter = painterResource(id = R.drawable.ic_arrow_back),
			contentDescription = "arrow_back",
			tint = Color.Black
		)

		Spacer(modifier = Modifier.width(16.dp))

		Text(
			text = stringResource(id = R.string.select_album_header_text),
			style = H5,
			color = Color.Black
		)
	}
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun SelectAlbumContent(
	modifier: Modifier = Modifier,
	lazyGridState: LazyGridState = rememberLazyGridState(),
	images: List<GalleryImage>,
	navigateBackToCreatePhotoPostWithImage: (String) -> Unit
) {
	var selectedImageIdx by remember { mutableIntStateOf(-1) }
	var selectedImageUri by remember { mutableStateOf("") }

	Box(
		modifier = modifier
	) {
		Column {
			if (selectedImageUri.isNotEmpty()) {
				GlideImage(
					modifier = Modifier
						.fillMaxWidth()
						.aspectRatio(1f),
					model = selectedImageUri,
					contentDescription = "",
					contentScale = ContentScale.Crop
				)

				Spacer(modifier = Modifier.height(6.dp))
			}

			SelectAlbumGridView(
				modifier = Modifier.fillMaxSize(),
				images = images,
				selectedImageIdx = selectedImageIdx,
				lazyGridState = lazyGridState
			) { index, image ->
				selectedImageIdx = index
				selectedImageUri = image
			}
		}

		AnimatedVisibility(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 20.dp)
				.align(Alignment.BottomCenter)
				.background(Color.Transparent)
				.zIndex(1f),
			visible = !lazyGridState.isScrollInProgress,
			enter = slideInVertically(initialOffsetY = { it }),
			exit = slideOutVertically(targetOffsetY = { it })
		) {
			SelectAlbumBottomArea(
				isClickable = selectedImageUri.isNotEmpty(),
				onClick = { navigateBackToCreatePhotoPostWithImage(selectedImageUri) }
			)
		}
	}
}

@Composable
fun SelectAlbumBottomArea(
	modifier: Modifier = Modifier,
	isClickable: Boolean = false,
	onClick: () -> Unit
) {
	Column(modifier = modifier) {
		SelectAlbumButton(
			isClickable = isClickable,
			onClick = onClick
		)

		Spacer(modifier = Modifier.height(24.dp))
	}
}

@Composable
fun SelectAlbumButton(
	isClickable: Boolean = true,
	onClick: () -> Unit
) {
	val textColor = if (isClickable) Color.White else DisabledBtnTextColor

	RoundedCornerButton(
		modifier = Modifier
			.fillMaxWidth(),
		cornerRadius = 10.dp,
		horizontalPadding = 24.dp,
		verticalPadding = 12.dp,
		isClickable = isClickable,
		isClickableColor = Main500,
		isNotClickableColor = Grey50,
		onClick = onClick
	) {
		Text(
			text = stringResource(id = R.string.select_album_button_text),
			style = Button1,
			color = textColor
		)
	}
}

@Composable
fun SelectAlbumGridView(
	modifier: Modifier = Modifier,
	images: List<GalleryImage>,
	selectedImageIdx: Int = -1,
	lazyGridState: LazyGridState,
	gridCellCount: Int = 3,
	thumbnailDimension: Int = 50,
	selectImage: (index: Int, image: String) -> Unit
) {
	val requestBuilderTransform =
		{ item: GalleryImage, requestBuilder: RequestBuilder<Drawable> ->
			requestBuilder.load(item)
		}

	val thumbnailSize = Size(thumbnailDimension.toFloat(), thumbnailDimension.toFloat())

	val preloadingData = rememberGlidePreloadingData(
		data = images,
		preloadImageSize = thumbnailSize,
		requestBuilderTransform = requestBuilderTransform
	)

	Box {
		LazyVerticalGrid(
			modifier = modifier,
			columns = GridCells.Fixed(gridCellCount),
			verticalArrangement = Arrangement.spacedBy(4.dp),
			horizontalArrangement = Arrangement.spacedBy(4.dp),
			state = lazyGridState
		) {
			items(preloadingData.size, contentType = { it }) { index ->
				val (galleryImage, preloadRequestBuilder) = preloadingData[index]

				key(galleryImage.id) {
					GalleryImageView(
						image = galleryImage,
						isSelected = selectedImageIdx == index,
						preloadRequestBuilder = preloadRequestBuilder,
						selectImage = { image -> selectImage(index, image) }
					)
				}
			}
		}

		IndicatorBar(
			modifier = Modifier
				.align(Alignment.TopEnd)
				.zIndex(1f),
			listState = lazyGridState,
			columnSize = 3
		)
	}
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun GalleryImageView(
	image: GalleryImage,
	isSelected: Boolean = false,
	preloadRequestBuilder: RequestBuilder<Drawable>,
	selectImage: (image: String) -> Unit
) {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.aspectRatio(1f)
			.background(color = Color.LightGray)
			.clickableSingle {
				selectImage(image.imageUri)
			}
	) {
		if (isSelected) {
			Box(
				modifier = Modifier
					.fillMaxSize()
					.background(Main500Transparency20)
					.border(width = 2.dp, color = Main500)
					.padding(top = 9.dp, end = 9.dp)
					.zIndex(1f)
			) {
				CircleCheckBox(
					modifier = Modifier.align(Alignment.TopEnd)
				)
			}
		}

		GlideImage(
			model = image.imageUri,
			contentDescription = "",
			contentScale = ContentScale.Crop
		) {
			it.thumbnail(preloadRequestBuilder)
				.diskCacheStrategy(DiskCacheStrategy.ALL)
		}
	}
}

@Composable
private fun CircleCheckBox(
	modifier: Modifier = Modifier
) {
	Box(
		modifier = modifier
			.background(color = Main500, shape = CircleShape)
			.padding(4.dp)
	) {
		Icon(
			painter = painterResource(id = R.drawable.ic_check_white_12),
			contentDescription = "ic_check_white_24",
			tint = Color.White
		)
	}
}

@Composable
private fun EmptyGalleryImagesBox(
	modifier: Modifier = Modifier
) {
	Box(
		modifier = modifier
	) {
		Text(
			text = "현재 갤러리에 이미지가 없습니다."
		)
	}
}