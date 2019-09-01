package com.example.simple.simplethink.vip

import com.example.simple.simplethink.model.*

/**
 * Created by mobileteam on 2019/6/21.
 */
class VIPCenterContact {

    interface Presenter{
        fun getSubscription()
        fun createSubscription(params: CreateSubRequest)
        fun wechatPay(orderId : String)
        fun aliPay(orderId : String)
        fun confirmWechatOrder(orderId : String?)
        fun confirmAlipayOrder(orderId : String?)
    }

    interface View{
        fun updateVipItem(sub : SubscriptionResponse)
        fun sendPreWxPay(message: OrderWXResponse)
        fun sendAliPay(message: OrderAliPayResponse)
        fun showPayDialog(orderId : String)

    }
}