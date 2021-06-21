package com.nikhilpatokar.assignment.models

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Id (
    @SerializedName("name")
    @Expose
    var name: String = "",

    @SerializedName("value")
    @Expose
    var value: String = ""
)