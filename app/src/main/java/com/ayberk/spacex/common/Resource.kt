package com.ayberk.spacex.common

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Fail(val message: String) : Resource<Nothing>()
    data class Error(val throwable: Throwable?) : Resource<Nothing>()
}