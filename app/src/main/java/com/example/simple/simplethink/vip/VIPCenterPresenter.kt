package com.example.simple.simplethink.vip

import android.util.Log
import com.example.simple.simplethink.buzzy.BuzzyCourseActivity
import com.example.simple.simplethink.buzzy.BuzzyCourseContact
import com.example.simple.simplethink.model.TotleItem
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.utils.auth.AuthInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by mobileteam on 2019/6/21.
 */
class VIPCenterPresenter(val httpResposityImpl : HttpResposityImpl, val view: VIPCenterActivity) : VIPCenterContact.Presenter {

    var buzzyCourseUrlList = ArrayList<TotleItem>()

    override fun getSubscription(auth : String){
        httpResposityImpl.getSubscription(auth).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    /*for(i in 0 until message.size){
                        var bannerURL = message[i].title_img_new
                        var title = message[i].title
                        var totleItem = TotleItem(title,bannerURL)
                        buzzyCourseUrlList.add(totleItem)
                        view.setBuzzyCourseAdapter(buzzyCourseUrlList)
                    }*/
                },{
                    error->
                    Log.e("---", "----getTotleSortfail:" + error)
                })
    }


}