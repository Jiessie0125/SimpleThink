package com.example.simple.simplethink.vip

import android.util.Log
import com.example.simple.simplethink.buzzy.BuzzyCourseActivity
import com.example.simple.simplethink.buzzy.BuzzyCourseContact
import com.example.simple.simplethink.model.CreateSubRequest
import com.example.simple.simplethink.model.OrderWXResponse
import com.example.simple.simplethink.model.TotleItem
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.utils.auth.AuthInstance
import com.google.gson.GsonBuilder
import com.tencent.mm.opensdk.modelpay.PayReq
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * Created by mobileteam on 2019/6/21.
 */
class VIPCenterPresenter(val httpResposityImpl : HttpResposityImpl, val view: VIPCenterActivity) : VIPCenterContact.Presenter {

    override fun getSubscription(){
        httpResposityImpl.getSubscription().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    view.updateVipItem(message)
                },{
                    error->
                    Log.e("---", "----getTotleSortfail:" + error)
                })
    }

    override fun createSubscription(params: CreateSubRequest){
        val body = RequestBody.create(MediaType.parse("application/json"), GsonBuilder()
                .disableHtmlEscaping()
                .create().toJson(params))
        httpResposityImpl.createSubscription(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                   // prePay(message.order_id)
                    Log.e("---", "----message:" + message)
                },{
                    error->
                    Log.e("---", "----getTotleSortfail:" + error)
                })
    }

    private fun prePay(orderId : String){
        httpResposityImpl.wxOrder(orderId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    view.sendPreWxPay(message)
                    Log.e("---", "----message:" + message)
                },{
                    error->
                    Log.e("---", "----getTotleSortfail:" + error)
                })
    }

    private fun aliPay(orderId : String){
        httpResposityImpl.aliOrder(orderId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    view.sendAliPay(message)
                    Log.e("---", "----message:" + message)
                },{
                    error->
                    Log.e("---", "----getTotleSortfail:" + error)
                })
    }


}