package com.example.simple.simplethink.main

import com.example.simple.simplethink.model.*

/**
 * Created by 111 on 2019/7/18.
 */
interface MainContract {

    interface View {
        fun onGetSuggestedActivitySuccess(message: List<ActivityResponse>)
        fun onGetBottomActivitySuccess(message: BottomActivityResponse)
        fun onGetSuggestedCourseSuccess(message: List<SuggestedCourse>)
        fun onGetPracticeSuccess(message: Map<String, List<PracticeResponse>>?)
        fun onFailure(e: Throwable)
        fun updateVipItem(sub : SubscriptionResponse)
    }

    interface Presenter {
        fun bind(view: View)
        fun unbind()
        fun getSuggestedActivity()
        fun getBottomActivity()
        fun getSuggestedCourse()
        fun getPracticeList()
        fun getSubscription()
    }
}