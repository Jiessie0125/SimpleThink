package com.example.simple.simplethink.main.activity

import com.example.simple.simplethink.model.SubscriptionResponse

/**
 * Created by mobileteam on 2019/6/21.
 */
class PraticeContact {

    interface Presenter{
//        fun getCourseLogs()
    }

    interface View{
        fun updateVipItem(sub : SubscriptionResponse)
    }
}