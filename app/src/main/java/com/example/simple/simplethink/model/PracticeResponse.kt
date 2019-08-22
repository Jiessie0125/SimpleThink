package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mobileteam on 2019/8/21.
 */
data class PracticeResponse (
        @SerializedName("unique_id") val unique_id: String,
        @SerializedName("completed_time") val completed_time: String,
        @SerializedName("log_type") val log_type: String,
        @SerializedName("id") val id: Int,
        @SerializedName("completed") val completed: String,
        @SerializedName("audio_id") val audio_id: String,
        @SerializedName("course") val course: PracticeCourseResponse,
        @SerializedName("section") val section: PracticeSectionResponse
) : Serializable

data class PracticeCourseResponse(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("subtitle") val subtitle: String,
        @SerializedName("content") val content: String
) : Serializable

data class PracticeSectionResponse(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String
) : Serializable