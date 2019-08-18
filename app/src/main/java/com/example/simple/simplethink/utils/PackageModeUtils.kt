package com.example.simple.simplethink.utils

import com.example.simple.simplethink.model.CourseLogs
import com.example.simple.simplethink.model.CreateSubRequest
import com.example.simple.simplethink.model.PraticeRequest

object PackageModeUtils {
    fun packagePraticeRequest(unique_id : String?,course_id : Int?,section_id : Int?,audio_id : Int?,completed_time : String): PraticeRequest{
        val courseLogs = CourseLogs(unique_id,
                course_id,
                section_id,
                audio_id,
                completed_time)
        val list = ArrayList<CourseLogs>()
        list.add(courseLogs)
        val praticeRequest = PraticeRequest(
                course_logs = list
        )
        return praticeRequest
    }


}