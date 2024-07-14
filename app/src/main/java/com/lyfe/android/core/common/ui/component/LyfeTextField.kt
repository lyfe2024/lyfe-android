package com.lyfe.android.core.common.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lyfe.android.core.common.ui.definition.LyfeTextFieldType
import com.lyfe.android.core.common.ui.theme.Body2
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.theme.Grey400
import com.lyfe.android.core.common.ui.util.clickableSingle

@Composable
fun LyfeTextField(
	modifier: Modifier = Modifier,
	singleLine: Boolean = false,
	maxLines: Int = Int.MAX_VALUE,
	verticalPadding: Dp = 12.dp,
	horizontalPadding: Dp = 12.dp,
	textFieldType: LyfeTextFieldType = LyfeTextFieldType.TC_DEFAULT_BG_WHITE_SC_TRANSPARENT,
	isActivateCloseIcon: Boolean = false,
	text: String,
	textStyle: TextStyle = Body2,
	textBoxHeightDp: Dp = 0.dp,
	hintText: String = "",
	hintTextStyle: TextStyle = TextStyle.Default,
	hintTextColor: Color = Grey200,
	borderWidth: Dp = 0.dp,
	borderIdleColor: Color = Color.Unspecified,
	borderFocusedColor: Color = Color.Unspecified,
	cornerRadius: Dp = 0.dp,
	requestFocus: Boolean = false,
	onTextClear: () -> Unit = {},
	onTextChange: (String) -> Unit
) {
	val focusRequester = remember { FocusRequester() }

	LaunchedEffect(Unit) {
		if (requestFocus) {
			focusRequester.requestFocus()
		}
	}

	var borderColor by remember { mutableStateOf(Color.Unspecified) }

	Box(
		modifier = modifier
	) {
		BasicTextField(
			modifier = Modifier
				.heightIn(min = textBoxHeightDp)
				.fillMaxWidth()
				.onFocusChanged {
					borderColor = if (it.isFocused) borderFocusedColor else borderIdleColor
				}
				.border(
					width = borderWidth,
					color = borderColor,
					shape = RoundedCornerShape(size = cornerRadius)
				)
				.focusRequester(focusRequester)
				.background(
					color = textFieldType.bgColor,
					shape = RoundedCornerShape(size = cornerRadius)
				)
				.padding(horizontal = horizontalPadding, vertical = verticalPadding),

			singleLine = singleLine,
			maxLines = maxLines,
			value = text,
			textStyle = textStyle,
			onValueChange = onTextChange
		) { innerTextField ->
			Row(
				verticalAlignment = Alignment.Top,
				horizontalArrangement = Arrangement.Start
			) {
				Box(
					modifier = Modifier.weight(1f)
				) {
					if (text.isEmpty()) {
						Text(
							text = hintText,
							style = hintTextStyle,
							color = hintTextColor
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