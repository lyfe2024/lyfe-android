package com.lyfe.android

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.lyfe.android.core.common.ui.theme.Grey50
import com.lyfe.android.core.common.ui.theme.Grey800
import com.lyfe.android.core.common.ui.theme.pretenard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SPLASH_TIME = 3000L

@AndroidEntryPoint
class IntroActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		installSplashScreen().setOnExitAnimationListener { splashScreenView ->
			val animation = ObjectAnimator.ofFloat(
				splashScreenView.view,
				"alpha",
				1f,
				0f
			)
			animation.interpolator = AnticipateInterpolator()
			val animationDuration = 1000L
			animation.duration = animationDuration

			animation.doOnEnd { splashScreenView.remove() }
			animation.start()
		}
		super.onCreate(savedInstanceState)

		setContent {
			val context = LocalContext.current

			LaunchedEffect(Unit) {
				delay(SPLASH_TIME)
				this.launch(Dispatchers.Main) {
					startActivity(
						Intent(context, MainActivity::class.java).apply {
							finish()
						}
					)
				}
			}

			LyfeSplash()
		}
	}
}

@Composable
private fun LyfeSplash() {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(Grey50)
			.padding(horizontal = 20.45.dp)
	) {
		Spacer(modifier = Modifier.height(122.dp))
		Text(
			text = "나만의 고민, \n" +
				"생각이 담긴 사진을 \n" +
				"신청하고 공유해보세요.",
			style = TextStyle(
				fontSize = 28.sp,
				lineHeight = 38.sp,
				fontFamily = pretenard,
				fontWeight = FontWeight.W700,
				color = Grey800
			)
		)

		Spacer(modifier = Modifier.weight(1f))

		Row(
			modifier = Modifier.fillMaxWidth()
		) {
			Spacer(modifier = Modifier.weight(1f))

			Image(
				painter = painterResource(id = R.drawable.ic_logo),
				contentDescription = "logo"
			)
		}

		Spacer(modifier = Modifier.height(136.dp))
	}
}