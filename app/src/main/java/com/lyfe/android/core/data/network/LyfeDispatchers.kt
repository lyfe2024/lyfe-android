package com.lyfe.android.core.data.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val lyfeDispatcher: LyfeDispatchers)

enum class LyfeDispatchers {
	Default,
	IO
}