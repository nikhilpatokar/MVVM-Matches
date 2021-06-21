package com.nikhilpatokar.assignment.network

import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import com.nikhilpatokar.assignment.utils.LiveDataCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.nikhilpatokar.assignment.utils.Constants

object ServiceGenerator {
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client: OkHttpClient =
        Builder()
            .retryOnConnectionFailure(false)
            .addInterceptor(logging)
            .build()
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
    private val retrofit = retrofitBuilder.build()
    val personApi = retrofit.create(PersonApi::class.java)
}