package com.lyfe.android.data.network.converter

import kotlinx.serialization.DeserializationStrategy
import okhttp3.ResponseBody
import retrofit2.Converter

internal class DeserializationStrategyConverter<T>(
	private val loader: DeserializationStrategy<T>,
	private val lyfeSerializer: LyfeSerializer
) : Converter<ResponseBody, T> {
	override fun convert(value: ResponseBody) = lyfeSerializer.fromResponseBody(loader, value)
}