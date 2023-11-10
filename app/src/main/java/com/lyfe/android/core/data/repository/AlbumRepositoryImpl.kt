package com.lyfe.android.core.data.repository

import com.lyfe.android.core.data.datasource.DeviceGalleryDataSource
import com.lyfe.android.core.data.mapper.toDomain
import com.lyfe.android.core.data.network.Dispatcher
import com.lyfe.android.core.data.network.LyfeDispatchers
import com.lyfe.android.core.domain.repository.AlbumRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
	private val deviceGalleryDataSource: DeviceGalleryDataSource,
	@Dispatcher(LyfeDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : AlbumRepository {

	override fun getAllPhotos() = flow {
		emit(deviceGalleryDataSource.getAllPhotos().map { it.toDomain() })
	}.flowOn(ioDispatcher)
}