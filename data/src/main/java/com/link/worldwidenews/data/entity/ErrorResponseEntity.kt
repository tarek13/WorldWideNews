package com.link.worldwidenews.data.entity


import com.google.gson.annotations.SerializedName

data class ErrorResponseEntity(
    @SerializedName("code")
    var code: String?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)