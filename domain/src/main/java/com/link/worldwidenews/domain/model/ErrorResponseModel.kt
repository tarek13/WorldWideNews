package com.link.worldwidenews.domain.model


data class ErrorResponseModel(
    var code: String?,
    var message: String?,
    var status: String?
)