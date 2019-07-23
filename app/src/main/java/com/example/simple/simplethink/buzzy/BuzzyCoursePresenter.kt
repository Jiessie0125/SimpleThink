package com.example.simple.simplethink.buzzy

import android.graphics.Bitmap
import android.util.Log
import com.example.simple.simplethink.model.TotleItem
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.utils.FilesUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

/**
 * Created by mobileteam on 2019/6/21.
 */
class BuzzyCoursePresenter(val httpResposityImpl : HttpResposityImpl, val view: BuzzyCourseActivity) : BuzzyCourseContact.Presenter{

    var buzzyCourseUrlList = ArrayList<TotleItem>()

    override fun getBuzzyCourse(id: Int){
        httpResposityImpl.getBuzzyCourse(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    for(i in 0 until message.size){
                        var bannerURL = message[i].title_img_new
                        var title = message[i].title
                        var totleItem = TotleItem(title,bannerURL)
                        buzzyCourseUrlList.add(totleItem)
                        view.setBuzzyCourseAdapter(buzzyCourseUrlList)
                    }
                },{
                    error->
                    Log.e("---","----getTotleSortfail:"+error)
                })
    }

    private fun getBuzzyCourseImage(url : String,strFileName : String){
       /* httpResposityImpl.getCourseImageItem(url).subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .map(object : Function<ResponseBody, Bitmap> {
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
                   /* var totleItem = TotleItem(strFileName,message)
                    buzzyCourseUrlList.add(totleItem)
                    buzzyCourseUrlList?.let {
                        view.setBuzzyCourseAdapter(buzzyCourseUrlList)
                    }*/
                },{
                    error ->
                    Log.e("---","----getItemImagefail:"+error)
                })*/
    }

    override fun getSortCouse(id:Int) {
        httpResposityImpl.getSortCourse(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    for(i in 0 until message.size){
                        var bannerURL = message[i].title_img_new
                        var title = message[i].title
                        var totleItem = TotleItem(title,bannerURL)
                        buzzyCourseUrlList.add(totleItem)
                        view.setBuzzyCourseAdapter(buzzyCourseUrlList)
                    }
                },{
                    error->
                    Log.e("---","----getTotleSortfail:"+error)
                })
    }
}