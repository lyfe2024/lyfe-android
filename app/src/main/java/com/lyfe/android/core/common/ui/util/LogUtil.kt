package com.lyfe.android.core.common.ui.util

import android.util.Log
import com.lyfe.android.BuildConfig

object LogUtil {
	fun i(tag: String = "", message: String) {
		if (BuildConfig.DEBUG) {
			Log.i(tag, message)
		}
	}

	fun w(tag: String = "", message: String) {
		if (BuildConfig.DEBUG) {
			Log.w(tag, message)
		}
	}

	fun v(tag: String = "", message: String) {
		if (BuildConfig.DEBUG) {
			Log.v(tag, message)
		}
	}

	fun e(tag: String = "", message: String) {
		if (BuildConfig.DEBUG) {
			Log.e(tag, message)
		}
	}

	fun d(tag: String = "", message: String) {
		if (BuildConfig.DEBUG) {
			Log.d(tag, message)
		}
	}
}

fun Exception.printLog() = println(this.message ?: "Empty Exception Error Message")