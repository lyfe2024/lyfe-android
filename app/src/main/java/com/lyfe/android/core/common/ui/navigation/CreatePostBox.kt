package com.lyfe.android.core.common.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lyfe.android.core.common.ui.theme.Main500
import com.lyfe.android.core.common.ui.theme.Title3
import com.lyfe.android.core.common.ui.util.clickableSingle

@Composable
fun CreatePostBox(
	modifier: Modifier = Modifier,
	@StringRes textRes: Int,
	@DrawableRes iconRes: Int,
	click: () -> Unit
) {
	Row(
		modifier
			.background(color = Main500, shape = RoundedCornerShape(12.dp))
			.clickableSingle { click() }
			.padding(horizontal = 16.dp, vertical = 5.dp),
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Icon(
			modifier = Modifier.size(16.dp),
			painter = painterResource(id = iconRes),
			contentDescription = "icon",
			tint = Color.White
		)

		Text(
			text = stringResource(id = textRes),
			style = Title3,
			color = Color.White
		)
	}
}