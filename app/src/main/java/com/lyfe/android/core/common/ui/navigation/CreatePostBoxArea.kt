package com.lyfe.android.core.common.ui.navigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.theme.ScrimColor
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.navigation.LyfeScreens

@Composable
fun CreatePostBoxArea(
	modifier: Modifier = Modifier,
	onDismiss: () -> Unit,
	onClickPhotoBox: () -> Unit,
	onClickTextBox: () -> Unit
) {
	val density = LocalDensity.current
	var postBoxHeight by remember { mutableStateOf(0.dp) }

	Box(
		modifier = Modifier.fillMaxSize()
	) {
		Spacer(
			modifier = Modifier
				.fillMaxSize()
				.background(ScrimColor)
				.clickableSingle {
					onDismiss()
				}
		)

		Column(
			modifier = modifier,
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(8.dp)
		) {
			CreatePostBox(
				modifier = Modifier
					.width(144.dp)
					.onGloballyPositioned {
						postBoxHeight = with(density) {
							it.size.height.toDp()
						}
					},
				textRes = R.string.create_board_picture,
				iconRes = R.drawable.ic_pic_fill,
				click = onClickPhotoBox
			)

			CreatePostBox(
				modifier = Modifier.width(144.dp),
				textRes = R.string.create_board,
				iconRes = R.drawable.ic_text,
				click = onClickTextBox
			)
		}
	}
}