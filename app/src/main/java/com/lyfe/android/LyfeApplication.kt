package com.lyfe.android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LyfeApplication : Application() {
	override fun onCreate() {
		super.onCreate()
		KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
	}
}