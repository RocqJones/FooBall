package com.zonkesoft.fooball.data_source.remote.dto

import com.google.gson.annotations.SerializedName

data class BaseApiResponse<T>(
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: T? = null
)
