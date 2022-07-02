package com.link.worldwidenews.data.entity


import com.google.gson.annotations.SerializedName

data class ErrorResponseEntity(
    @SerializedName("code")
    var code: String?=null,
    @SerializedName("message")
    var message: String?=null,
    @SerializedName("status")
    var status: String?=null
)