package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
data class SubscriptionResponse(
        @SerializedName("common") val common : List<Common>,
        @SerializedName("user") val user : VipUser
): Serializable