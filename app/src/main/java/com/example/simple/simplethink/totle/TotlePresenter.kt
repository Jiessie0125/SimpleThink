package com.example.simple.simplethink.totle

import android.util.Log
import com.example.simple.simplethink.MyApp
import com.example.simple.simplethink.netapi.HttpRepository
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.activity.SceneDetailActivity
import com.example.simple.simplethink.totle.activity.SceneDetailContact
import com.example.simple.simplethink.utils.SharedPreferencesUtil
import com.example.simple.simplethink.utils.auth.AuthInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by jiessie on 2019/6/4.
 */
class TotlePresenter( val view: TotleActivity) : TotleContact.Presenter {

    private val repository: HttpRepository = HttpResposityImpl()
    override fun refreshToken(refresh : String?) {
        repository.refresh(refresh!!).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    val token = message.accessToken
                    AuthInstance.getInstance().accessToken = token
                    SharedPreferencesUtil.setString(MyApp.context, AuthInstance.AUTH, AuthInstance.getInstance().accessToken)
                    SharedPreferencesUtil.setString(MyApp.context, AuthInstance.REFRESHTOKEN,message.refreshToken)
                },{
                    error->
                    Log.e("---", "----getTotleSortfail:" + error)
                })
    }


   }