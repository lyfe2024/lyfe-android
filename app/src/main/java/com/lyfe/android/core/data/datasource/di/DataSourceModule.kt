package com.lyfe.android.core.data.datasource.di

import com.lyfe.android.core.data.datasource.DeviceGalleryDataSource
import com.lyfe.android.core.data.datasource.DeviceGalleryDataSourceImpl
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
}