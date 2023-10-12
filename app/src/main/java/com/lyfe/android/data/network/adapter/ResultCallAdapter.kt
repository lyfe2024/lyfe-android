package com.lyfe.android.data.network.adapter

import com.lyfe.android.data.network.model.Result
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class ResultCallAdapter<R : Any>(
	private val responseType: Type
) : CallAdapter<R, Call<Result<R>>> {

	override fun responseType(): Type = responseType

	override fun adapt(call: Call<R>): Call<Result<R>> = ResultCall(call)
}