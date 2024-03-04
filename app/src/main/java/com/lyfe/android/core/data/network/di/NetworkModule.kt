package com.lyfe.android.core.data.network.di

import com.lyfe.android.BuildConfig
import com.lyfe.android.core.data.network.adapter.ResultCallAdapterFactory
import com.lyfe.android.core.data.network.authenticator.TokenAuthenticator
import com.lyfe.android.core.data.network.converter.asConverterFactory
import com.lyfe.android.core.data.network.interceptor.TokenInterceptor
import com.lyfe.android.core.data.network.service.AuthService
import com.lyfe.android.core.data.network.service.UserService
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
import javax.inject.Named
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
	fun providesLyfeOkHttpClient(
		tokenInterceptor: TokenInterceptor,
		tokenAuthenticator: TokenAuthenticator
	): OkHttpClient =
		OkHttpClient.Builder()
			.connectTimeout(ConnectTimeout, TimeUnit.SECONDS)
			.writeTimeout(WriteTimeout, TimeUnit.SECONDS)
			.readTimeout(ReadTimeout, TimeUnit.SECONDS)
			.addInterceptor(
				HttpLoggingInterceptor().apply {
					level = HttpLoggingInterceptor.Level.BODY
				}
			)
			.addInterceptor(tokenInterceptor)
			.authenticator(tokenAuthenticator)
			.build()

	@Provides
	@Singleton
	fun providesLyfeRetrofit(okHttpClient: OkHttpClient): Retrofit {
		val jsonConfig = Json { isLenient = true }

		return Retrofit.Builder()
			.baseUrl(BuildConfig.BASE_URL)
			.client(okHttpClient)
			.addConverterFactory(jsonConfig.asConverterFactory(contentType))
			.addCallAdapterFactory(ResultCallAdapterFactory())
			.build()
	}

	@Provides
	@Singleton
	fun providesUserService(retrofit: Retrofit): UserService {
		return retrofit.create(UserService::class.java)
	}

	@Provides
	@Singleton
	@Named("lyfe")
	fun providesLyfeAuthService(retrofit: Retrofit): AuthService {
		return retrofit.create(AuthService::class.java)
	}

	@Provides
	@Singleton
	@Named("authenticator")
	fun providesAuthenticatorAuthService(): AuthService {
		val jsonConfig = Json { isLenient = true }

		val okHttpClient = OkHttpClient.Builder()
			.connectTimeout(ConnectTimeout, TimeUnit.SECONDS)
			.writeTimeout(WriteTimeout, TimeUnit.SECONDS)
			.readTimeout(ReadTimeout, TimeUnit.SECONDS)
			.addInterceptor(
				HttpLoggingInterceptor().apply {
					level = HttpLoggingInterceptor.Level.BODY
				}
			)
			.build()

		val retrofit = Retrofit.Builder()
			.baseUrl(BuildConfig.BASE_URL)
			.addConverterFactory(jsonConfig.asConverterFactory(contentType))
			.addCallAdapterFactory(ResultCallAdapterFactory())
			.client(okHttpClient)
			.build()

		return retrofit.create(AuthService::class.java)
	}
}