package com.lyfe.android.core.data.datasource.di

import com.lyfe.android.core.data.datasource.AuthDataSource
import com.lyfe.android.core.data.datasource.AuthDataSourceImpl
import com.lyfe.android.core.data.datasource.DeviceGalleryDataSource
import com.lyfe.android.core.data.datasource.DeviceGalleryDataSourceImpl
import com.lyfe.android.core.data.datasource.FeedbackDataSource
import com.lyfe.android.core.data.datasource.FeedbackDataSourceImpl
import com.lyfe.android.core.data.datasource.ImageDataSource
import com.lyfe.android.core.data.datasource.ImageDataSourceImpl
import com.lyfe.android.core.data.datasource.LocalUserDataSource
import com.lyfe.android.core.data.datasource.LocalUserDataSourceImpl
import com.lyfe.android.core.data.datasource.PolicyDataSource
import com.lyfe.android.core.data.datasource.PolicyDataSourceImpl
import com.lyfe.android.core.data.datasource.RemoteUserDataSource
import com.lyfe.android.core.data.datasource.RemoteUserDataSourceImpl
import com.lyfe.android.core.data.datasource.TokenDataSource
import com.lyfe.android.core.data.datasource.TokenLocalDataSource
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

	@Binds
	fun bindsTokenLocalDataSource(
		tokenLocalDataSource: TokenLocalDataSource
	): TokenDataSource

	@Singleton
	@Binds
	fun bindsLocalUserDataSource(
		localUserDataSourceImpl: LocalUserDataSourceImpl
	): LocalUserDataSource

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

	@Singleton
	@Binds
	fun bindsPolicyDataSource(
		policyDataSourceImpl: PolicyDataSourceImpl
	): PolicyDataSource

	@Singleton
	@Binds
	fun bindsFeedbackDataSource(
		feedbackDataSourceImpl: FeedbackDataSourceImpl
	): FeedbackDataSource
}