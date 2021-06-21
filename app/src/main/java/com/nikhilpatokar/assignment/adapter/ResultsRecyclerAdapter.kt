package com.nikhilpatokar.assignment.adapter

import com.bumptech.glide.RequestManager
import com.bumptech.glide.util.ViewPreloadSizeProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.ListPreloader.PreloadModelProvider
import android.view.ViewGroup
import android.view.LayoutInflater
import com.nikhilpatokar.assignment.R
import android.text.TextUtils
import com.bumptech.glide.RequestBuilder
import com.nikhilpatokar.assignment.models.Result
import java.util.ArrayList

class ResultsRecyclerAdapter(
    private val mOnActionTakenListener: OnActionTakenListener,
    private val requestManager: RequestManager,
    private val preloadSizeProvider: ViewPreloadSizeProvider<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), PreloadModelProvider<String> {

    private var mResults: List<Result>

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_person_list_item, viewGroup, false)
        return ResultViewHolder(view, mOnActionTakenListener, requestManager, preloadSizeProvider)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        (viewHolder as ResultViewHolder).onBind(mResults[i])
    }

    override fun getItemCount(): Int {
        return mResults.size
    }

    fun setResults(results: List<Result>) {
        mResults = results
        notifyDataSetChanged()
    }

    fun getSelectedResult(position: Int): Result {
        return mResults[position]
    }

    override fun getPreloadItems(position: Int): List<String> {
        val url = mResults[position].picture?.medium
        return if (TextUtils.isEmpty(url)) {
            emptyList()
        } else listOf(url!!)
    }

    override fun getPreloadRequestBuilder(item: String): RequestBuilder<*> {
        return requestManager.load(item)
    }

    init {
        mResults = ArrayList()
    }
}