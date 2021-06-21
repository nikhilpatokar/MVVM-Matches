package com.nikhilpatokar.assignment.network

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.nikhilpatokar.assignment.network.Resource.Companion.loading
import com.nikhilpatokar.assignment.network.Resource.Companion.success
import com.nikhilpatokar.assignment.network.Resource.Companion.error
import com.nikhilpatokar.assignment.utils.AppExecutors
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.LiveData

// CacheObject: Type for the Resource data. (database cache)
// RequestObject: Type for the API response. (network request)
abstract class NetworkBoundResource<CacheObject, RequestObject>(private val appExecutors: AppExecutors) {
    private val results = MediatorLiveData<Resource<CacheObject>>()
    private fun init() {

        // update LiveData for loading status
        results.value = loading<Any>("") as Resource<CacheObject>
        // observe LiveData source from local db
        val dbSource = loadFromDb()
        results.addSource(dbSource) { cacheObject ->
            results.removeSource(dbSource)
            if (shouldFetch(cacheObject)) {
                // get data from the network
                fetchFromNetwork(dbSource)
            } else {
                results.addSource(dbSource) { cacheObject ->
                    setValue(success(cacheObject)) }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<CacheObject>) {

        // update LiveData for loading status
        results.addSource(dbSource) { cacheObject -> setValue(loading(cacheObject)) }
        val apiResponse = createCall()
        results.addSource(apiResponse) { requestObjectApiResponse ->
            results.removeSource(dbSource)
            results.removeSource(apiResponse)

            when (requestObjectApiResponse) {
                is ApiResponse<*>.ApiSuccessResponse<*> -> {
                    appExecutors.diskIO().execute { // save the response to the local db
                        saveCallResult(processResponse(requestObjectApiResponse as ApiResponse<*>.ApiSuccessResponse<*>) as RequestObject)
                        appExecutors.mainThread().execute {
                            results.addSource(loadFromDb()) { cacheObject ->
                                setValue(
                                    success(cacheObject)
                                )
                            }
                        }
                    }
                }
                is ApiResponse<*>.ApiEmptyResponse<*> -> {
                    appExecutors.mainThread().execute {
                        results.addSource(loadFromDb()) { cacheObject ->
                            setValue(
                                success(cacheObject)
                            )
                        }
                    }
                }
                is ApiResponse<*>.ApiErrorResponse<*> -> {
                    results.addSource(loadFromDb()) { cacheObject ->
                        setValue(
                            error(
                                (requestObjectApiResponse as ApiResponse<*>.ApiErrorResponse<*>).errorMessage,
                                cacheObject
                            )
                        )
                    }
                }
            }
        }
    }

    private fun processResponse(response: ApiResponse<*>.ApiSuccessResponse<*>): CacheObject {
        return response.body as CacheObject
    }

    private fun setValue(newValue: Resource<CacheObject>) {
        if (results.value != newValue) {
            results.value = newValue
        }
    }

    // Called to save the result of the API response into the database.
    @WorkerThread
    protected abstract fun saveCallResult(item: RequestObject)

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract fun shouldFetch(data: CacheObject?): Boolean

    // Called to get the cached data from the database.
    @MainThread
    protected abstract fun loadFromDb(): LiveData<CacheObject>

    // Called to create the API call.
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestObject>>

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    val asLiveData: LiveData<Resource<CacheObject>>
        get() = results

    companion object {
        private const val TAG = "NetworkBoundResource"
    }

    init {
        init()
    }
}