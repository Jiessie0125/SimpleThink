package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
data class FirstCourseResponse(
        @SerializedName("id") val id : Int,
        @SerializedName("channel") val channel : String,
        @SerializedName("prohibit_channel") val prohibit_channel : String,
        @SerializedName("name") val name : String,
        @SerializedName("courses_id") val courses_id : String,
        @SerializedName("num") val num: Int,
        @SerializedName("sequence") val sequence : Int,
        @SerializedName("courses") val courses : List<Course>
): Serializable