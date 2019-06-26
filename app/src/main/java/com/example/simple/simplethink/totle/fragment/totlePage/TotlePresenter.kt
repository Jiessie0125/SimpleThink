package com.example.simple.simplethink.totle.fragment.totlePage

import android.graphics.Bitmap
import android.util.Log
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.fragment.totlePage.TotleContact
import com.example.simple.simplethink.totle.fragment.totlePage.TotleFragment
import com.example.simple.simplethink.utils.FilesUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

/**
 * Created by jiessie on 2019/6/4.
 */
class TotlePresenter(val httpResposityImpl : HttpResposityImpl, val view: TotleFragment) : TotleContact.Presenter {

    override fun getBanner(){
        httpResposityImpl.getBanner().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    var bannerUrlList = ArrayList<String>()
                    for(i in 0 until message.size){
                        var bannerURL = message[i].imgURL
                        bannerUrlList.add(bannerURL)
                    }
                    view.setBanner(bannerUrlList)
                },{
                    error->
                    Log.e("---","----getTotleSortfail:"+error)
                })
    }


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
                        if(FilesUtils.savaBitmap(messageByte,strFileName,"png")){
                            return FilesUtils.getItemIcon(strFileName,"png")
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

    override fun getCourse(){
        httpResposityImpl.getCourseImage().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    Log.e("---","----getCourse:"+message)
                    view.setBuzzyItem(message.id)
                    view.setCourseAdapterView(message.courses)
                },{
                    error->
                    Log.e("---","----getCourse:"+error)
                })
    }

    override fun getCourseImage(url : String,strFileName : String) {
        httpResposityImpl.getCourseImageItem(url).subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .map(object :Function<ResponseBody,Bitmap> {
                    override fun apply(t: ResponseBody): Bitmap? {
                        val messageByte = t.bytes()
                        if(FilesUtils.savaBitmap(messageByte,strFileName,"jpg")){
                            return FilesUtils.getItemIcon(strFileName,"jpg")
                        }else {
                            return null
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({message ->
                    view.getCourseImageView(strFileName,message)
                },{
                    error ->
                    Log.e("---","----getItemImagefail:"+error)
                })
    }
}