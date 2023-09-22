package com.lyfe.android.core.navigation

import com.lyfe.android.core.navigation.navigator.LyfeNavigator
import com.lyfe.android.core.navigation.navigator.LyfeNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NavigationModule {

	@Binds
	abstract fun provideLyfeNavigator(
		lyfeNavigatorImpl: LyfeNavigatorImpl
	): LyfeNavigator
}