package com.lyfe.android.core.common.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TextField(
	modifier: Modifier = Modifier,
	singleLine: Boolean = false,
	maxLines: Int = Int.MAX_VALUE,
	placeHolder: @Composable () -> Unit,
	text: String,
	onChange: (String) -> Unit,
) {
	Box(
		modifier = modifier
	) {
		BasicTextField(
			modifier = Modifier
				.fillMaxWidth()
				.border(
					width = 1.dp, color = Color(0xFFC4C4C4), shape = RoundedCornerShape(size = 8.dp)
				)
				.padding(horizontal = 12.dp, vertical = 8.dp),
			singleLine = singleLine,
			maxLines = maxLines,
			value = text,
			onValueChange = onChange
		) {
			if (text.isEmpty()) {
				placeHolder()
			} else {
				Text(
					text = text
				)
			}
		}
	}
}