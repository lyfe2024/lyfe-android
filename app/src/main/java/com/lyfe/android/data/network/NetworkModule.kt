package com.lyfe.android.data.network

import com.lyfe.android.BuildConfig
import com.lyfe.android.data.network.converter.asConverterFactory
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

	private val contentType = "application/json".toMediaType()

	@Provides
	@Singleton
	fun providesLyfeOkHttpClient(): OkHttpClient =
		OkHttpClient.Builder()
			.connectTimeout(15, TimeUnit.SECONDS)
			.writeTimeout(20, TimeUnit.SECONDS)
			.readTimeout(15, TimeUnit.SECONDS)
			.addInterceptor(HttpLoggingInterceptor().apply {
				level = HttpLoggingInterceptor.Level.BODY
			}).build()

	@Provides
	@Singleton
	fun providesLyfeRetrofit(okHttpClient: OkHttpClient): Retrofit =
		Retrofit.Builder()
			.baseUrl(BuildConfig.BASE_URL)
			.client(okHttpClient)
			.addConverterFactory(Json.asConverterFactory(contentType))
			.build()
}