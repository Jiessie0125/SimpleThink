package com.example.simple.simplethink.main.activity

import com.example.simple.simplethink.netapi.HttpResposityImpl

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