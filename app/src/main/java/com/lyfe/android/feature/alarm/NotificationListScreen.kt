package com.lyfe.android.feature.alarm

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.model.NotificationType
import com.lyfe.android.core.common.ui.theme.Body2
import com.lyfe.android.core.common.ui.theme.Button1
import com.lyfe.android.core.common.ui.theme.Caption3
import com.lyfe.android.core.common.ui.theme.Grey100
import com.lyfe.android.core.common.ui.theme.Grey300
import com.lyfe.android.core.common.ui.theme.H3
import com.lyfe.android.core.common.ui.theme.Main500
import com.lyfe.android.core.common.ui.theme.Title1
import com.lyfe.android.core.common.ui.util.LogUtil
import com.lyfe.android.core.model.Notification

@Composable
fun NotificationListRoute(
	viewModel: NotificationListViewModel = hiltViewModel()
) {
	val notificationList by viewModel.notificationList.collectAsStateWithLifecycle()
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()

	NotificationListScreen(
		notificationList = notificationList,
		uiState = uiState,
		fetchNextNotificationList = viewModel::fetchNextNotifications,
		onNotiClick = { }
	)
}

@Composable
fun NotificationListScreen(
	notificationList: List<Notification>,
	uiState: NotificationListUiState,
	fetchNextNotificationList: () -> Unit,
	onNotiClick: (targetId: Long) -> Unit
) {
	Box(
		modifier = Modifier.fillMaxSize()
	) {
		if (uiState == NotificationListUiState.Loading) {
			// Progress Bar
			LogUtil.d("NotificationListScreen", "NotificationListUiState Loading")
		}

		Column(
			modifier = Modifier.fillMaxSize()
		) {
			NotificationListTopBar()

			NotificationListContentArea(
				notificationList = notificationList,
				uiState = uiState,
				fetchNextNotificationList = fetchNextNotificationList,
				onNotiClick = onNotiClick
			)
		}
	}
}

@Composable
private fun NotificationListTopBar(
	modifier: Modifier = Modifier
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.padding(vertical = 10.dp, horizontal = 20.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(
			text = stringResource(id = R.string.noti_list_screen_title),
			style = H3,
			color = Color.Black
		)

		Text(
			text = stringResource(id = R.string.noti_list_read_all_text),
			style = Button1,
			color = Main500
		)
	}
}

@Composable
private fun NotificationListContentArea(
	notificationList: List<Notification>,
	uiState: NotificationListUiState,
	fetchNextNotificationList: () -> Unit,
	onNotiClick: (targetId: Long) -> Unit
) {
	if (notificationList.isNotEmpty()) {
		NotificationListContent(
			notificationList = notificationList,
			uiState = uiState,
			fetchNextNotificationList = fetchNextNotificationList,
			onNotiClick = onNotiClick
		)
	} else if (uiState != NotificationListUiState.Loading) {
		NotificationListEmptyContent()
	}
}

@Composable
private fun NotificationListEmptyContent() {
	Box(modifier = Modifier.fillMaxSize()) {
		Text(
			modifier = Modifier.align(Alignment.Center),
			text = stringResource(id = R.string.noti_list_data_empty),
			style = Title1,
			color = Main500
		)
	}
}

@Composable
private fun NotificationListContent(
	modifier: Modifier = Modifier,
	notificationList: List<Notification>,
	uiState: NotificationListUiState,
	fetchNextNotificationList: () -> Unit,
	onNotiClick: (targetId: Long) -> Unit
) {
	/**
	 * Notification API가 완성되면 itemsIndexed -> items, key 값을 noti.id로 변경 필요
	 * 현재 id값이 중복으로 와서 호출하면 앱이 내려감
	 */
	Box(
		modifier = modifier.fillMaxSize()
	) {
		val threshold = 20

		LazyColumn(
			verticalArrangement = Arrangement.spacedBy(4.dp)
		) {
			itemsIndexed(
				items = notificationList
			) { index, notification ->
				if ((index + threshold) >= notificationList.size && uiState != NotificationListUiState.Loading) {
					fetchNextNotificationList()
				}

				key(index) {
					NotificationBox(
						modifier = Modifier.fillMaxWidth(),
						notiType = NotificationType.findByValue(notification.notificationType),
						notiContent = notification.content,
						notifiedAt = notification.notifiedAt,
						onNotiClick = { onNotiClick(notification.targetId) }
					)
				}
			}
		}
	}
}

@Composable
private fun NotificationBox(
	modifier: Modifier = Modifier,
	notiType: NotificationType,
	notiContent: String,
	notifiedAt: String,
	onNotiClick: () -> Unit
) {
	var isPressed by remember { mutableStateOf(false) }
	val interactionSource = remember { MutableInteractionSource() }

	LaunchedEffect(interactionSource) {
		interactionSource.interactions.collect { interaction ->
			isPressed = when (interaction) {
				is PressInteraction.Press -> true
				else -> false
			}
		}
	}

	Row(
		modifier
			.fillMaxWidth()
			.padding(vertical = 10.dp, horizontal = 20.dp)
			.background(color = if (isPressed) Grey100 else Color.Unspecified)
			.clickable(
				interactionSource = interactionSource,
				indication = null,
				onClick = onNotiClick
			)
			.padding(vertical = 8.dp)
	) {
		Icon(
			modifier = Modifier.size(24.dp),
			painter = painterResource(id = R.drawable.ic_noti_box),
			contentDescription = "noti_box",
			tint = Color.Black
		)

		Spacer(modifier = Modifier.width(8.dp))

		NotiBoxContent(
			modifier = Modifier.weight(1f),
			typeText = stringResource(id = notiType.stringRes),
			notifiedAt = notifiedAt,
			message = notiContent
		)
	}
}

@Composable
private fun NotiBoxContent(
	modifier: Modifier = Modifier,
	typeText: String,
	notifiedAt: String,
	message: String
) {
	Column(
		modifier = modifier.fillMaxWidth(),
		verticalArrangement = Arrangement.spacedBy(2.dp)
	) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(
				text = typeText,
				style = Caption3,
				color = Grey300
			)

			Text(
				text = notifiedAt,
				style = Caption3,
				color = Grey300
			)
		}

		Text(
			text = message,
			style = Body2,
			color = Color.Black
		)
	}
}