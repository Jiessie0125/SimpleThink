package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
data class TotleSortResponse(
        @SerializedName("id") val id : Int,
        @SerializedName("language") val language : String,
        @SerializedName("channel") val channel : String,
        @SerializedName("prohibit_channel") val prohibit_channel : String,
        @SerializedName("category_name") val category_name : String,
        @SerializedName("image") val image : String,
        @SerializedName("sequence") val sequence: Int,
        @SerializedName("status") val status : String
): Serializable