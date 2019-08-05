package com.example.simple.simplethink.main

import android.app.Activity
import com.example.simple.simplethink.model.ActivityResponse
import com.example.simple.simplethink.model.BottomActivityResponse
import com.example.simple.simplethink.model.SuggestedCourse

/**
 * Created by 111 on 2019/7/18.
 */
interface MainContract {

    interface View {
        fun onGetSuggestedActivitySuccess(message: List<ActivityResponse>)
        fun onGetBottomActivitySuccess(message: BottomActivityResponse)
        fun onGtSuggestedCourseSuccess(message: List<SuggestedCourse>)
        fun onFailure(e: Throwable)

    }

    interface Presenter {
        fun bind(view: View)
        fun unbind()
        fun getSuggestedActivity(activity: Activity)
        fun getBottomActivity()
        fun getSuggestedCourse(activity: Activity)
    }
}