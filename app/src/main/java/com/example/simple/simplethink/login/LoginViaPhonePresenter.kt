package com.example.simple.simplethink.login

import com.example.simple.simplethink.MyApp
import com.example.simple.simplethink.netapi.auth.AuthRepository
import com.example.simple.simplethink.netapi.auth.AuthRepositoryImp
import com.example.simple.simplethink.utils.SharedPreferencesUtil
import com.example.simple.simplethink.utils.auth.AuthInstance
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by 111 on 2019/7/18.
 */
class LoginViaPhonePresenter : LoginViaPhoneContract.Presenter {
    private var view: LoginViaPhoneContract.View? = null
    private val repository: AuthRepository = AuthRepositoryImp()
    override fun bind(view: LoginViaPhoneContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun login(userName: String, password: String) {
        view?.showLoading()
        repository.auth(userName, password).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({ message ->
                    val token = message.accessToken
                    AuthInstance.getInstance().accessToken = token
                    SharedPreferencesUtil.setString(MyApp.context, AuthInstance.AUTH,AuthInstance.getInstance().accessToken)
                    SharedPreferencesUtil.setString(MyApp.context, AuthInstance.REFRESHTOKEN,message.refreshToken)
                    loadUserInfo()
                }, { error ->
                    view?.dismissLoading()
                    view?.onFailure(error)
                })
    }

    private fun loadUserInfo() {
        repository.loadUserInfo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({ message ->
                    view?.dismissLoading()
                    AuthInstance.getInstance().userInfo = message
                    view?.onSuccess()
                }, { error ->
                    view?.dismissLoading()
                    view?.onFailure(error)
                })
    }
}