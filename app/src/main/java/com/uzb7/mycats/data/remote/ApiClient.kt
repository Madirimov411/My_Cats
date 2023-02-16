package com.uzb7.mycats.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val isTester = true
    private val SERVER_DEVELOPMENT = "https://api.thecatapi.com/v1/"
    private val SERVER_PRODUCTION = "https://api.thecatapi.com/v1/"

    private fun baseURL(): String {
        if (isTester) return SERVER_DEVELOPMENT
        return SERVER_PRODUCTION
    }

    private val client = getClient()

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val builder = chain.request().newBuilder()
            builder.addHeader(
                "x-api-key",
                "live_iJK9W0svogqUozU1JdpKqQNeclp92SG7jqoqflWWhr8SbNuoJegmsQ1WWSyXszvB"
            )
            chain.proceed(builder.build())
        }).build()

    }

    private val retrofit = getRetrofitClient(client)

    private fun getRetrofitClient(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(baseURL())
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()
    }

    val apiService= retrofit.create(ApiService::class.java)

}