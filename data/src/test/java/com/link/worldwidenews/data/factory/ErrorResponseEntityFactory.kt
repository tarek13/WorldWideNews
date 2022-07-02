package com.link.worldwidenews.data.factory

import com.link.worldwidenews.data.entity.ErrorResponseEntity
import com.link.worldwidenews.domain.model.ErrorResponseModel


object ErrorResponseEntityFactory {
    fun generateDummyErrorResponseEntity(): ErrorResponseEntity {
        return ErrorResponseEntity(
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