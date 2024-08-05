package com.lyfe.android.feature.detail.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.theme.Button3
import com.lyfe.android.core.common.ui.theme.Caption4
import com.lyfe.android.core.common.ui.theme.Grey300
import com.lyfe.android.core.common.ui.util.clickableSingle


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FeedDetailCommentUserRow(
	modifier: Modifier = Modifier,
	profileImg: String,
	userName: String,
	date: String,
	clickOption: () -> Unit = {}
) {
	Row(
		modifier = modifier
			.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically
	) {
		Box(
			modifier = Modifier
				.size(24.dp)
				.clip(CircleShape)
		) {
			GlideImage(
				modifier = Modifier.fillMaxSize(),
				model = profileImg,
				contentDescription = "profileImg",
				contentScale = ContentScale.Crop
			)
		}

		Spacer(modifier = Modifier.width(8.dp))

		Text(
			text = userName,
			style = Button3,
			color = Color.Black
		)

		Spacer(modifier = Modifier.width(8.dp))

		Text(
			text = date,
			style = Caption4,
			color = Grey300
		)

		Spacer(modifier = Modifier.weight(1f))

		Icon(
			modifier = Modifier
				.size(24.dp)
				.clickableSingle { clickOption() },
			painter = painterResource(id = R.drawable.ic_menu_more),
			contentDescription = "ic_menu_more",
			tint = Grey300
		)
	}
}

@Preview
@Composable
private fun FeedDetailCommentUserRowPreview() {
	FeedDetailCommentUserRow(
		profileImg = "",
		userName = "userName",
		date = "date"
	)
}