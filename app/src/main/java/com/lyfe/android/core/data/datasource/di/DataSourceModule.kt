package com.lyfe.android.core.data.datasource.di

import com.lyfe.android.core.data.datasource.DeviceGalleryDataSource
import com.lyfe.android.core.data.datasource.DeviceGalleryDataSourceImpl
import com.lyfe.android.core.data.datasource.TestDataSource
import com.lyfe.android.core.data.datasource.TestDataSourceImpl
import com.lyfe.android.core.data.datasource.UserDataSource
import com.lyfe.android.core.data.datasource.UserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

	@Singleton
	@Binds
	fun bindsDeviceGalleryImageDataSource(
		deviceGalleryImageDataSourceImpl: DeviceGalleryDataSourceImpl
	): DeviceGalleryDataSource

	@Singleton
	@Binds
	fun bindsTestDataSource(
		testDataSourceImpl: TestDataSourceImpl
	): TestDataSource

	@Singleton
	@Binds
	fun bindsUserDataSource(
		userDataSourceImpl: UserDataSourceImpl
	): UserDataSource
}