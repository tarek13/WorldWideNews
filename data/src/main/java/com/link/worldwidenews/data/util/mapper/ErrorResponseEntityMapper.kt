package com.link.worldwidenews.data.util.mapper.error

import com.link.worldwidenews.data.entity.ErrorResponseEntity
import com.link.worldwidenews.domain.model.ErrorResponseModel


fun ErrorResponseEntity?.mapToDomain(): ErrorResponseModel? {
    return this?.run {
        ErrorResponseModel(
            code,
            message,
            status,
        )
    }
}


