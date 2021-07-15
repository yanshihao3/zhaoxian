// -*- Mode: java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
package com.zq.base.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import java.lang.reflect.Type

object GsonUtils {
    const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
    val localGson = createLocalGson()
    private val sRemoteGson = createRemoteGson()
    private fun createLocalGsonBuilder(): GsonBuilder {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setLenient()
        gsonBuilder.setDateFormat(DATE_FORMAT)
        return gsonBuilder
    }

    private fun createLocalGson(): Gson {
        return createLocalGsonBuilder().create()
    }

    private fun createRemoteGson(): Gson {
        return createLocalGsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Throws(JsonSyntaxException::class)
    fun <T> fromLocalJson(json: String?, clazz: Class<T>?): T? {
        return try {
            localGson.fromJson(json, clazz)
        } catch (e: JsonSyntaxException) {
            null
        }
    }

    fun <T> fromLocalJson(json: String?, typeOfT: Type?): T {
        return localGson.fromJson(json, typeOfT)
    }

    fun toJson(src: Any?): String {
        return localGson.toJson(src)
    }

    fun toRemoteJson(src: Any?): String {
        return sRemoteGson.toJson(src)
    }
}