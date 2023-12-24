package com.lyfe.android.core.common.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyfe.android.core.common.ui.definition.LyfeTextFieldType
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.ui.theme.Grey200
import com.lyfe.android.ui.theme.Grey400

@Composable
fun LyfeTextField(
	modifier: Modifier = Modifier,
	singleLine: Boolean = false,
	maxLines: Int = Int.MAX_VALUE,
	verticalPadding: Dp = 12.dp,
	horizontalPadding: Dp = 12.dp,
	textFieldType: LyfeTextFieldType = LyfeTextFieldType.TC_DEFAULT_BG_WHITE_SC_TRANSPARENT,
	isActivateCloseIcon: Boolean = true,
	text: String,
	hintText: String = "",
	fontSize: TextUnit = 16.sp,
	lineHeight: TextUnit = TextUnit.Unspecified,
	onTextClear: () -> Unit = {},
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
					color = textFieldType.strokeColor,
					shape = RoundedCornerShape(size = 8.dp)
				)
				.background(color = textFieldType.bgColor, shape = RoundedCornerShape(size = 8.dp))
				.padding(horizontal = horizontalPadding, vertical = verticalPadding),
			singleLine = singleLine,
			maxLines = maxLines,
			value = text,
			textStyle = TextStyle(
				color = textFieldType.textColor,
				fontSize = fontSize,
				fontWeight = FontWeight.W600,
				lineHeight = lineHeight
			),
			onValueChange = onTextChange,
		) { innerTextField ->
			Row(
				verticalAlignment = Alignment.CenterVertically
			) {
				Box(
					modifier = Modifier.weight(1f)
				) {
					if (text.isEmpty()) {
						Text(
							text = hintText,
							style = TextStyle(
								color = Grey200,
								fontSize = fontSize,
								fontWeight = FontWeight.W600
							)
						)
					}
					innerTextField()
				}

				if (isActivateCloseIcon && textFieldType.closeIcon != null) {
					Spacer(modifier = Modifier.width(8.dp))
					Image(
						modifier = Modifier
							.clickableSingle {
								onTextClear()
							},
						painter = painterResource(id = textFieldType.closeIcon),
						contentDescription = "close_icon"
					)
				}
			}
		}
	}
}

@Preview
@Composable
fun Preview_LyfeTextFields() {
	Column(
		modifier = Modifier
			.background(Grey400)
			.padding(horizontal = 10.dp)
	) {
		LyfeTextField(
			text = "사용불가능한 닉네임",
			textFieldType = LyfeTextFieldType.TC_GREY200_BG_TRANSPARENT_SC_GREY200,
			onTextChange = {}
		)

		Spacer(modifier = Modifier.height(20.dp))

		LyfeTextField(
			text = "사용불가능한 닉네임 사용불가능한 닉네임 사용불가능한 닉네임 ",
			textFieldType = LyfeTextFieldType.TC_DEFAULT_BG_TRANSPARENT_SC_DEFAULT,
			onTextChange = {}
		)

		Spacer(modifier = Modifier.height(20.dp))

		LyfeTextField(
			text = "사용불가능한 닉네임",
			textFieldType = LyfeTextFieldType.TC_ERROR_BG_TRANSPARENT_SC_ERROR,
			onTextChange = {}
		)

		Spacer(modifier = Modifier.height(20.dp))

		LyfeTextField(
			text = "사용불가능한 닉네임",
			textFieldType = LyfeTextFieldType.TC_GREY200_BG_WHITE_SC_TRANSPARENT,
			onTextChange = {}
		)

		Spacer(modifier = Modifier.height(20.dp))

		LyfeTextField(
			text = "사용불가능한 닉네임",
			textFieldType = LyfeTextFieldType.TC_DEFAULT_BG_WHITE_SC_TRANSPARENT,
			onTextChange = {}
		)

		Spacer(modifier = Modifier.height(20.dp))

		LyfeTextField(
			text = "사용불가능한 닉네임",
			textFieldType = LyfeTextFieldType.TC_DEFAULT_BG_WHITE_SC_DEFAULT,
			onTextChange = {}
		)
	}
}

@Preview
@Composable
fun Preview_LyfeTextField_Input_Test() {
	var text by remember { mutableStateOf("") }

	LyfeTextField(
		text = text,
		textFieldType = LyfeTextFieldType.TC_DEFAULT_BG_WHITE_SC_TRANSPARENT,
		onTextClear = { text = "" }
	) {
		text = it
	}
}