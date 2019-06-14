package com.example.simple.simplethink.totle.fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.utils.FileUtils
import com.example.simple.simplethink.utils.FileUtils.APP_IMAGE_DIR
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.io.File

/**
 * Created by jiessie on 2019/6/4.
 */
class TotlePresenter(val httpResposityImpl : HttpResposityImpl, val view: TotleFragment) : TotleContact.Presenter {

    override fun getTotleSort(){
        val disposable = httpResposityImpl.getTotleSort().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    view.getTotleSortIcon(message)
                },{
                    error->
                    Log.e("---","----fail:"+error)
                })
    }

    override fun getItemImage(url : String,fileName : String){
        val disposable = httpResposityImpl.getItemImage(url).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(object :Function<ResponseBody, Bitmap?> {
                    override fun apply(t: ResponseBody): Bitmap {
                        return if (FileUtils.saveFileToDisc(t,fileName)) {//保存图片成功
                            BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().appendText(APP_IMAGE_DIR+fileName).toString() )
                        } else Bitmap.createBitmap(null)
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({message ->

                    view.getItemImage(message)
                },{
                    error ->
                    Log.e("---","----fail:"+error)
                })
    }
}