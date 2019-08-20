package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mobileteam on 2019/8/19.
 */

data class PracticeAllResponse(
        val result: List<PracticeListResponse>
) : Serializable

data class PracticeListResponse(
        val practice: List<PracticeResponse>
) : Serializable

data class PracticeResponse(
        @SerializedName("unique_id") val unique_id: String,
        @SerializedName("date_time") val date_time: String,
        @SerializedName("course") val audio_id: PracticeCourseResponse,
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



