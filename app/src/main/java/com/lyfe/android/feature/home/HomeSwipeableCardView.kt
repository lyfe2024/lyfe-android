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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.lyfe.android.R
import com.lyfe.android.core.common.ui.component.LyfeCardViewDesignType
import com.lyfe.android.core.common.ui.component.LyfeFeedCardView
import com.lyfe.android.core.common.ui.theme.Grey10
import com.lyfe.android.core.common.ui.theme.Grey100
import com.lyfe.android.core.common.ui.theme.Grey200
import com.lyfe.android.core.common.ui.theme.Grey300
import com.lyfe.android.core.common.ui.theme.Grey400
import com.lyfe.android.core.common.ui.theme.H5
import com.lyfe.android.core.common.ui.util.clickableSingle
import com.lyfe.android.core.common.ui.util.shadow
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
private const val SHADOW_ALPHA = 0.25f

private val fakeFeed = Feed(
	feedId = 0L,
	title = "",
	content = "",
	feedImageUrl = "",
	date = "",
	userId = 0L,
	userName = "",
	userProfileImgUrl = "",
	whiskyCount = 0,
	commentCount = 0,
	isLike = false
)

@Composable
fun HomeSwipeableImageFeeds(
	modifier: Modifier,
	feeds: List<Feed>,
	onFeedClick: (feedId: Long) -> Unit,
	onMoreFeedClick: () -> Unit
) {
	val fakeFeeds = List(4) { fakeFeed }
	val shownFeeds = (feeds + fakeFeeds).reversed()

	var feedList by remember(shownFeeds) { mutableStateOf(shownFeeds) }

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
						// 카드 하나 지우면 list 에서 해당 카드 제거
						val removed = feedList.last()
						feedList = feedList - removed
					},
					onClick = { onFeedClick(feed.feedId) },
					onMoreFeedClick = onMoreFeedClick
				)
			}
		}
	}
}

@Composable
fun HomeSwipeableCard(
	modifier: Modifier = Modifier,
	order: Int,
	totalCount: Int,
	feed: Feed,
	onMoveToRemove: () -> Unit,
	onClick: (Feed) -> Unit,
	onMoreFeedClick: () -> Unit
) {
	val alphaState by animateFloatAsState(
		targetValue = if (order >= totalCount - 4) 1f else 0f,
		label = ""
	)
	val animatedScale by animateFloatAsState(
		targetValue = 1f - (totalCount - order - 1) * 0.05f,
		label = ""
	)
	val animatedXOffset by animateDpAsState(
		targetValue = ((totalCount - order - 1) * 24).dp,
		label = ""
	)
	val shadowXOffset by animateDpAsState(
		targetValue = (2 - (totalCount - order - 1) * 0.11).dp,
		label = ""
	)
	val shadowYOffset by animateDpAsState(
		targetValue = (6 - (totalCount - order - 1) * 0.33).dp,
		label = ""
	)
	val animateBlurRadius by animateDpAsState(
		targetValue = (10 - (totalCount - order - 1) * 0.56).dp,
		label = ""
	)

	Box(
		modifier = modifier
			.offset { IntOffset(x = animatedXOffset.roundToPx(), y = 0) }
			.graphicsLayer {
				scaleX = animatedScale
				scaleY = animatedScale
			}
			.clickableSingle {
				if (feed.feedId > 0L) {
					onClick(feed)
				}
			}
			.swipeToRemove(
				isSwipeableOrder = (order == totalCount - 1) && feed.feedId > 0L,
				onMoveToRemove = onMoveToRemove
			)
			.alpha(alphaState)
	) {
		if (feed.feedId != 0L) {
			LyfeFeedCardView(
				modifier = Modifier
					.fillMaxWidth(MAXIMUM_CARD_RATIO)
					.shadow(
						color = Color(
							red = 0f,
							green = 0f,
							blue = 0f,
							alpha = SHADOW_ALPHA
						),
						offsetX = shadowXOffset,
						offsetY = shadowYOffset,
						blurRadius = animateBlurRadius
					),
				feed = feed,
				designType = LyfeCardViewDesignType.HOME_SCREEN_CARD
			)
		} else {
			MoreFeedCardView(
				modifier = Modifier
					.fillMaxWidth(MAXIMUM_CARD_RATIO)
					.shadow(
						color = Color(
							red = 0f,
							green = 0f,
							blue = 0f,
							alpha = SHADOW_ALPHA
						),
						offsetX = shadowXOffset,
						offsetY = shadowYOffset,
						blurRadius = animateBlurRadius
					),
				order = totalCount - order,
				onMoreFeedClick = onMoreFeedClick
			)
		}
	}
}

@Composable
private fun MoreFeedCardView(
	modifier: Modifier,
	designType: LyfeCardViewDesignType = LyfeCardViewDesignType.HOME_SCREEN_CARD,
	order: Int,
	onMoreFeedClick: () -> Unit
) {
	val backgroundColor = when (order) {
		1 -> Grey300
		2 -> Grey200
		3 -> Grey100
		4 -> Grey10
		else -> Color.Transparent
	}

	Box(
		modifier = modifier
			.widthIn(min = 152.dp)
			.aspectRatio(designType.ratio)
			.fillMaxSize()
			.background(
				color = backgroundColor,
				shape = RoundedCornerShape(16.dp)
			)
	) {
		Column(
			modifier = Modifier
				.align(Alignment.Center)
				.size(128.dp)
				.background(
					color = Grey400,
					shape = CircleShape
				)
				.clickableSingle { onMoreFeedClick() },
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			Image(
				painter = painterResource(id = R.drawable.ic_arrow_next_white),
				contentDescription = "더보기"
			)

			Text(
				text = stringResource(id = R.string.home_feed_more),
				style = H5,
				color = Color.White
			)
		}
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
							launch {
								offsetX.animateTo(
									targetValue = 0f,
									initialVelocity = velocity
								)
							}
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