package com.example.simple.simplethink.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mobileteam on 2019/6/4.
 */
data class ScenesResponse(
        @SerializedName("id") val id : Int,
        @SerializedName("channel") val channel : String,
        @SerializedName("prohibit_channel") val prohibit_channel : String,
        @SerializedName("title") val title : String,
        @SerializedName("subtitle_new") val subtitle_new : String,
        @SerializedName("content_new") val content_new: String,
        @SerializedName("title_img_new") val title_img_new : String,
        @SerializedName("content_img_new") val content_img_new : String,
        @SerializedName("type_new") val type_new: String,
        @SerializedName("recommend") val recommend : Int,
        @SerializedName("label_img_new") val label_img_new : String,
        @SerializedName("play_count") val play_count : Int,
        @SerializedName("sequence") val sequence : Int,
        @SerializedName("section_total_count") val section_total_count: Int,
        @SerializedName("collect_count") val collect_count : Int,
        @SerializedName("sections") val sections : List<Sections>
): Serializable