package com.example.simple.simplethink.login

import com.example.simple.simplethink.netapi.HttpRepository
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.netapi.auth.AuthRepository
import com.example.simple.simplethink.netapi.auth.AuthRepositoryImp
import com.example.simple.simplethink.utils.URLConstant
import com.example.simple.simplethink.utils.auth.AuthInstance
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by 111 on 2019/7/18.
 */
class LoginPresenter : LoginContract.Presenter {


    private var view: LoginContract.View? = null
    private val authRepository: AuthRepository = AuthRepositoryImp()
    private val repository: HttpRepository = HttpResposityImpl()
    override fun bind(view: LoginContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun login(userName: String) {
        view?.loading()
        authRepository.checkIsUserExist(userName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ message ->
                    if (message.exist?.toBoolean() == true) auth(userName) else register(userName)
                }, { error ->
                    view?.dismiss()
                    view?.onFailure(error)
                })

    }

    private fun auth(userName: String) {
        authRepository.auth(userName, URLConstant.THIRD_PSD).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ message ->
                    val token = message.accessToken
                    AuthInstance.getInstance().accessToken = token
                    view?.onLoginSuccess()
                    view?.dismiss()
                }, { error ->
                    view?.dismiss()
                    view?.onFailure(error)
                })
    }

    private fun register(userName: String) {
        repository.register(URLConstant.THIRD_PSD, userName, null).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ message ->
                    auth(userName)
                }, { error ->
                    view?.dismiss()
                    view?.onFailure(error)
                })
    }

    override fun loadUserInfo() {
        view?.loading()
        authRepository.loadUserInfo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ message ->
                    AuthInstance.getInstance().userInfo = message
                    view?.onLoadUserInfoSuccess()
                    view?.dismiss()
                }, { error ->
                    view?.dismiss()
                    view?.onFailure(error)
                })
    }
}