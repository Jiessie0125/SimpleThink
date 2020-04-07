package com.example.simple.simplethink.login

import android.util.Log
import com.example.simple.simplethink.netapi.HttpRepository
import com.example.simple.simplethink.netapi.HttpResposityImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by 111 on 2019/7/18.
 */
class ForgetPasswordPresenter : ForgetPasswordContract.Presenter {


    override fun unbind() {
        this.view = null
    }


    private var view: ForgetPasswordContract.View? = null
    private val repository: HttpRepository = HttpResposityImpl()
    override fun bind(view: ForgetPasswordContract.View) {
        this.view = view
    }


    override fun sendSMS(phoneNumber: Long) {
        repository.sendSMS(phoneNumber).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({ message ->
                    view?.onSuccess()
                }, { error ->
                    Log.e("---", "----sendSMSFail:" + error)
                    view?.onFailure(error)
                })
    }

    override fun updateUserInfo(password_old: String, password_new: String, username: String, code: String) {
        repository.updateUserInfo(password_old, password_new, username, code).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({ message ->
                    view?.onSuccess()
                }, { error ->
                    Log.e("---", "----updateUserInfoFail:" + error)
                    view?.onFailure(error)
                })
    }

    override fun register(password_old: String, password_new: String, username: String, code: String) {
        repository.register(password = password_old, username = username, code = code).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({ message ->
                    view?.onRegisterSuccess()
                }, { error ->
                    Log.e("---", "----registerFail:" + error)
                    view?.onFailure(error)
                })
    }

}