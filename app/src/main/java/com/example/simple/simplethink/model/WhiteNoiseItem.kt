package com.example.simple.simplethink.model

import android.net.Uri
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
data class WhiteNoiseItem(
        @SerializedName("id") val id : Int?,
        @SerializedName("title") val title : String,
        @SerializedName("img") val img : Int,
        @SerializedName("imgSelected") val imgSelected : Int,
        @SerializedName("url") val url : String,
        @SerializedName("sequence") val sequence : String
): Serializable