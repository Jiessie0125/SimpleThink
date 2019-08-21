package com.example.simple.simplethink.main

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
        fun onGetSuggestedCourseSuccess(message: List<SuggestedCourse>)
        fun onGetPracticeSuccess()
        fun onFailure(e: Throwable)
    }

    interface Presenter {
        fun bind(view: View)
        fun unbind()
        fun getSuggestedActivity()
        fun getBottomActivity()
        fun getSuggestedCourse()
        fun getPracticeList()
    }
}