package com.lyfe.android.feature.home

import android.view.VelocityTracker
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.lyfe.android.core.common.ui.component.LyfeCardViewDesignType
import com.lyfe.android.core.common.ui.component.LyfeFeedCardView
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.model.Feed
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.min
import kotlin.math.roundToInt

// 원본 코드 참고
// https://www.jetpackcompose.app/snippets/SwipeableCards
// 이것저것 만져보면서 고친거라 저도 100% 코드를 이해한 건 아니라 주석이 많지 않습니다...
private const val MAXIMUM_CARD_RATIO = 0.85f

@Composable
fun HomeSwipeableFeeds(
	modifier: Modifier,
	feeds: List<Feed>,
	onClick: () -> Unit
) {
	var feedList by remember { mutableStateOf(feeds) }

	Box(
		modifier = modifier,
		contentAlignment = Alignment.Center
	) {
		feedList.forEachIndexed { idx, feed ->
			key(feed) {
				HomeSwipeableCard(
					order = idx,
					totalCount = feedList.size,
					feed = feed,
					onMoveToRemove = {
						// 카드 하나 지우면 list에서 해당 카드 제거
						// 카드가 1개만 남으면 그대로.
						if (feedList.size > 1) {
							val removed = feedList.last()
							feedList = listOf(removed) + (feedList - removed)
						}
					},
					onClick = onClick
				)
			}
		}
	}
}

@Composable
fun HomeSwipeableCard(
	order: Int,
	totalCount: Int,
	feed: Feed,
	onMoveToRemove: () -> Unit,
	onClick: () -> Unit = {}
) {
	val animatedScale by animateFloatAsState(
		targetValue = 1f - (totalCount - order - 1) * 0.05f,
		label = ""
	)
	val animatedXOffset by animateDpAsState(
		targetValue = ((totalCount - order - 1) * 24).dp,
		label = ""
	)
	Box(
		modifier = Modifier
			.offset { IntOffset(x = animatedXOffset.roundToPx(), y = 0) }
			.graphicsLayer {
				scaleX = animatedScale
				scaleY = animatedScale
			}
			.clickableSingle { onClick() }
			.swipeToRemove(
				isSwipeableOrder = order == totalCount - 1,
				onMoveToRemove = onMoveToRemove
			)
	) {
		LyfeFeedCardView(
			modifier = Modifier.fillMaxWidth(MAXIMUM_CARD_RATIO),
			feed = feed,
			designType = LyfeCardViewDesignType.HOME_SCREEN_CARD
		)
	}
}

fun Modifier.swipeToRemove(
	isSwipeableOrder: Boolean,
	onMoveToRemove: () -> Unit
): Modifier =
	if (!isSwipeableOrder) {
		this
	} else {
		composed {
			val offsetX = remember { Animatable(0f) }
			var clearedHurdle by remember { mutableStateOf(false) }
			pointerInput(Unit) {
				val decay = splineBasedDecay<Float>(this)
				coroutineScope {
					while (true) {
						offsetX.stop()
						val velocityTracker = VelocityTracker.obtain()
						awaitPointerEventScope {
							horizontalDrag(awaitFirstDown().id) { change ->
								val horizontalDragOffset = offsetX.value + change.positionChange().x
								launch {
									offsetX.snapTo(horizontalDragOffset)
								}
								if (abs(change.positionChange().x) > Offset.VisibilityThreshold.x) {
									change.consume()
								}
							}
						}
						val velocity = velocityTracker.xVelocity
						velocityTracker.recycle()
						val targetOffsetX = decay.calculateTargetValue(offsetX.value, velocity)
						if (targetOffsetX.absoluteValue <= size.width / 2) {
							// Not enough velocity; Reset.
							launch { offsetX.animateTo(targetValue = 0f, initialVelocity = velocity) }
						} else {
							// Enough velocity to remove the card
							val duration = 600
							val maxDistanceToFling = (size.width * 2.0).toFloat()
							val easeInOutEasing = CubicBezierEasing(0.42f, 0.0f, 0.58f, 1.0f)
							val distanceToFling = min(
								targetOffsetX.absoluteValue + size.width,
								maxDistanceToFling
							)
							val animationJobs = listOf(
								launch {
									offsetX.animateTo(
										targetValue = 0f,
										initialVelocity = velocity,
										animationSpec = keyframes {
											durationMillis = duration
											-distanceToFling at (duration / 2) with easeInOutEasing
											40f at duration - 70
										}
									) {
										if (value <= -size.width && !clearedHurdle) {
											onMoveToRemove()
											clearedHurdle = true
										}
									}
								}
							)
							animationJobs.joinAll()
							clearedHurdle = false
						}
					}
				}
			}
				.offset { IntOffset(offsetX.value.roundToInt(), 0) }
				.graphicsLayer {
					transformOrigin = TransformOrigin.Center
				}
		}
	}