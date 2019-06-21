package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
data class BannerResponse(
        @SerializedName("id") val id : Int,
        @SerializedName("channel") val channel : String,
        @SerializedName("prohibit_channel") val prohibit_channel : String,
        @SerializedName("imgURL") val imgURL : String,
        @SerializedName("tag") val tag : String,
        @SerializedName("lessionsID") val lessionsID: String,
        @SerializedName("sequence") val sequence : Int,
        @SerializedName("start_time") val start_time : String,
        @SerializedName("end_time") val end_time: String
): Serializable