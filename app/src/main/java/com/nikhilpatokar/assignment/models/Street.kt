package com.nikhilpatokar.assignment.models

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Street (
    @SerializedName("number")
    @Expose
    var number: Int? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null
)