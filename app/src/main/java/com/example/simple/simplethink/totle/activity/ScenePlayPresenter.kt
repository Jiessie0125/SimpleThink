package com.example.simple.simplethink.totle.activity

import android.util.Log
import com.example.simple.simplethink.model.CourseLogs
import com.example.simple.simplethink.model.PraticeRequest
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * Created by jiessie on 2019/6/4.
 */
class ScenePlayPresenter( val view: ScenePlayActivity) : ScenePlayContact.Presenter {
    val httpResposityImpl = HttpResposityImpl()
    override fun uploadPractice(params: PraticeRequest) {
        val body = RequestBody.create(MediaType.parse("application/json"),GsonBuilder()
                .disableHtmlEscaping()
                .create().toJson(params))
        httpResposityImpl.uploadPractice(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    Log.e("---", "----uploadExce:" + message)
                },{
                    error->
                    Log.e("---", "----uploadExce:" + error)
                })
    }

   }