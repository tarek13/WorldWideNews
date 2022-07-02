package com.link.worldwidenews.utils.helper

import com.link.worldwidenews.R
import com.link.worldwidenews.domain.util.AppConstants


object ErrorMessageHelper {


    fun showGeneralErrorMessage(errorCode: Int): Int {
        when (errorCode) {
            AppConstants.ERROR_CODE_NOT_CONNECTED_TO_INTERNET -> return R.string.not_connected_to_internet
            AppConstants.ERROR_CODE_TIME_OUT -> return R.string.error_msg_timeout
            AppConstants.ERROR_CODE_LOAD_DATA -> return R.string.error_load_data
        }
        return errorCode
    }
}