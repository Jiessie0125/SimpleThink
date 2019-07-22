package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(@SerializedName("username") val userName: String?,
                            @SerializedName("nickname") val nickName: String?,
                            @SerializedName("avatar") val avatar: String?,
                            @SerializedName("gender") val gender: String?,
                            @SerializedName("range_age") val ageRange: String?,
                            @SerializedName("range_location") val locationRange: String?,
                            @SerializedName("course_count") val courseCount: Int?,
                            @SerializedName("duration_count") val durationCount: String?,
                            @SerializedName("continue_day_count") val continueDay: String?)