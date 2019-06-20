package com.example.simple.simplethink.totle.fragment

import android.util.Log
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.utils.FilesUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by jiessie on 2019/6/4.
 */
class TotlePresenter(val httpResposityImpl : HttpResposityImpl, val view: TotleFragment) : TotleContact.Presenter {

    override fun getTotleSort(){
       httpResposityImpl.getTotleSort().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    view.getTotleSortIcon(message)
                },{
                    error->
                    Log.e("---","----getTotleSortfail:"+error)
                })
    }

    override fun getItemImage(url : String,strFileName : String){
         httpResposityImpl.getItemImage(url).subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    val messageByte = message.bytes()
                    FilesUtils.savaBitmap(messageByte,strFileName)
                    view.getItemImage(strFileName)
                },{
                    error ->
                    Log.e("---","----getItemImagefail:"+error)
                })
    }
}