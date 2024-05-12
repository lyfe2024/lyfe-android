package com.lyfe.android.core.data.network.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.lyfe.android.BuildConfig
import com.lyfe.android.core.data.network.token.TokenManager
import com.lyfe.android.core.data.network.adapter.ResultCallAdapterFactory
import com.lyfe.android.core.data.network.authenticator.TokenAuthenticator
import com.lyfe.android.core.data.network.converter.asConverterFactory
import com.lyfe.android.core.data.network.interceptor.NetworkInterceptor
import com.lyfe.android.core.data.network.service.AWSService
import com.lyfe.android.core.data.network.service.AuthService
import com.lyfe.android.core.data.network.service.BoardService
import com.lyfe.android.core.data.network.service.CommentService
import com.lyfe.android.core.data.network.service.FeedbackService
import com.lyfe.android.core.data.network.service.ImageService
import com.lyfe.android.core.data.network.service.NotificationService
import com.lyfe.android.core.data.network.service.PolicyService
import com.lyfe.android.core.data.network.service.TopicService
import com.lyfe.android.core.data.network.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.Interceptor
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
	private const val TOKEN_MANAGER_DATASTORE_FILE_NAME = "token_manager_datastore"

	@Provides
	@Singleton
	fun providesLyfeOkHttpClient(
		networkInterceptor: NetworkInterceptor,
		tokenAuthenticator: TokenAuthenticator
	): OkHttpClient = createOkHttpClient(networkInterceptor, tokenAuthenticator)

	@Singleton
	@Provides
	fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
		val dataStore = PreferenceDataStoreFactory.create(
			produceFile = {
				context.preferencesDataStoreFile(TOKEN_MANAGER_DATASTORE_FILE_NAME)
			}
		)
		return TokenManager(dataStore)
	}

	@Provides
	@Singleton
	fun providesLyfeRetrofit(okHttpClient: OkHttpClient): Retrofit {
		val jsonConfig = Json { isLenient = true }
		return createRetrofit(
			baseUrl = BuildConfig.BASE_URL,
			okHttpClient = okHttpClient,
			jsonConfig = jsonConfig
		)
	}

	@Provides
	@Singleton
	@Named("AWS")
	fun providesAWSRetrofit(): Retrofit {
		val jsonConfig = Json { isLenient = true }
		return createRetrofit(
			baseUrl = BuildConfig.AWS_BASE_URL,
			okHttpClient = createOkHttpClient(),
			jsonConfig = jsonConfig
		)
	}

	private fun createOkHttpClient(
		networkInterceptor: Interceptor? = null,
		tokenAuthenticator: Authenticator? = null
	) = OkHttpClient.Builder()
		.connectTimeout(ConnectTimeout, TimeUnit.SECONDS)
		.writeTimeout(WriteTimeout, TimeUnit.SECONDS)
		.readTimeout(ReadTimeout, TimeUnit.SECONDS)
		.addInterceptor(
			HttpLoggingInterceptor().apply {
				level = HttpLoggingInterceptor.Level.BODY
			}
		).apply {
			if (tokenAuthenticator != null) {
				authenticator(tokenAuthenticator)
			}
			if (networkInterceptor != null) {
				addInterceptor(networkInterceptor)
			}
		}
		.build()

	private fun createRetrofit(baseUrl: String, okHttpClient: OkHttpClient, jsonConfig: Json): Retrofit =
		Retrofit.Builder()
			.baseUrl(baseUrl)
			.addConverterFactory(jsonConfig.asConverterFactory(contentType))
			.addCallAdapterFactory(ResultCallAdapterFactory())
			.client(okHttpClient)
			.build()

	@Provides
	@Singleton
	fun providesUserService(retrofit: Retrofit): UserService {
		return retrofit.create(UserService::class.java)
	}

	@Provides
	@Singleton
	fun providesAuthService(retrofit: Retrofit): AuthService {
		return retrofit.create(AuthService::class.java)
	}

	@Provides
	@Singleton
	fun providesImageService(retrofit: Retrofit): ImageService {
		return retrofit.create(ImageService::class.java)
	}

	@Provides
	@Singleton
	fun providesPolicyService(retrofit: Retrofit): PolicyService {
		return retrofit.create(PolicyService::class.java)
	}

	@Provides
	@Singleton
	fun providesFeedbackService(retrofit: Retrofit): FeedbackService {
		return retrofit.create(FeedbackService::class.java)
	}

	@Provides
	@Singleton
	fun providesNotificationService(retrofit: Retrofit): NotificationService {
		return retrofit.create(NotificationService::class.java)
	}

	@Provides
	@Singleton
	fun providesBoardService(retrofit: Retrofit): BoardService {
		return retrofit.create(BoardService::class.java)
	}

	@Provides
	@Singleton
	fun providesCommentService(retrofit: Retrofit): CommentService {
		return retrofit.create(CommentService::class.java)
	}

	@Provides
	@Singleton
	fun providesTopicService(retrofit: Retrofit): TopicService {
		return retrofit.create(TopicService::class.java)
	}

	@Provides
	@Singleton
	fun providesAWSService(@Named("AWS") retrofit: Retrofit): AWSService {
		return retrofit.create(AWSService::class.java)
	}

	@Provides
	@Singleton
	@Named("authenticator")
	fun providesAuthenticatorAuthService(): AuthService {
		val jsonConfig = Json { isLenient = true }

		val retrofit = createRetrofit(
			baseUrl = BuildConfig.BASE_URL,
			jsonConfig = jsonConfig,
			okHttpClient = createOkHttpClient()
		)

		return retrofit.create(AuthService::class.java)
	}
}