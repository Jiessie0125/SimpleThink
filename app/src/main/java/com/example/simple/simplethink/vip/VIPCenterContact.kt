package com.example.simple.simplethink.vip

import com.example.simple.simplethink.model.BuzzyCourseResponse
import com.example.simple.simplethink.model.CreateSubRequest
import com.example.simple.simplethink.model.SubscriptionResponse

/**
 * Created by mobileteam on 2019/6/21.
 */
class VIPCenterContact {

    interface Presenter{
        fun getSubscription()
        fun createSubscription(params: CreateSubRequest)
    }

    interface View{
        fun updateVipItem(sub : SubscriptionResponse)
    }
}