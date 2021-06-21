package com.nikhilpatokar.assignment.repository

import android.content.Context
import com.nikhilpatokar.assignment.network.ServiceGenerator.personApi
import com.nikhilpatokar.assignment.persistence.ResultDao
import com.nikhilpatokar.assignment.utils.AppExecutors
import androidx.lifecycle.LiveData
import com.nikhilpatokar.assignment.models.Result
import com.nikhilpatokar.assignment.network.*
import com.nikhilpatokar.assignment.persistence.PersonDatabase
import com.nikhilpatokar.assignment.utils.NetworkHelper

class ResultsRepository private constructor(val context: Context) {
    private val resultDao: ResultDao = PersonDatabase.getInstance(context)!!.resultDao
    fun updatePersonData(status: String, id: Int) {
        val appExecutors = AppExecutors.instance
        appExecutors!!.diskIO().execute { resultDao.updateStatus(status, id) }
    }

    fun searchPersonApi(noOfResults: Int): LiveData<Resource<List<Result>>> {
        return object: NetworkBoundResource<List<Result>, PersonResponse>(AppExecutors.instance!!){
            override fun saveCallResult(item: PersonResponse) {
                if (item.results?.isNotEmpty() == true) {
                    resultDao.insertResults(item.results)
                }
            }

            override fun shouldFetch(data: List<Result>?): Boolean {
                return NetworkHelper(context).isNetworkConnected()
            }

            override fun loadFromDb(): LiveData<List<Result>> {
                return resultDao.searchResults(noOfResults)
            }

            override fun createCall(): LiveData<ApiResponse<PersonResponse>> {
                return personApi.fetchPersonResult(10)
            }

        }.asLiveData
    }

    companion object {
        private const val TAG = "ResultsRepository"
        private var instance: ResultsRepository? = null
        fun getInstance(context: Context): ResultsRepository {
            if (instance == null) {
                instance = ResultsRepository(context)
            }
            return instance!!
        }
    }
}