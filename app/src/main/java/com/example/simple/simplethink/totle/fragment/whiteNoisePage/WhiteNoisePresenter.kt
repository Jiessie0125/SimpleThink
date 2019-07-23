package com.example.simple.simplethink.totle.fragment.whiteNoisePage

import android.app.Activity
import android.util.Log
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.fragment.totlePage.TotleContact
import com.example.simple.simplethink.totle.fragment.totlePage.TotleFragment
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.LocalDataCache
import com.example.simple.simplethink.utils.URLConstant
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by jiessie on 2019/6/4.
 */
class WhiteNoisePresenter(val httpResposityImpl : HttpResposityImpl, val view: WhithNoiseFragment, val context : Activity) : WhiteNoiseContact.Presenter {

    override fun getWhiteNoise(){
        httpResposityImpl.getWhiteItem().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    view.updateView(message)
                },{
                    error->
                    Log.e("---", "----getTotleSortfail:" + error)
                })
    }
}