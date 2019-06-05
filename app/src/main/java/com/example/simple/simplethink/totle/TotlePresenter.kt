package com.example.simple.simplethink.totle

import android.util.Log
import com.example.simple.simplethink.netapi.HttpResposityImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by jiessie on 2019/6/4.
 */
class TotlePresenter(val httpResposityImpl : HttpResposityImpl) : TotleContact.Presenter {

    override fun getTotleSort(){
        val disposable = httpResposityImpl.getTotleSort().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    Log.e("---","----success:"+message)
                },{
                    error->
                    Log.e("---","----fail:"+error)
                })
    }
}