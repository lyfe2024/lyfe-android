package com.lyfe.android.feature.policy

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeButton
import com.lyfe.android.core.common.ui.definition.LyfeButtonType
import com.lyfe.android.core.navigation.LyfeScreens
import com.lyfe.android.core.navigation.navigator.LyfeNavigator

@Composable
fun LoginCompleteScreen(
	navigator: LyfeNavigator
) {
	Column(
		modifier = Modifier.fillMaxSize()
			.padding(vertical = 24.dp, horizontal = 20.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Image(
			modifier = Modifier.weight(1f),
			painter = painterResource(id = R.drawable.ic_main_logo),
			contentDescription = "로고"
		)

		LyfeButton(
			modifier = Modifier
				.fillMaxWidth()
				.height(48.dp),
			cornerSize = 10.dp,
			isClearIconShow = false,
			buttonType = LyfeButtonType.TC_WHITE_BG_MAIN500_SC_TRANSPARENT,
			text = stringResource(R.string.login_complete_start),
			onClick = {
				navigator.navigateAndroidClearBackStack(LyfeScreens.Home.name)
			}
		)
	}
}