package com.lyfe.android.core.data.network.converter

import kotlinx.serialization.SerializationStrategy
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Converter

internal class SerializationStrategyConverter<T>(
	private val contentType: MediaType,
	private val saver: SerializationStrategy<T>,
	private val lyfeSerializer: LyfeSerializer
) : Converter<T, RequestBody> {
	override fun convert(value: T) = lyfeSerializer.toRequestBody(contentType, saver, value)
}