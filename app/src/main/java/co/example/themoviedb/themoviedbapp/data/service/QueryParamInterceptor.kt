package co.example.themoviedb.themoviedbapp.data.service

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author santiagoalvarez.
 */
class QueryParamInterceptor(private val query: String, private val value: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response? {
        if (chain != null) {
            val originalRequest = chain.request()
            val httpUrl = originalRequest.url().newBuilder()
                    .addQueryParameter(query, value)
                    .build()
            return chain.proceed(originalRequest.newBuilder()
                    .url(httpUrl)
                    .build())
        }
        return null
    }
}