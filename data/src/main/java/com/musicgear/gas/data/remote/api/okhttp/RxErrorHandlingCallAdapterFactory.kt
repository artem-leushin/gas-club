package com.musicgear.gas.data.remote.api.okhttp

import com.musicgear.gas.domain.exception.ConnectionErrorException
import com.musicgear.gas.data.remote.api.entity.ErrorHolder
import com.musicgear.gas.data.remote.api.entity.toException
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal class RxErrorHandlingCallAdapterFactory : CallAdapter.Factory() {

  private val originalFactory: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

  override fun get(
    returnType: Type,
    annotations: Array<out Annotation>,
    retrofit: Retrofit
  ): CallAdapter<*, *>? {
    return RxCallAdapterWrapper(
      retrofit,
      originalFactory.get(returnType, annotations, retrofit) as CallAdapter<*, *>
    )
  }
}

internal class RxCallAdapterWrapper<R, T>(
  private val retrofit: Retrofit,
  private val wrapped: CallAdapter<R, T>
) : CallAdapter<R, T> {

  override fun responseType(): Type = wrapped.responseType()

  override fun adapt(call: Call<R>): T = with(wrapped.adapt(call)) {
    when (this) {
      is Single<*> -> onErrorResumeNext { throwable: Throwable ->
        Single.error(mapException(throwable))
      } as T

      is Observable<*> -> onErrorResumeNext { throwable: Throwable ->
        Observable.error(mapException(throwable))
      } as T

      is Flowable<*> -> onErrorResumeNext { throwable: Throwable ->
        Flowable.error(mapException(throwable))
      } as T

      is Completable -> onErrorResumeNext { throwable: Throwable ->
        Completable.error(mapException(throwable))
      } as T

      is Maybe<*> -> onErrorResumeNext { throwable: Throwable ->
        Maybe.error(mapException(throwable))
      } as T
      else -> this
    }
  }

  private fun mapException(responseError: Throwable): Throwable = when (responseError) {
    is ServerHttpException -> {
      if (responseError.code() == 503) ConnectionErrorException(responseError)
      else mapServerException(responseError)
    }
    is SocketTimeoutException -> ConnectionErrorException(responseError)
    is UnknownHostException -> ConnectionErrorException(responseError)
    else -> responseError
  }

  private fun mapServerException(responseError: ServerHttpException): RuntimeException {
    val bodyConverter: Converter<ResponseBody, ErrorHolder> =
      retrofit.responseBodyConverter(ErrorHolder::class.java, emptyArray())

    val errorBody = responseError.response()?.errorBody()

    val holder = try {
      errorBody?.let { bodyConverter.convert(it) }
        ?: ErrorHolder(
          responseError.code(),
          message = responseError.message()
        )
    } catch (ex: IOException) {
      ErrorHolder(
        responseError.code(),
        message = responseError.message()
      )
    }

    return holder.toException()
  }
}

typealias ServerHttpException = retrofit2.HttpException
