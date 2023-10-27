package com.lyfe.android.core.data.network.di

import com.lyfe.android.core.data.network.Dispatcher
import com.lyfe.android.core.data.network.LyfeDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

	@Provides
	@Dispatcher(LyfeDispatchers.IO)
	fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

	@Provides
	@Dispatcher(LyfeDispatchers.Default)
	fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}