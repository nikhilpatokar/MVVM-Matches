package com.nikhilpatokar.assignment.adapter

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.util.ViewPreloadSizeProvider
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.nikhilpatokar.assignment.R
import com.nikhilpatokar.assignment.models.Result

class ResultViewHolder(
    itemView: View,
    private val mOnActionTakenListener: OnActionTakenListener,
    private val requestManager: RequestManager,
    private val viewPreloadSizeProvider: ViewPreloadSizeProvider<*>
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val accept: TextView
    private val reject: TextView
    private val status: TextView
    private val name: TextView
    private val age: TextView
    private val location: TextView
    private val image: ImageView
    fun onBind(result: Result) {
        requestManager.load(result.picture.large).circleCrop().into(image)
        name.text = """${result.name.first} ${result.name.last}"""
        if (result.dob.age != 0) {
            age.text = result.dob.age.toString()
        }
        location.text = result.location.city + ", " + result.location.country
        when {
            result.status.equals("none", ignoreCase = true) -> {
                accept.visibility = View.VISIBLE
                reject.visibility = View.VISIBLE
                status.visibility = View.GONE
            }
            result.status.equals("Accepted", ignoreCase = true) -> {
                accept.visibility = View.GONE
                reject.visibility = View.GONE
                status.visibility = View.VISIBLE
                status.setText(R.string.member_accepted_message)
            }
            result.status.equals("Rejected", ignoreCase = true) -> {
                accept.visibility = View.GONE
                reject.visibility = View.GONE
                status.visibility = View.VISIBLE
                status.setText(R.string.member_declined_message)
            }
        }
        viewPreloadSizeProvider.setView(image)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.accept_button -> {
                accept.visibility = View.GONE
                reject.visibility = View.GONE
                status.visibility = View.VISIBLE
                status.setText(R.string.member_accepted_message)
                mOnActionTakenListener.onAcceptClick(adapterPosition)
            }
            R.id.reject_button -> {
                accept.visibility = View.GONE
                reject.visibility = View.GONE
                status.visibility = View.VISIBLE
                status.setText(R.string.member_declined_message)
                mOnActionTakenListener.onRejectClick(adapterPosition)
            }
        }
    }

    init {
        name = itemView.findViewById(R.id.person_name)
        age = itemView.findViewById(R.id.person_age)
        location = itemView.findViewById(R.id.person_location)
        image = itemView.findViewById(R.id.person_image)
        status = itemView.findViewById(R.id.status)
        accept = itemView.findViewById(R.id.accept_button)
        reject = itemView.findViewById(R.id.reject_button)
        accept.setOnClickListener(this)
        reject.setOnClickListener(this)
    }
}