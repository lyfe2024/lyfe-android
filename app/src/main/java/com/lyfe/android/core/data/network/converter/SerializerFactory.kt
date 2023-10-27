package com.lyfe.android.core.data.network.converter

import com.lyfe.android.core.data.network.converter.LyfeSerializer.FromString
import com.lyfe.android.core.data.network.converter.LyfeSerializer.FromBytes
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.StringFormat
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

internal class SerializerFactory(
	private val contentType: MediaType,
	private val lyfeSerializer: LyfeSerializer
) : Converter.Factory() {
	@Suppress("RedundantNullableReturnType") // Retaining interface contract.
	override fun responseBodyConverter(
		type: Type,
		annotations: Array<out Annotation>,
		retrofit: Retrofit
	): Converter<ResponseBody, *>? {
		val loader = lyfeSerializer.serializer(type)
		return DeserializationStrategyConverter(loader, lyfeSerializer)
	}

	@Suppress("RedundantNullableReturnType") // Retaining interface contract.
	override fun requestBodyConverter(
		type: Type,
		parameterAnnotations: Array<out Annotation>,
		methodAnnotations: Array<out Annotation>,
		retrofit: Retrofit
	): Converter<*, RequestBody>? {
		val saver = lyfeSerializer.serializer(type)
		return SerializationStrategyConverter(contentType, saver, lyfeSerializer)
	}
}

/**
 * Return a [Converter.Factory] which uses Kotlin serialization for string-based payloads.
 *
 * Because Kotlin serialization is so flexible in the types it supports, this converter assumes
 * that it can handle all types. If you are mixing this with something else, you must add this
 * instance last to allow the other converters a chance to see their types.
 */
@JvmName("create")
fun StringFormat.asConverterFactory(contentType: MediaType): Converter.Factory {
	return SerializerFactory(contentType, FromString(this))
}

/**
 * Return a [Converter.Factory] which uses Kotlin serialization for byte-based payloads.
 *
 * Because Kotlin serialization is so flexible in the types it supports, this converter assumes
 * that it can handle all types. If you are mixing this with something else, you must add this
 * instance last to allow the other converters a chance to see their types.
 */
@JvmName("create")
fun BinaryFormat.asConverterFactory(contentType: MediaType): Converter.Factory {
	return SerializerFactory(contentType, FromBytes(this))
}