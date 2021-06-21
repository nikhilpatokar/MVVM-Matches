package com.nikhilpatokar.assignment.models

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Picture (
    @SerializedName("large")
    @Expose
    var large: String = "",

    @SerializedName("medium")
    @Expose
    var medium: String = "",

    @SerializedName("thumbnail")
    @Expose
    var thumbnail: String = ""
)