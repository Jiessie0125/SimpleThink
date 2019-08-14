package com.example.simple.simplethink.main.updatepassword

import android.util.Log
import com.example.simple.simplethink.main.MainContract
import com.example.simple.simplethink.netapi.HttpRepository
import com.example.simple.simplethink.netapi.HttpResposityImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by mobileteam on 2019/8/13.
 */
class UpdatePasswordPresenter : UpdatePasswordContract.Presenter {



    private var view: UpdatePasswordContract.View? = null
    private val repository: HttpRepository = HttpResposityImpl()

    override fun bind(view: UpdatePasswordContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }


    override fun updatePassword(password_old: String, password_new: String, username: String, code: String) {
        repository.updateUserInfo(password_old,password_new,username,code).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({ message ->
                    Log.e("---","----getUpdatePasswordActivity:"+message)
                    view?.onUpdatePasswordActivitySuccess()
                }, { error ->
                    view?.onFailure(error)
                })
    }
}