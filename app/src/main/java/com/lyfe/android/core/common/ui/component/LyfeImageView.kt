package com.lyfe.android.core.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lyfe.android.core.common.ui.theme.BlackTransparent30

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LyfeImageView(
	modifier: Modifier = Modifier,
	imageUrl: String,
	contentScale: ContentScale = ContentScale.Crop,
	contentDescription: String? = null,
	showBlurOnImage: Boolean = true
) {
	Box(
		modifier = modifier
			.fillMaxWidth()
			.aspectRatio(1f)
	) {
		GlideImage(
			modifier = Modifier.fillMaxSize(),
			model = imageUrl,
			contentScale = contentScale,
			contentDescription = contentDescription
		)

		if (showBlurOnImage) {
			Spacer(
				modifier = Modifier
					.fillMaxSize()
					.background(BlackTransparent30)
			)
		}
	}
}

@Preview
@Composable
private fun Preview_LyfeImageView() {
	LyfeImageView(
		imageUrl = "https://dimg.donga.com/wps/NEWS/IMAGE/2022/01/28/111500268.2.jpg"
	)
}