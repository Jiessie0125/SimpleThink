package com.example.simple.simplethink.main

import android.util.Log
import com.example.simple.simplethink.netapi.HttpRepository
import com.example.simple.simplethink.netapi.HttpResposityImpl
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by 111 on 2019/7/18.
 */
class SettingPresenter : SettingContract.Presenter {


    private var view: SettingContract.View? = null
    private val repository: HttpRepository = HttpResposityImpl()
    override fun bind(view: SettingContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun appLogoff() {
        repository.appLogoff().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({ message ->
                    Log.e("---","----Logoff:"+message)
                    view?.onLogoffSuccess(message)
                }, { error ->
                    view?.onFailure(error)
                })
    }

}