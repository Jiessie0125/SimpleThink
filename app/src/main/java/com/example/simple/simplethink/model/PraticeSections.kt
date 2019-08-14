package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
data class PraticeSections(
        @SerializedName("id") val id : Int,
        @SerializedName("course_id") val course_id : Int,
        @SerializedName("audio_id") val audio_id: Int
): Serializable