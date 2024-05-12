package com.lyfe.android.core.data.datasource.di

import com.lyfe.android.core.data.datasource.AuthDataSource
import com.lyfe.android.core.data.datasource.AuthRemoteDataSource
import com.lyfe.android.core.data.datasource.CommentDataSource
import com.lyfe.android.core.data.datasource.CommentRemoteDataSource
import com.lyfe.android.core.data.datasource.BoardDataSource
import com.lyfe.android.core.data.datasource.BoardRemoteDataSource
import com.lyfe.android.core.data.datasource.DeviceGalleryDataSource
import com.lyfe.android.core.data.datasource.DeviceGalleryDataSourceImpl
import com.lyfe.android.core.data.datasource.FeedbackDataSource
import com.lyfe.android.core.data.datasource.FeedbackRemoteDataSource
import com.lyfe.android.core.data.datasource.ImageDataSource
import com.lyfe.android.core.data.datasource.ImageRemoteDataSource
import com.lyfe.android.core.data.datasource.NotificationDataSource
import com.lyfe.android.core.data.datasource.NotificationRemoteDataSource
import com.lyfe.android.core.data.datasource.PolicyDataSource
import com.lyfe.android.core.data.datasource.PolicyRemoteDataSource
import com.lyfe.android.core.data.datasource.TokenDataSource
import com.lyfe.android.core.data.datasource.TokenLocalDataSource
import com.lyfe.android.core.data.datasource.TopicDataSource
import com.lyfe.android.core.data.datasource.TopicRemoteDataSource
import com.lyfe.android.core.data.datasource.UserLocalDataSource
import com.lyfe.android.core.data.datasource.UserLocalDataSourceImpl
import com.lyfe.android.core.data.datasource.UserRemoteDataSource
import com.lyfe.android.core.data.datasource.UserRemoteDataSourceImpl
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
	fun bindsUserRemoteDataSource(
		userRemoteDataSourceImpl: UserRemoteDataSourceImpl
	): UserRemoteDataSource

	@Binds
	fun bindsTokenLocalDataSource(
		tokenLocalDataSource: TokenLocalDataSource
	): TokenDataSource

	@Singleton
	@Binds
	fun bindsLocalUserDataSource(
		userLocalDataSourceImpl: UserLocalDataSourceImpl
	): UserLocalDataSource

	@Singleton
	@Binds
	fun bindsAuthDataSource(
		authRemoteDataSourceImpl: AuthRemoteDataSource
	): AuthDataSource

	@Singleton
	@Binds
	fun bindsImageDataSource(
		imageRemoteDataSource: ImageRemoteDataSource
	): ImageDataSource

	@Singleton
	@Binds
	fun bindsPolicyDataSource(
		policyRemoteDataSource: PolicyRemoteDataSource
	): PolicyDataSource

	@Singleton
	@Binds
	fun bindsNotificationDataSource(
		notificationRemoteDataSource: NotificationRemoteDataSource
	): NotificationDataSource

	@Singleton
	@Binds
	fun bindsFeedbackDataSource(
		feedbackRemoteDataSource: FeedbackRemoteDataSource
	): FeedbackDataSource

	@Singleton
	@Binds
	fun bindsBoardDataSource(
		boardRemoteDataSource: BoardRemoteDataSource
	): BoardDataSource

	@Singleton
	@Binds
	fun bindsCommentDataSource(
		commentRemoteDataSource: CommentRemoteDataSource
	) : CommentDataSource

	@Singleton
	@Binds
	fun bindsTopicDataSource(
		topicRemoteDataSource: TopicRemoteDataSource
	): TopicDataSource
}