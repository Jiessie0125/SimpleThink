package com.example.simple.simplethink.totle.activity

import android.util.Log
import com.example.simple.simplethink.model.CourseLogs
import com.example.simple.simplethink.model.PraticeRequest
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.netapi.auth.AuthRepository
import com.example.simple.simplethink.netapi.auth.AuthRepositoryImp
import com.example.simple.simplethink.utils.auth.AuthInstance
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * Created by jiessie on 2019/6/4.
 */
class ScenePlayPresenter : ScenePlayContact.Presenter {


    override fun bind(view: ScenePlayContact.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    val httpResposityImpl = HttpResposityImpl()
    var view: ScenePlayContact.View? = null
    private val authRepository: AuthRepository = AuthRepositoryImp()

    override fun uploadPractice(params: String) {
        val body = RequestBody.create(MediaType.parse("multipart/form-data"), params)
        httpResposityImpl.uploadPractice(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({ message ->
                    view?.onuploadPracticeSuccess()
                    Log.e("---", "----uploadExce:" + message)
                }, { error ->
                    Log.e("---", "----uploadExce:" + error)
                })
    }

    override  fun loadUserInfo() {
        authRepository.loadUserInfo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ message ->
                    AuthInstance.getInstance().userInfo = message
                    view?.onLoadUserInfoSuccess()
                }, { error ->
                    view?.onFailure(error)
                })
    }

}