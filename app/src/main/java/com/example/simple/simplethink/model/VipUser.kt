package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
data class VipUser(
        @SerializedName("start_at") val start_at : String,
        @SerializedName("end_at") val end_at : String,
        @SerializedName("remain_second") val remain_second : Int
): Serializable