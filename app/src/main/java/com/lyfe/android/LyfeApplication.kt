package com.lyfe.android

import android.app.Application
import com.google.firebase.FirebaseApp
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LyfeApplication : Application() {
	override fun onCreate() {
		super.onCreate()
		KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
		FirebaseApp.initializeApp(this)
	}
}