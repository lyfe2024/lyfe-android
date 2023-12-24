package com.lyfe.android.core.common.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lyfe.android.core.common.ui.definition.SNSLoginButtonType

@Composable
fun SNSLoginButton(
	modifier: Modifier = Modifier,
	verticalPadding: Dp = 14.dp,
	horizontalPadding: Dp = 20.dp,
	buttonType: SNSLoginButtonType,
	fontSize: TextUnit = 14.sp,
	lineHeight: TextUnit = 24.sp,
	fontWeight: FontWeight = FontWeight.W600,
	onClick: () -> Unit
) {
	Button(
		modifier = modifier,
		shape = RoundedCornerShape(size = 10.dp),
		colors = ButtonDefaults.buttonColors(
			containerColor = buttonType.bgColor,
			contentColor = buttonType.textColor
		),
		contentPadding = PaddingValues(
			horizontal = horizontalPadding,
			vertical = verticalPadding
		),
		onClick = onClick
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically
		) {
			Image(
				modifier = Modifier.size(20.dp),
				painter = painterResource(id = buttonType.iconRes),
				contentDescription = "sns_logo_icon"
			)

			Text(
				modifier = Modifier.fillMaxWidth(),
				text = stringResource(id = buttonType.textRes),
				textAlign = TextAlign.Center,
				style = TextStyle(
					fontSize = fontSize,
					lineHeight = lineHeight,
					fontWeight = fontWeight,
					color = buttonType.textColor
				)
			)
		}
	}
}

@Preview
@Composable
private fun Preview_SNSLoginButton() {
	Column {
		SNSLoginButton(
			modifier = Modifier.fillMaxWidth(),
			buttonType = SNSLoginButtonType.Kakao
		) {}

		Spacer(modifier = Modifier.height(20.dp))

		SNSLoginButton(
			modifier = Modifier.fillMaxWidth(),
			buttonType = SNSLoginButtonType.Apple
		) {}

		Spacer(modifier = Modifier.height(20.dp))

		SNSLoginButton(
			modifier = Modifier.fillMaxWidth(),
			buttonType = SNSLoginButtonType.Google
		) {}
	}
}