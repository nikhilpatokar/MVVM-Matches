package com.nikhilpatokar.assignment.network

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.nikhilpatokar.assignment.models.Info
import com.nikhilpatokar.assignment.models.Result

class PersonResponse {
    @SerializedName("results")
    @Expose
    var results: List<Result>? = null

    @SerializedName("info")
    @Expose
    var info: Info? = null
}