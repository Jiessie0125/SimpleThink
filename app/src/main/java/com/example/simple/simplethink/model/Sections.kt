package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
data class Sections(
        @SerializedName("id") val id : Int,
        @SerializedName("title") val title : String,
        @SerializedName("title_img") val title_img : String,
        @SerializedName("course_id") val course_id : Int,
        @SerializedName("sequence") val sequence : Int,
        @SerializedName("free") val free: String,
        @SerializedName("main_duration") val main_duration : Int,
        @SerializedName("url") val url : String,
        @SerializedName("audio_id") val audio_id: Int
): Serializable