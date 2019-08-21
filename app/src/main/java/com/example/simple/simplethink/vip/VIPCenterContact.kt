package com.example.simple.simplethink.vip

import com.example.simple.simplethink.model.*

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
        fun sendPreWxPay(message: OrderWXResponse)
        fun sendAliPay(message: OrderAliPayResponse)
    }
}