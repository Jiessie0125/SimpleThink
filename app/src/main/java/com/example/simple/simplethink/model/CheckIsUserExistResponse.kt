package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName

/**
 * Created by 111 on 2019/7/18.
 */
data class CheckIsUserExistResponse(
        @SerializedName("exist") val exist: String?)