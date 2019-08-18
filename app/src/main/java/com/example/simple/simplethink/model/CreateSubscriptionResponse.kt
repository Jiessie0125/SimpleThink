package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
data class CreateSubscriptionResponse(
        @SerializedName("order_id") val order_id : String,
        @SerializedName("alipay_price") val alipay_price : Int
): Serializable