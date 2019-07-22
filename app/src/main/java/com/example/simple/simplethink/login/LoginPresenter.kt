package com.example.simple.simplethink.login

import android.util.Log
import com.example.simple.simplethink.netapi.auth.AuthRepository
import com.example.simple.simplethink.netapi.auth.AuthRepositoryImp
import com.example.simple.simplethink.utils.auth.AuthInstance
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by 111 on 2019/7/18.
 */
class LoginPresenter : LoginContract.Presenter {
    private var view: LoginContract.View? = null
    private val repository: AuthRepository = AuthRepositoryImp()
    override fun bind(view: LoginContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun login(userName: String, password: String) {
        repository.auth(userName, password).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({ message ->
                    val token = message.accessToken
                    AuthInstance.getInstance().accessToken = token
                    view?.onSuccess()
                }, { error ->
                    Log.e("---", "----getTotleSortfail:" + error)
                    view?.onFailure()
                })
    }

}