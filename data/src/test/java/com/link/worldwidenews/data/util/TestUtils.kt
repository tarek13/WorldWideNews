package com.link.worldwidenews.data.util

import com.google.gson.Gson
import com.link.worldwidenews.data.entity.ErrorResponseEntity

object TestUtils {
    fun getJson(fileName: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)

        val out = StringBuilder()
        inputStream?.bufferedReader()?.useLines { lines ->
            lines.forEach { line ->
                out.append(line)
            }
        }

        return out.toString()
    }

    fun <T> getEntityFromJson(json: String, className: Class<T>): T? {
        val gson = Gson()
        return gson.fromJson(json, className)
    }

    fun handleErrorResponse(errorBody: String?): ErrorResponseEntity? {
        val error: ErrorResponseEntity?
        try {
            val gson = Gson()

            error = gson.fromJson(errorBody, ErrorResponseEntity::class.java)
        } catch (e: Exception) {
            return ErrorResponseEntity()
        }
        return error
    }
}