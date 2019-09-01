package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
data class PayResponse(
        @SerializedName("text") val text : String,
        @SerializedName("code") val code : String
): Serializable