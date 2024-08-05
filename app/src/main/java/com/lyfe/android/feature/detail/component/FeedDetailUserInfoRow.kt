package com.lyfe.android.feature.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
import com.lyfe.android.core.common.ui.theme.Body3
import com.lyfe.android.core.common.ui.theme.Button2
import com.lyfe.android.core.common.ui.theme.Caption4
import com.lyfe.android.core.common.ui.theme.Grey400
import com.lyfe.android.core.common.ui.theme.Title3
import com.lyfe.android.core.common.ui.util.clickableSingle

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FeedDetailUserInfoRow(
	modifier: Modifier = Modifier,
	cheersCnt: Int,
	commentCnt: Int,
	profileImg: String,
	userName: String,
	date: String,
	onWhiskyClick: () -> Unit = {}
) {
	Row(
		modifier = modifier
			.padding(horizontal = 20.dp, vertical = 12.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Row(
			modifier = Modifier.clickableSingle { onWhiskyClick() },
			horizontalArrangement = Arrangement.spacedBy(4.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			Icon(
				modifier = Modifier.size(24.dp),
				painter = painterResource(id = R.drawable.ic_glass_cheers),
				contentDescription = "ic_glass_cheers",
				tint = Color.Black
			)

			Text(
				text = cheersCnt.toString(),
				style = Button2,
				color = Color.Black
			)
		}

		Spacer(modifier = Modifier.width(8.dp))

		Icon(
			modifier = Modifier.size(24.dp),
			painter = painterResource(id = R.drawable.ic_comment),
			contentDescription = "ic_glass_cheers",
			tint = Color.Black
		)

		Spacer(modifier = Modifier.width(2.dp))

		Text(
			text = "댓글 $commentCnt",
			style = Body3
		)

		Spacer(modifier = Modifier.weight(1f))

		GlideImage(
			modifier = Modifier
				.size(32.dp)
				.clip(CircleShape),
			model = profileImg,
			contentDescription = "profile_img",
			contentScale = ContentScale.Crop
		)

		Spacer(modifier = Modifier.width(8.dp))

		Column {
			Text(
				text = userName,
				style = Title3,
				color = Color.Black
			)

			Text(
				text = date,
				style = Caption4,
				color = Grey400
			)
		}
	}
}

@Preview
@Composable
private fun FeedDetailUserInfoRowPreview() {
	FeedDetailUserInfoRow(
		cheersCnt = 10,
		commentCnt = 10,
		profileImg = "",
		userName = "userName",
		date = "date"
	)
}