package com.lyfe.android.core.data.network.di

import com.lyfe.android.BuildConfig
import com.lyfe.android.core.data.network.adapter.ResultCallAdapterFactory
import com.lyfe.android.core.data.network.converter.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	private const val ConnectTimeout = 15L
	private const val WriteTimeout = 20L
	private const val ReadTimeout = 15L
	private val contentType = "application/json".toMediaType()

	@Provides
	@Singleton
	fun providesLyfeOkHttpClient(): OkHttpClient =
		OkHttpClient.Builder()
			.connectTimeout(ConnectTimeout, TimeUnit.SECONDS)
			.writeTimeout(WriteTimeout, TimeUnit.SECONDS)
			.readTimeout(ReadTimeout, TimeUnit.SECONDS)
			.addInterceptor(
				HttpLoggingInterceptor().apply {
					level = HttpLoggingInterceptor.Level.BODY
				}
			).build()

	@Provides
	@Singleton
	fun providesLyfeRetrofit(okHttpClient: OkHttpClient): Retrofit =
		Retrofit.Builder()
			.baseUrl(BuildConfig.BASE_URL)
			.client(okHttpClient)
			.addConverterFactory(Json.asConverterFactory(contentType))
			.addCallAdapterFactory(ResultCallAdapterFactory())
			.build()

	@Provides
	@Singleton
	fun providesGoogleRetrofit(okHttpClient: OkHttpClient): Retrofit =
		Retrofit.Builder()
			.baseUrl(BuildConfig.GOOGLE_API_URL)
			.client(okHttpClient)
			.addConverterFactory(Json.asConverterFactory(contentType))
			.addCallAdapterFactory(ResultCallAdapterFactory())
			.build()
}