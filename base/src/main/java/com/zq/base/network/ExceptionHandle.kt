package com.zq.base.network

import android.net.ParseException
import android.util.Log
import android.util.MalformedJsonException
import com.google.gson.JsonParseException
import com.zq.base.network.ERROR
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException

/**

 */
object ExceptionHandle {

    fun handleException(e: Throwable): ResponseThrowable {
        val ex: ResponseThrowable
        if (e is ResponseThrowable) {
            ex = e
        } else if (e is HttpException) {
            if (e.code() == 401) {
                ex = ResponseThrowable(ERROR.TOKNE_ERROR, e)
            } else {
                ex = ResponseThrowable(ERROR.HTTP_ERROR, e)
            }
        } else if (e is JsonParseException
            || e is JSONException
            || e is ParseException || e is MalformedJsonException
        ) {
            ex = ResponseThrowable(ERROR.PARSE_ERROR, e)
        } else if (e is ConnectException) {
            ex = ResponseThrowable(ERROR.NETWORD_ERROR, e)
        } else if (e is javax.net.ssl.SSLException) {
            ex = ResponseThrowable(ERROR.SSL_ERROR, e)
        } else if (e is java.net.SocketTimeoutException) {
            ex = ResponseThrowable(ERROR.TIMEOUT_ERROR, e)
        } else if (e is java.net.UnknownHostException) {
            ex = ResponseThrowable(ERROR.TIMEOUT_ERROR, e)
        } else {
            ex = if (!e.message.isNullOrEmpty()) ResponseThrowable(1000, e.message!!, e)
            else ResponseThrowable(ERROR.UNKNOWN, e)
        }
        return ex
    }
}