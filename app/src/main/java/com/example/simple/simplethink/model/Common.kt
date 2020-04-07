package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
data class Common(
        @SerializedName("id") val id : Int?,
        @SerializedName("title") val title : String?,
        @SerializedName("type") val type : String?,
        @SerializedName("unit") val unit : String?,
        @SerializedName("price") val price : Int?,
        @SerializedName("discount_price") val discount_price : Double?,
        @SerializedName("theme_img") val theme_img : String?,
        @SerializedName("description_img") val description_img : String?,
        @SerializedName("InAppID") val InAppID : String?,
        @SerializedName("start_date") val start_date : String?,
        @SerializedName("end_date") val end_date : String?,
        @SerializedName("status") val status : String?,
        @SerializedName("remain_days") val remain_days : String?
): Serializable