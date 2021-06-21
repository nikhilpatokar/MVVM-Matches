package com.nikhilpatokar.assignment.network

import retrofit2.http.GET
import androidx.lifecycle.LiveData
import retrofit2.http.Query

interface PersonApi {
    @GET("api")
    fun fetchPersonResult(@Query("results") results: Int): LiveData<ApiResponse<PersonResponse>>
}