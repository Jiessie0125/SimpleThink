package com.example.simple.simplethink.totle.activity

import android.app.Activity
import android.util.Log
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.fragment.totlePage.TotleContact
import com.example.simple.simplethink.totle.fragment.totlePage.TotleFragment
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.LocalDataCache
import com.example.simple.simplethink.utils.URLConstant
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by jiessie on 2019/6/4.
 */
class SceneDetailPresenter(val httpResposityImpl : HttpResposityImpl, val view: SceneDetailActivity) : SceneDetailContact.Presenter {
    override fun getSceneItemMp3(url : String) {
        httpResposityImpl.getSceneMP3(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    view.initView()
                },{
                    error->
                    Log.e("---","----getTotleSortfail:"+error)
                })
    }


   }