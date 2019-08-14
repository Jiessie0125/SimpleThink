package com.example.simple.simplethink.totle.activity

import android.util.Log
import com.example.simple.simplethink.model.CourseLogs
import com.example.simple.simplethink.netapi.HttpResposityImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by jiessie on 2019/6/4.
 */
class ScenePlayPresenter( val view: ScenePlayActivity) : ScenePlayContact.Presenter {
    val httpResposityImpl = HttpResposityImpl()
    override fun uploadPractice(params:  ArrayList<CourseLogs>) {
        httpResposityImpl.uploadPractice(params).subscribeOn(Schedulers.io())
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