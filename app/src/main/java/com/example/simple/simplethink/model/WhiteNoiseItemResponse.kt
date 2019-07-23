package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
data class WhiteNoiseItemResponse(
        @SerializedName("id") val id : Int?,
        @SerializedName("title") val title : String,
        @SerializedName("img") val img : String,
        @SerializedName("url") val url : String,
        @SerializedName("sequence") val sequence : String
): Serializable