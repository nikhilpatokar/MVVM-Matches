package com.nikhilpatokar.assignment.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import com.nikhilpatokar.assignment.repository.ResultsRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nikhilpatokar.assignment.models.Result
import com.nikhilpatokar.assignment.network.Resource

class PersonListViewModel(application: Application) : AndroidViewModel(application) {
    private var noOfPersons = 10
    private val results = MediatorLiveData<Resource<List<Result>>>()
    private val resultRepository: ResultsRepository

    // query extras
    private var isQueryExhausted = false
    private var isPerformingQuery = false
    private var cancelRequest = false
    private var requestStartTime: Long = 0
    val personResults: LiveData<Resource<List<Result>>>
        get() = results

    fun fetchPersonData() {
        executeSearch()
    }

    fun updatePersonData(status: String, id: Int) {
        resultRepository.updatePersonData(status, id)
    }

    private fun executeSearch() {
        requestStartTime = System.currentTimeMillis()
        cancelRequest = false
        val repositorySource = resultRepository.searchPersonApi(noOfPersons)
        results.addSource(repositorySource, Observer<Resource<List<Result>>> { listResource ->
            if (!cancelRequest) {
                if (listResource != null) {
                    if (listResource.status === Resource.Status.SUCCESS) {
                        isPerformingQuery = false
                        if (listResource.data != null) {
                            if (listResource.data.isEmpty()) {
                                results.value = Resource(
                                    Resource.Status.ERROR,
                                    listResource.data,
                                    QUERY_EXHAUSTED
                                )
                                isQueryExhausted = true
                            }
                        }
                        results.removeSource(repositorySource)
                    } else if (listResource.status === Resource.Status.ERROR) {
                        isPerformingQuery = false
                        if (listResource.message == QUERY_EXHAUSTED) {
                            isQueryExhausted = true
                        }
                        results.removeSource(repositorySource)
                    }
                    results.setValue(listResource)
                } else {
                    results.removeSource(repositorySource)
                }
                isPerformingQuery = false
            } else {
                results.removeSource(repositorySource)
            }
            isPerformingQuery = false
        })
    }

    fun searchNextPerson() {
        if (!isPerformingQuery) {
            isPerformingQuery = true
            noOfPersons += 10
            executeSearch()
        }
    }

    companion object {
        private const val TAG = "PersonListViewModel"
        const val QUERY_EXHAUSTED = "No more results."
    }

    init {
        resultRepository = ResultsRepository.getInstance(application)
    }
}