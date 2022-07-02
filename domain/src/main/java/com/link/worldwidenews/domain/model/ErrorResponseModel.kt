package com.link.worldwidenews.domain.model


data class ErrorResponseModel(
    var code: String?=null,
    var message: String?=null,
    var status: String?=null
)