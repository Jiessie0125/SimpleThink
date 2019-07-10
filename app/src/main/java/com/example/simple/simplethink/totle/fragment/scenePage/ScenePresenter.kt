package com.example.simple.simplethink.totle.fragment.scenePage

import android.graphics.Bitmap
import android.util.Log
import com.example.simple.simplethink.model.SceneItem
import com.example.simple.simplethink.model.ScenesResponse
import com.example.simple.simplethink.model.Sections
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.utils.FilesUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

/**
 * Created by jiessie on 2019/6/4.
 */
class ScenePresenter(val httpResposityImpl: HttpResposityImpl, val view: SceneFragment) : SceneContact.Presenter {

    var scenesResponse = ArrayList<SceneItem>()

    override fun getAllScene() {
        httpResposityImpl.getScenes().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({ message ->
                    Log.e("---", "----getScenes:" + message)
                    /*for(i in 0 until message.size ){
                       // getSencesImage(message[i].title_img_new,message[i].title,message[i].sections)
                        var sceneItem = SceneItem(message[i].title,message[i].title_img_new,message[i].sections)
                        scenesResponse.add(sceneItem)

                   }*/
                    view.setSenceAdapter(message)
                }, { error ->
                    Log.e("---", "----getScenes:" + error)
                })
    }

    private fun getSencesImage(url: String, strFileName: String,scensMsg: List<Sections>) {
       /*httpResposityImpl.getCourseImageItem(url).subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .map(object : Function<ResponseBody, Bitmap> {
                    override fun apply(t: ResponseBody): Bitmap? {
                        val messageByte = t.bytes()
                        if (FilesUtils.savaBitmap(messageByte, strFileName, "jpg")) {
                            return FilesUtils.getItemIcon(strFileName, "jpg")
                        } else {
                            return null
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ message ->
                    var sceneItem = SceneItem(strFileName,message,scensMsg)
                    scenesResponse.add(sceneItem)
                    view.setSenceAdapter(scenesResponse)
                }, { error ->
                    Log.e("---", "----getItemImagefail:" + error)
                })*/

    }
}