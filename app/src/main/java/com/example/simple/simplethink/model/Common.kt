package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
data class Common(
        @SerializedName("id") val id : Int,
        @SerializedName("title") val title : String,
        @SerializedName("price") val price : Int,
        @SerializedName("discount_price") val discount_price : Double,
        @SerializedName("unit") val unit : String,
        @SerializedName("theme_img") val theme_img : String
): Serializable