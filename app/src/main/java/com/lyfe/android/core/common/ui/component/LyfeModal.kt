package com.lyfe.android.core.common.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyfe.android.core.common.ui.definition.LyfeButtonType

@Composable
fun LyfeModal(
	modifier: Modifier = Modifier,
	title: String,
	message: String,
	confirmBtnText: String,
	dismissBtnText: String,
	onConfirm: () -> Unit,
	onDismiss: () -> Unit
) {
	AlertDialog(
		modifier = modifier,
		onDismissRequest = onDismiss,
		title = {
			Text(
				text = title,
				style = TextStyle(
					fontSize = 18.sp,
					lineHeight = 28.sp,
					fontWeight = FontWeight.W700,
					color = Color.Black
				)
			)
		},
		text = if (message.isNotEmpty()) {
			{
				Text(
					text = message,
					style = TextStyle(
						fontSize = 16.sp,
						lineHeight = 24.sp,
						fontWeight = FontWeight.W500,
						color = Color.Black
					)
				)
			}
		} else {
			null
		},
		buttons = {
			Row(
				modifier = Modifier.padding(
					start = 16.dp,
					end = 16.dp,
					bottom = 16.dp,
					top = if (message.isEmpty()) 16.dp else 0.dp
				)
			) {
				LyfeButton(
					modifier = Modifier.weight(1f),
					text = dismissBtnText,
					onClick = onDismiss,
					buttonType = LyfeButtonType.TC_GREY500_BG_GREY50_SC_TRANSPARENT,
					isClearIconShow = false
				)

				Spacer(Modifier.width(16.dp))

				LyfeButton(
					modifier = Modifier.weight(1f),
					text = confirmBtnText,
					onClick = onConfirm,
					buttonType = LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT,
					isClearIconShow = false
				)
			}
		},
		shape = RoundedCornerShape(16.dp)
	)
}

@Preview
@Composable
private fun Preview_LyfeModal() {
	var showModal by remember { mutableStateOf(true) }

	Box(
		modifier = Modifier.background(Color.White)
	) {
		Button(
			modifier = Modifier.align(Alignment.Center),
			onClick = {
				showModal = true
			}
		) {
			Text(
				modifier = Modifier,
				text = "TEST"
			)
		}

		if (showModal) {
			LyfeModal(
				title = "로그인",
				message = "반응을 남기려면 로그인이 필요해요.",
				confirmBtnText = "로그인",
				dismissBtnText = "취소",
				onConfirm = { showModal = false },
				onDismiss = { showModal = false }
			)
		}
	}
}