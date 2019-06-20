package com.example.simple.simplethink.totle.fragment

import android.graphics.Bitmap
import android.util.Log
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.utils.FilesUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

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
                .subscribeOn(Schedulers.newThread())
                 .map(object :Function<ResponseBody,Bitmap> {
                     override fun apply(t: ResponseBody): Bitmap? {
                         val messageByte = t.bytes()
                        if(FilesUtils.savaBitmap(messageByte,strFileName)){
                            return FilesUtils.getItemIcon(strFileName)
                        }else {
                            return null
                        }
                     }
                 })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({message ->
                    view.getItemImage(strFileName,message)
                },{
                    error ->
                    Log.e("---","----getItemImagefail:"+error)
                })
    }
}