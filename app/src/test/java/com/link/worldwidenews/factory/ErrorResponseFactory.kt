package com.link.worldwidenews.factory

import com.link.worldwidenews.domain.model.ErrorResponseModel
import com.link.worldwidenews.model.ErrorResponse


object ErrorResponseFactory {
    fun generateDummyErrorResponse(): ErrorResponse {
        return ErrorResponse(
            "code",
            "message",
            "status"
        )
    }

    fun generateDummyErrorResponseDomain(): ErrorResponseModel {
        return ErrorResponseModel(
            "code",
            "message",
            "status"
        )
    }
}