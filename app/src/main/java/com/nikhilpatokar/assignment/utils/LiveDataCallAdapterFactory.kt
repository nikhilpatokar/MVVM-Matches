package com.nikhilpatokar.assignment.utils

import retrofit2.CallAdapter
import retrofit2.Retrofit
import androidx.lifecycle.LiveData
import com.nikhilpatokar.assignment.network.ApiResponse
import com.nikhilpatokar.assignment.utils.LiveDataCallAdapter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType: Type = getRawType(observableType)
        require(!(rawObservableType !== ApiResponse::class.java)) { "Type must be a defined resource" }

        require(observableType is ParameterizedType) { "resource must be parameterized" }
        val bodyType = getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<Type>(bodyType)
    }
}