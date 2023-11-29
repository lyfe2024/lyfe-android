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
import androidx.compose.ui.unit.dp
import com.lyfe.android.ui.theme.TextFieldBgColor

@Composable
fun TextField(
	modifier: Modifier = Modifier,
	singleLine: Boolean = false,
	maxLines: Int = Int.MAX_VALUE,
	placeHolder: @Composable () -> Unit,
	text: String,
	onTextChange: (String) -> Unit
) {
	Box(
		modifier = modifier
	) {
		BasicTextField(
			modifier = Modifier
				.fillMaxWidth()
				.border(
					width = 1.dp,
					color = TextFieldBgColor,
					shape = RoundedCornerShape(size = 8.dp)
				)
				.padding(horizontal = 12.dp, vertical = 8.dp),
			singleLine = singleLine,
			maxLines = maxLines,
			value = text,
			onValueChange = onTextChange
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