package com.lyfe.android.core.data.datasource.di

import com.lyfe.android.core.data.datasource.AuthDataSource
import com.lyfe.android.core.data.datasource.AuthDataSourceImpl
import com.lyfe.android.core.data.datasource.DeviceGalleryDataSource
import com.lyfe.android.core.data.datasource.DeviceGalleryDataSourceImpl
import com.lyfe.android.core.data.datasource.ImageDataSource
import com.lyfe.android.core.data.datasource.ImageDataSourceImpl
import com.lyfe.android.core.data.datasource.LocalTokenDataSource
import com.lyfe.android.core.data.datasource.LocalTokenDataSourceImpl
import com.lyfe.android.core.data.datasource.RemoteUserDataSource
import com.lyfe.android.core.data.datasource.RemoteUserDataSourceImpl
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
	fun bindsRemoteUserDataSource(
		remoteUserDataSourceImpl: RemoteUserDataSourceImpl
	): RemoteUserDataSource

	@Singleton
	@Binds
	fun bindsLocalTokenDataSource(
		localTokenDataSourceImpl: LocalTokenDataSourceImpl
	): LocalTokenDataSource

	@Singleton
	@Binds
	fun bindsAuthDataSource(
		authDataSourceImpl: AuthDataSourceImpl
	): AuthDataSource

	@Singleton
	@Binds
	fun bindsImageDataSource(
		imageDataSourceImpl: ImageDataSourceImpl
	): ImageDataSource
}