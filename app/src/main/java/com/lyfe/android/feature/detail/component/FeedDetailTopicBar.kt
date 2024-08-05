package com.lyfe.android.feature.detail.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lyfe.android.core.common.ui.theme.H4
import com.lyfe.android.core.common.ui.theme.Main500

@Composable
fun FeedDetailTopicBar(
	modifier: Modifier = Modifier,
	topic: String
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.padding(vertical = 16.dp, horizontal = 20.dp),
		verticalAlignment = Alignment.CenterVertically
	){
		Text(
			modifier = modifier,
			text = topic,
			style = H4,
			color = Main500
		)
	}
}

@Preview
@Composable
private fun FeedDetailTopicBarPreview() {
	FeedDetailTopicBar(topic = "토픽입니다.")
}