package com.nikhilpatokar.assignment.ui

import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.nikhilpatokar.assignment.R

abstract class BaseActivity : AppCompatActivity() {
    private lateinit var mProgressBar: ProgressBar
    override fun setContentView(layoutResID: Int) {
        val constraintLayout =
            layoutInflater.inflate(R.layout.activity_base, null) as ConstraintLayout
        val frameLayout = constraintLayout.findViewById<FrameLayout>(R.id.activity_content)
        mProgressBar = constraintLayout.findViewById(R.id.progress_bar)
        layoutInflater.inflate(layoutResID, frameLayout, true)
        super.setContentView(constraintLayout)
    }

    fun showProgressBar(visibility: Boolean) {
        mProgressBar!!.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
    }

    fun showToast(message: String?) {
        Toast.makeText(this@BaseActivity, message, Toast.LENGTH_LONG).show()
    }
}