package com.example.simple.simplethink.main.activity

import android.util.Log
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.vip.VIPCenterActivity
import com.example.simple.simplethink.vip.VIPCenterContact
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by mobileteam on 2019/6/21.
 */
class PraticePresenter( val view: PraticeActivity) : PraticeContact.Presenter {
    val httpResposityImpl = HttpResposityImpl()
    override fun getCourseLogs(){
//        httpResposityImpl.getCourseLogs().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map { result -> result }
//                .subscribe({message ->
//                    view.updateVipItem(message)
//                },{
//                    error->
//                    Log.e("---", "----getTotleSortfail:" + error)
//                })
    }


}