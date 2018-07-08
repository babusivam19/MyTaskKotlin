package com.babu.mytaskkotlin.data.model

import android.util.Log

/**
 * Created by Babu on 7/8/2018.
 */
class Response private constructor(status: Status, var data: Any?) {
    var status: Status? = status

    enum class Status {
        SUCCESS, FAIL
    }

    companion object {
        fun success(data: Any?): Response {
            Log.d("response--->", "Success")
            return Response(Status.SUCCESS, data)
        }

        fun error(data: Any?, error: Throwable?): Response {
            Log.d("response--->", "Error")
            return Response(Status.FAIL, data)
        }
    }

}