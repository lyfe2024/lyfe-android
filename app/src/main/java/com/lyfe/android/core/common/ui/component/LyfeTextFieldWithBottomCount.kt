package com.lyfe.android.core.common.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lyfe.android.core.common.ui.definition.LyfeTextFieldType

@Composable
fun LyfeTextFieldWithBottomCount(
	modifier: Modifier = Modifier,
	singleLine: Boolean = false,
	maxLines: Int = Int.MAX_VALUE,
	verticalPadding: Dp = 12.dp,
	horizontalPadding: Dp = 12.dp,
	textFieldType: LyfeTextFieldType = LyfeTextFieldType.TC_DEFAULT_BG_WHITE_SC_TRANSPARENT,
	isActivateCloseIcon: Boolean = true,
	maxLength: Int,
	text: String,
	onTextClear: () -> Unit = {},
	onTextChange: (String) -> Unit
) {
	Column(
		modifier = modifier
	) {
		LyfeTextField(
			modifier = Modifier.fillMaxWidth(),
			verticalPadding = verticalPadding,
			horizontalPadding = horizontalPadding,
			singleLine = singleLine,
			maxLines = maxLines,
			textFieldType = textFieldType,
			isActivateCloseIcon = isActivateCloseIcon,
			text = text,
			onTextClear = onTextClear,
			onTextChange = {
				if (it.length > maxLength) return@LyfeTextField
				onTextChange(it)
			}
		)
		Spacer(modifier = Modifier.height(4.dp))
		Text(
			modifier = Modifier.align(Alignment.End),
			text = "${text.length}/$maxLength",
			fontWeight = FontWeight.W400
		)
	}
}

@Preview
@Composable
fun Preview_LyfeTextFieldWithBottomCount() {
	var text by remember { mutableStateOf("") }

	LyfeTextFieldWithBottomCount(
		modifier = Modifier.fillMaxWidth(),
		text = text,
		onTextClear = { text = "" },
		onTextChange = {
			text = it
		},
		maxLength = 300
	)
}