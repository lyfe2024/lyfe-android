package com.lyfe.android.core.common.ui.util

interface MultipleEventsBlockManager {
	fun processEvent(event: () -> Unit)

	companion object
}

internal fun MultipleEventsBlockManager.Companion.get(): MultipleEventsBlockManager =
	MultipleEventsBlockManagerImpl()

private class MultipleEventsBlockManagerImpl : MultipleEventsBlockManager {
	private val now: Long
		get() = System.currentTimeMillis()

	private var lastEventTimeMs: Long = 0

	override fun processEvent(event: () -> Unit) {
		if (now - lastEventTimeMs >= 300L) {
			event.invoke()
		}
		lastEventTimeMs = now
	}
}