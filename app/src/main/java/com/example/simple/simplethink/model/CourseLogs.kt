package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
data class CourseLogs(
        @SerializedName("unique_id") val unique_id : String?,
        @SerializedName("course_id") val course_id : Int?,
        @SerializedName("section_id") val section_id : Int?,
        @SerializedName("audio_id") val audio_id : Int?,
        @SerializedName("completed_time") val completed_time : String
): Serializable

data class PraticeRequest(
        @SerializedName("course_logs") val course_logs : List<CourseLogs>?
): Serializable

data class CreateSubRequest(
        @SerializedName("subscription_id") val subscription_id : Int?,
        @SerializedName("platform") val platform : String?
): Serializable