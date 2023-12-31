package com.lyfe.android.core.data.di

import com.lyfe.android.core.data.repository.AlbumRepositoryImpl
import com.lyfe.android.core.data.repository.TestRepositoryImpl
import com.lyfe.android.core.data.repository.UserRepositoryImpl
import com.lyfe.android.core.data.repository.fake.FakeLyfeRepository
import com.lyfe.android.core.domain.repository.AlbumRepository
import com.lyfe.android.core.domain.repository.LyfeRepository
import com.lyfe.android.core.domain.repository.TestRepository
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
	fun bindsTestRepository(
		testRepositoryImpl: TestRepositoryImpl
	): TestRepository

	@Singleton
	@Binds
	fun bindsUserRepository(
		userRepositoryImpl: UserRepositoryImpl
	): UserRepository
}