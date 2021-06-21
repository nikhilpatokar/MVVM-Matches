package com.nikhilpatokar.assignment.models

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Registered (
    @SerializedName("date")
    @Expose
    var date: String = "",

    @SerializedName("age")
    @Expose
    var age: Int = 0
)