package com.link.worldwidenews.utils

import com.google.gson.Gson

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


}