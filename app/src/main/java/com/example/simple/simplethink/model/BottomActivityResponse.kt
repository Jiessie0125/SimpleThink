package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
data class BottomActivityResponse(
        @SerializedName("imgURL") val imgURL : String,
        @SerializedName("tag") val tag : String,
        @SerializedName("lessionsID") val lessionsID: String
): Serializable