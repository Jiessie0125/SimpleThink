package com.example.simple.simplethink.totle.fragment.totlePage

import android.app.Activity
import android.graphics.Bitmap
import android.util.Log
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.LocalDataCache
import com.example.simple.simplethink.utils.URLConstant.GETCOURSEIMAGE
import com.example.simple.simplethink.utils.URLConstant.GETTOTLESORT
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

/**
 * Created by jiessie on 2019/6/4.
 */
class TotlePresenter(val httpResposityImpl : HttpResposityImpl, val view: TotleFragment,val context : Activity) : TotleContact.Presenter {

    override fun getBanner(){
        httpResposityImpl.getBanner().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    view.setBanner(message)
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
                    LocalDataCache.save(message,GETTOTLESORT)
                    for (i in 0 until message.size){
                        FilesUtils.downloadImage(context, message[i].image, message[i].category_name)
                    }
                    view.setTotleIcon(message)
                   /*
                    view.getTotleSortIcon(false,message)*/
                },{
                    error->
                    Log.e("---","----getTotleSortfail:"+error)
                })
    }

    override fun getCourse(){
        httpResposityImpl.getCourseImage().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    Log.e("---","----getCourse:"+message)
                    LocalDataCache.save(message,GETCOURSEIMAGE)
                    view.setBuzzyItem(message.id)
                    view.setCourseAdapterView(false,message.courses)
                    for (i in 0 until message.courses.size){
                        FilesUtils.downloadImage(context, message.courses[i].title_img_new, message.courses[i].title)
                    }
                    view.setCourseIcon(message.courses)
                },{
                    error->
                    Log.e("---","----getCourse:"+error)
                })
    }

}