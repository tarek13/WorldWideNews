package com.link.worldwidenews.utils.mapper

import com.link.worldwidenews.domain.model.ErrorResponseModel
import com.link.worldwidenews.model.ErrorResponse

fun ErrorResponseModel?.mapToView(): ErrorResponse? {
    return this?.run {
        ErrorResponse(
            code,
            message,
            status,
        )
    }
}


