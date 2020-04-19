package com.tranced.twtquestionaire.commons.experimental.network

import android.os.Build
import com.tranced.twtquestionaire.commons.experimental.CommonContext
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * User-Agent's format: "$ApplicationName/$ApplicationVersion($Device; $OS $OSVersion)"
 */
internal val userAgent =
    "WePeiYang/${CommonContext.applicationVersion} (${Build.BRAND} ${Build.PRODUCT}; Android ${Build.VERSION.SDK_INT})"

internal inline val Request.uaed: Request
    get() = newBuilder().header("User-Agent", userAgent).build()

internal object UserAgentInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(chain.request().uaed)
}