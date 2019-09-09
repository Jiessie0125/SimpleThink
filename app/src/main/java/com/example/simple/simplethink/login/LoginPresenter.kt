package com.example.simple.simplethink.login

import com.example.simple.simplethink.MyApp
import cn.sharesdk.framework.PlatformDb
import com.example.simple.simplethink.netapi.HttpRepository
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.netapi.auth.AuthRepository
import com.example.simple.simplethink.netapi.auth.AuthRepositoryImp
import com.example.simple.simplethink.utils.SharedPreferencesUtil
import com.example.simple.simplethink.utils.URLConstant
import com.example.simple.simplethink.utils.auth.AuthInstance
import com.example.simple.simplethink.utils.auth.AuthInstance.Companion.AUTH
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

    override fun login(platformDb: PlatformDb) {
        view?.loading()
        authRepository.checkIsUserExist(platformDb.userId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ message ->
                    if (message.exist?.toBoolean() == true)
                        auth(platformDb.userId)
                    else
                        register(platformDb.userId,platformDb.userName,platformDb.userIcon,platformDb.userGender)
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
                    SharedPreferencesUtil.setString(MyApp.context,AUTH,AuthInstance.getInstance().accessToken)
                    SharedPreferencesUtil.setString(MyApp.context, AuthInstance.REFRESHTOKEN,message.refreshToken)
                    view?.onLoginSuccess()
                    view?.dismiss()
                }, { error ->
                    view?.dismiss()
                    view?.onFailure(error)
                })
    }

    private fun register(userName: String, nickName: String, avatar: String, gender: String) {
        val myGender = if (gender.equals("m", true)) "male" else if (gender.equals("f", true)) "female" else "other"
        repository.register(password = URLConstant.THIRD_PSD, username = userName, nickname = nickName, avatar = avatar, gender = myGender).subscribeOn(Schedulers.io())
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