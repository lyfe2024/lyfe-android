package com.lyfe.android.core.common.ui.util

interface MultipleEventsBlockManager {
	fun processEvent(event: () -> Unit)

	companion object
}