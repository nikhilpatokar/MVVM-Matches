package com.nikhilpatokar.assignment.network

import retrofit2.Response
import java.io.IOException

/**
 * Generic class for handling responses from Retrofit
 * @param <T>
</T> */
open class ApiResponse<T> {
    fun create(error: Throwable): ApiResponse<T> {
        return ApiErrorResponse(if (error.message == "") error.message!! else "Unknown Network Error")
    }

    fun create(response: Response<T>): ApiResponse<T> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body is PersonResponse) {
                if ((body as PersonResponse).info!!.results == 0) {
                    // query is exhausted
                    return ApiErrorResponse("QUERY_EXHAUSTED")
                }
            }
            if (body == null || response.code() == 204) { // 204 is empty response
                ApiEmptyResponse()
            } else {
                ApiSuccessResponse(body)
            }
        } else {
            var errorMsg: String = ""
            errorMsg = try {
                response.errorBody()!!.string()
            } catch (e: IOException) {
                e.printStackTrace()
                response.message()
            }
            ApiErrorResponse(errorMsg)
        }
    }

    /**
     * Generic success response from api
     * @param <T>
    </T> */
    inner class ApiSuccessResponse<T> internal constructor(val body: T) : ApiResponse<T>()

    /**
     * Generic Error response from API
     * @param <T>
    </T> */
    inner class ApiErrorResponse<T> internal constructor(val errorMessage: String) :
        ApiResponse<T>()

    /**
     * separate class for HTTP 204 resposes so that we can make ApiSuccessResponse's body non-null.
     */
    inner class ApiEmptyResponse<T> : ApiResponse<T>()
    companion object {
        private const val TAG = "ApiResponse"
    }
}