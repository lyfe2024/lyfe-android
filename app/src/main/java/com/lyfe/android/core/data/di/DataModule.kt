package com.lyfe.android.core.data.di

import com.lyfe.android.core.data.repository.AlbumRepositoryImpl
import com.lyfe.android.core.data.repository.AuthRepositoryImpl
import com.lyfe.android.core.data.repository.ImageRepositoryImpl
import com.lyfe.android.core.data.repository.PolicyRepositoryImpl
import com.lyfe.android.core.data.repository.TokenRepositoryImpl
import com.lyfe.android.core.data.repository.UserRepositoryImpl
import com.lyfe.android.core.data.repository.fake.FakeLyfeRepository
import com.lyfe.android.core.domain.repository.AlbumRepository
import com.lyfe.android.core.domain.repository.AuthRepository
import com.lyfe.android.core.domain.repository.ImageRepository
import com.lyfe.android.core.domain.repository.LyfeRepository
import com.lyfe.android.core.domain.repository.PolicyRepository
import com.lyfe.android.core.domain.repository.TokenRepository
import com.lyfe.android.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

	// 추후 LyfeRepository로 변경해야함
	@Singleton
	@Binds
	fun bindsLyfeRepository(
		lyfeRepositoryImpl: FakeLyfeRepository
	): LyfeRepository

	@Singleton
	@Binds
	fun bindsImageRepository(
		imageRepository: AlbumRepositoryImpl
	): AlbumRepository

	@Singleton
	@Binds
	fun bindsUserRepository(
		userRepositoryImpl: UserRepositoryImpl
	): UserRepository

	@Singleton
	@Binds
	fun bindsAuthRepository(
		authRepositoryImpl: AuthRepositoryImpl
	): AuthRepository

	@Singleton
	@Binds
	fun bindsTokenRepository(
		tokenRepositoryImpl: TokenRepositoryImpl
	): TokenRepository

	@Singleton
	@Binds
	fun bindsImageUrlRepository(
		imageRepositoryImpl: ImageRepositoryImpl
	): ImageRepository

	@Singleton
	@Binds
	fun bindsPolicyRepository(
		policyRepositoryImpl: PolicyRepositoryImpl
	): PolicyRepository
}