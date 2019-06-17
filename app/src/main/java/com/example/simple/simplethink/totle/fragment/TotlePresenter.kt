package com.example.simple.simplethink.totle.fragment

import android.os.Environment
import android.util.Log
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.utils.FilesUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

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
                    Log.e("---","----fail:"+error)
                })
    }

    override fun getItemImage(url : String,strFileName : String){
         httpResposityImpl.getItemImage(url).subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    FilesUtils.savaBitmap(message,strFileName)
                    Log.e("---","----success:"+message)
                    //view.getItemImage(message!!)
                },{
                    error ->
                    Log.e("---","----fail:"+error)
                })
    }
}