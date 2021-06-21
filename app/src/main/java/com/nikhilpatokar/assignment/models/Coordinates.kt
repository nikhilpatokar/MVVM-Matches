package com.nikhilpatokar.assignment.models

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Coordinates (
    @SerializedName("latitude")
    @Expose
    var latitude: String = "",

    @SerializedName("longitude")
    @Expose
    var longitude: String = ""
)