package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
data class OrderWXResponse(
        @SerializedName("status_code") val status_code : Int,
        @SerializedName("appid") val appid : String,
        @SerializedName("partnerid") val partnerid : String,
        @SerializedName("prepayid") val prepayid : String,
        @SerializedName("package") val packageValue : String,
        @SerializedName("noncestr") val noncestr : String,
        @SerializedName("timestamp") val timestamp : Int,
        @SerializedName("sign") val sign : String
): Serializable