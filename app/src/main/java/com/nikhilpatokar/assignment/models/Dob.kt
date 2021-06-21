package com.nikhilpatokar.assignment.models

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Dob (
    @SerializedName("date")
    @Expose
    var date: String = "",

    @SerializedName("age")
    @Expose
    var age: Int = 0
)