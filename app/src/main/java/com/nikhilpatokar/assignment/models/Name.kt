package com.nikhilpatokar.assignment.models

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Name(
    @SerializedName("title")
    @Expose
    var title: String = "",

    @SerializedName("first")
    @Expose
    var first: String = "",

    @SerializedName("last")
    @Expose
    var last: String = ""
)