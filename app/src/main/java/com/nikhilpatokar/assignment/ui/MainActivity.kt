package com.nikhilpatokar.assignment.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.nikhilpatokar.assignment.R
import com.nikhilpatokar.assignment.adapter.OnActionTakenListener
import com.nikhilpatokar.assignment.adapter.ResultsRecyclerAdapter
import com.nikhilpatokar.assignment.models.Result
import com.nikhilpatokar.assignment.network.Resource
import com.nikhilpatokar.assignment.viewmodels.PersonListViewModel

class MainActivity : BaseActivity(), OnActionTakenListener {
    private lateinit var mPersonListViewModel: PersonListViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: ResultsRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPersonListViewModel = ViewModelProviders.of(this).get(PersonListViewModel::class.java)
        initRecyclerView()
        subscribeObservers()
        mPersonListViewModel?.fetchPersonData()
    }

    private fun subscribeObservers() {

        mPersonListViewModel.personResults.observe(this,
            Observer<Resource<List<Result>>> { listResource ->
                if (listResource != null) {
                    if (listResource.data != null) {
                        when (listResource.status) {
                            Resource.Status.LOADING -> {
                                showProgressBar(true)
                            }
                            Resource.Status.ERROR -> {
                                showToast(listResource.message)
                                showProgressBar(false)
                            }
                            Resource.Status.SUCCESS -> {
                                mAdapter.setResults(listResource.data)
                                showProgressBar(false)
                            }
                        }
                    }
                }
            })
    }

    private fun initRecyclerView() {
        mRecyclerView = findViewById(R.id.person_list)
        val viewPreloader = ViewPreloadSizeProvider<String>()
        mAdapter = ResultsRecyclerAdapter(this, initGlide(), viewPreloader)
        mRecyclerView.setLayoutManager(LinearLayoutManager(this))
        val preloader = RecyclerViewPreloader(
            Glide.with(this),
            mAdapter,
            viewPreloader,
            30
        )
        mRecyclerView.addOnScrollListener(preloader)
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    return;
                }
                if (!mRecyclerView.canScrollVertically(1)) {
                    mPersonListViewModel.searchNextPerson()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dx == 0 && dy == 0) {
                    mPersonListViewModel.searchNextPerson()
                }
            }
        })
        mRecyclerView.setAdapter(mAdapter)
    }

    private fun initGlide(): RequestManager {
        val options = RequestOptions()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
        return Glide.with(this)
            .setDefaultRequestOptions(options)
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onAcceptClick(position: Int) {
        val result: Result = mAdapter.getSelectedResult(position)
        mPersonListViewModel.updatePersonData(
            "Accepted",
            result._id
        )
    }

    override fun onRejectClick(position: Int) {
        val result: Result = mAdapter.getSelectedResult(position)
        mPersonListViewModel.updatePersonData("Rejected", result._id)
    }
}