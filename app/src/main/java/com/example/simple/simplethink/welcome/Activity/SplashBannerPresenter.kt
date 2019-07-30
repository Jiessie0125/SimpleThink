package com.example.simple.simplethink.welcome.Activity

import android.util.Log
import com.example.simple.simplethink.netapi.HttpResposityImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by mobileteam on 2019/7/15.
 */
class SplashBannerPresenter(private val httpResposityImpl : HttpResposityImpl, val view: WelcomeActivity) : SplashContract.Presenter {
    override fun getSplashBanner() {
        httpResposityImpl.getSplashBanner().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    view.initSplashBannerData(message)
                },{
                    error->
                    view.onFailure(error)
                })
    }


}