package com.nikhilpatokar.assignment.models

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Login (
    @SerializedName("uuid")
    @Expose
    var uuid: String? = null,

    @SerializedName("username")
    @Expose
    var username: String? = null,

    @SerializedName("password")
    @Expose
    var password: String? = null,

    @SerializedName("salt")
    @Expose
    var salt: String? = null,

    @SerializedName("md5")
    @Expose
    var md5: String? = null,

    @SerializedName("sha1")
    @Expose
    var sha1: String? = null,

    @SerializedName("sha256")
    @Expose
    var sha256: String? = null
)