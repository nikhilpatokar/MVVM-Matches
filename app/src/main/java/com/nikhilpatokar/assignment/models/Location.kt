package com.nikhilpatokar.assignment.models

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.nikhilpatokar.assignment.models.Street
import com.nikhilpatokar.assignment.models.Coordinates
import com.nikhilpatokar.assignment.models.Timezone

data class Location (
    @SerializedName("street")
    @Expose
    @Embedded
    var street: Street? = null,

    @SerializedName("city")
    @Expose
    var city: String? = null,

    @SerializedName("state")
    @Expose
    var state: String? = null,

    @SerializedName("country")
    @Expose
    var country: String? = null,

    @SerializedName("postcode")
    @Expose
    var postcode: String? = null,

    @SerializedName("coordinates")
    @Expose
    @Embedded
    var coordinates: Coordinates? = null,

    @SerializedName("timezone")
    @Expose
    @Embedded
    var timezone: Timezone? = null
)