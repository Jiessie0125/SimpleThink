package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName

/**
 * Created by jiessie on 2019/7/30.
 */
data class ErrorResponse(@SerializedName("error_code") val errorCode: Int,
                         @SerializedName("message") val message: String)