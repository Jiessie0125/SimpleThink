package com.example.simple.simplethink.model

/**
 * Created by mobileteam on 2019/6/4.
 */
data class TotleSortResponse(
        val id : Int,
        val language : String,
        val channel : String,
        val prohibit_channel : String,
        val category_name : String,
        val image : String,
        val sequence: String,
        val status : String
)