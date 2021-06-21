package com.nikhilpatokar.assignment.models

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Timezone (
    @SerializedName("offset")
    @Expose
    var offset: String = "",

    @SerializedName("description")
    @Expose
    var description: String = ""
)