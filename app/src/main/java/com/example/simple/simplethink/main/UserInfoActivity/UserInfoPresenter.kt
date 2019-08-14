package com.example.simple.simplethink.main.UserInfoActivity

import android.util.Log
import com.example.simple.simplethink.netapi.HttpRepository
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.netapi.auth.AuthRepository
import com.example.simple.simplethink.netapi.auth.AuthRepositoryImp
import com.example.simple.simplethink.utils.auth.AuthInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created by mobileteam on 2019/8/13.
 */
class UserInfoPresenter: UserInfoContract.Presenter {


    private var view: UserInfoContract.View? = null
    private val repository: HttpRepository = HttpResposityImpl()
    private val authRepository: AuthRepository = AuthRepositoryImp()


    override fun bind(view: UserInfoContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun uploadFile(avata: MultipartBody.Part) {
        repository.uploadFile(avata).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({ message ->
                    Log.e("---","----uploadFile:"+message)
                    view?.onUploadFileSuccess(message)
                }, { error ->
                    view?.onFailure(error)
                })
    }

    override fun updateUserInfo(avatar: String?, nickname: String?) {
        repository.updateUser(avatar,nickname).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({ message ->
                    Log.e("---","----uploadFile:"+message)
                    view?.onUpdateUserInfoSuccess()
                }, { error ->
                    view?.onFailure(error)
                })
    }

    override  fun loadUserInfo() {
        authRepository.loadUserInfo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ message ->
                    AuthInstance.getInstance().userInfo = message
                    view?.onRefreshUerInfoSuccess()
                }, { error ->
                    view?.onFailure(error)
                })
    }
}