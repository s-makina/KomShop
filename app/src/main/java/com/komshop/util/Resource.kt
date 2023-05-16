package com.komshop.util

data class Resource <out T>(val status: Status, val data: T?, val message: String?) {
    companion object{
        fun <T> success(data: T?) = Resource(Status.SUCCESS, data = data, null)

        fun <T> error(message: String?, data: T?) = Resource(Status.ERROR, data = data, message = message)

        fun <T> loading(data: T?) = Resource(Status.LOADING, data = data, null)

        fun <T> idle() = Resource(Status.IDLE, data = null, null)
    }
}

enum class Status{
    SUCCESS,
    ERROR,
    LOADING,
    IDLE
}