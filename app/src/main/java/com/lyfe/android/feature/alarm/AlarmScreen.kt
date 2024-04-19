package com.lyfe.android.feature.alarm

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyfe.android.R
import com.lyfe.android.core.model.Alarm
import com.lyfe.android.core.common.ui.theme.TempColor
import com.lyfe.android.core.model.Notification

@Composable
fun AlarmRoute(
	viewModel: AlarmViewModel = hiltViewModel()
) {
	val notificationList by viewModel.notificationList.collectAsStateWithLifecycle()
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()

	AlarmScreen(
		notificationList = notificationList,
		uiState = uiState,
		fetchNextNotificationList = viewModel::fetchNextNotifications
	)
}

@Composable
fun AlarmScreen(
	notificationList: List<Notification>,
	uiState: AlarmUiState,
	fetchNextNotificationList: () -> Unit
) {
	val context = LocalContext.current

	Column(
		modifier = Modifier
			.padding(vertical = 16.dp, horizontal = 24.dp)
			.fillMaxSize()
	) {
		AlarmTopBar(
			modifier = Modifier.fillMaxWidth(),
			readAllText = context.getString(R.string.alarm_read_all_text)
		)

		Spacer(modifier = Modifier.height(16.dp))

		Text(
			text = context.getString(R.string.alarm_screen_title),
			style = TextStyle(
				fontSize = 24.sp,
				lineHeight = 36.sp,
				fontWeight = FontWeight(weight = 700),
				color = Color.Black
			)
		)

		Spacer(modifier = Modifier.height(21.dp))

		AlarmContentArea(
			notificationList = notificationList,
			uiState = uiState,
			fetchNextNotificationList = fetchNextNotificationList
		)
	}
}

@Composable
private fun AlarmContentArea(
	notificationList: List<Notification>,
	uiState: AlarmUiState,
	fetchNextNotificationList: () -> Unit
) {
	if (notificationList.isNotEmpty()) {
		AlarmContent(
			notificationList = notificationList,
			uiState = uiState,
			fetchNextNotificationList = fetchNextNotificationList
		)
	} else {
//		val noAlarmListMsg = context.getString(R.string.no_alarm_list)
//		NoAlarmListContent(message = noAlarmListMsg)
	}
//	when (viewModel.uiState) {
//		is AlarmUiState.Success -> {
//			val notificationList = (viewModel.uiState as? AlarmUiState.Success)?.notificationList ?: emptyList()
//			Log.e("Test@@@", "notificationList: $notificationList")
//
//			if (notificationList.isNotEmpty()) {
//				AlarmContent(notificationList, context)
//			} else {
//				val noAlarmListMsg = context.getString(R.string.no_alarm_list)
//				NoAlarmListContent(message = noAlarmListMsg)
//			}
//		}

//		is AlarmUiState.Failure -> {
//			val dataLoadingFailureMsg = context.getString(R.string.data_loading_failure)
//			NoAlarmListContent(message = dataLoadingFailureMsg)
//		}
//
//		is AlarmUiState.Loading -> {
//			// 로딩하는 동안 Progressbar 보여주기
//		}
//	}
}

@Composable
private fun AlarmContent(
	notificationList: List<Notification>,
	uiState: AlarmUiState,
	fetchNextNotificationList: () -> Unit
) {
//	LazyColumn(
//		verticalArrangement = Arrangement.spacedBy(4.dp)
//	) {
//		items(
//			items = notificationList,
//			key = { it.id }
//		) { notification ->
//			AlarmBox(
//				modifier = Modifier.fillMaxWidth(),
////				typeText = context.getString(alarm.type.stringRes),
//				message = notification.content,
//				time = notification.notifiedAt,
//				typeText = "aa"
//			)
//		}
//	}
	LazyColumn(
		verticalArrangement = Arrangement.spacedBy(4.dp)
	) {
		itemsIndexed(
			items = notificationList,
		) { index, notification ->
			Log.e("Test@@@", "index: $index")
			Log.e("Test@@@", "index+10: ${index+1 >= notificationList.size}")
			Log.e("Test@@@", "uiState: $uiState")
			if ((index + 10) >= notificationList.size && uiState != AlarmUiState.Loading) {
				Log.e("Test@@@", "Screen call fetch")
				fetchNextNotificationList()
			}

			key(index) {
				AlarmBox(
					modifier = Modifier.fillMaxWidth(),
//				typeText = context.getString(alarm.type.stringRes),
					message = notification.content,
					time = notification.notifiedAt,
					typeText = "aa"
				)
			}
		}
	}
}

@Composable
private fun AlarmTopBar(
	modifier: Modifier = Modifier,
	readAllText: String
) {
	Row(
		modifier = modifier,
		horizontalArrangement = Arrangement.End
	) {
		Text(
			text = readAllText,
			style = TextStyle(
				fontSize = 16.sp,
				lineHeight = 24.sp,
				fontWeight = FontWeight(weight = 600),
				color = Color(color = 0xFF000000),
				textAlign = TextAlign.Right
			)
		)
	}
}

@Composable
private fun AlarmBox(
	modifier: Modifier = Modifier,
	typeText: String,
	message: String,
	time: String
) {
	var isPressed by remember { mutableStateOf(false) }
	val pressedColor = Color(color = 0x1A000000)
	val interactionSource = remember { MutableInteractionSource() }

	LaunchedEffect(interactionSource) {
		interactionSource.interactions.collect { interaction ->
			when (interaction) {
				is PressInteraction.Press -> isPressed = true
				else -> isPressed = false
			}
		}
	}

	Row(
		modifier
			.background(color = if (isPressed) pressedColor else Color.Unspecified)
			.clickable(
				interactionSource = interactionSource,
				indication = null,
				onClick = {}
			)
			.padding(vertical = 8.dp)
	) {
		Image(
			modifier = Modifier
				.size(24.dp)
				.wrapContentWidth(),
			painter = painterResource(id = R.drawable.ic_launcher_background),
			contentDescription = ""
		)
		Spacer(modifier = Modifier.width(8.dp))

		AlarmTextContent(
			modifier = Modifier
				.weight(1f),
			typeText = typeText,
			message = message
		)
		Text(
			modifier = Modifier
				.wrapContentWidth(),
			text = time,
			style = TextStyle(
				fontSize = 10.sp,
				lineHeight = 16.sp,
				fontWeight = FontWeight(weight = 400),
				color = TempColor.CCCCCC,
				textAlign = TextAlign.Right
			)
		)
	}
}

@Composable
private fun AlarmTextContent(
	modifier: Modifier = Modifier,
	typeText: String,
	message: String
) {
	Column(
		modifier = modifier
	) {
		Text(
			text = typeText,
			fontSize = 10.sp,
			lineHeight = 16.sp,
			fontWeight = FontWeight(weight = 400),
			color = TempColor.CCCCCC
		)
		Text(
			text = message,
			fontSize = 14.sp,
			lineHeight = 22.sp,
			fontWeight = FontWeight(weight = 500),
			color = Color(color = 0xFF000000)
		)
	}
}

@Composable
private fun NoAlarmListContent(
	message: String
) {
	Box(modifier = Modifier.fillMaxSize()) {
		Text(
			modifier = Modifier.align(Alignment.Center),
			text = message,
			style = TextStyle(
				fontSize = 18.sp,
				lineHeight = 28.sp,
				fontWeight = FontWeight(weight = 700),
				color = Color.Black,
				textAlign = TextAlign.Center
			)
		)
	}
}