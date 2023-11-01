package com.lyfe.android.core.common.ui.util

internal fun MultipleEventsBlockManager.Companion.get(): MultipleEventsBlockManager =
	MultipleEventsBlockManagerImpl()

private class MultipleEventsBlockManagerImpl : MultipleEventsBlockManager {
	private val now: Long
		get() = System.currentTimeMillis()

	private var lastEventTimeMs: Long = 0

	private val delayTime = 300L

	override fun processEvent(event: () -> Unit) {
		if (now - lastEventTimeMs >= delayTime) {
			event.invoke()
		}
		lastEventTimeMs = now
	}
}