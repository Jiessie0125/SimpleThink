package com.example.simple.simplethink.main

import android.app.Activity
import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.example.simple.simplethink.netapi.HttpRepository
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.netapi.auth.AuthRepository
import com.example.simple.simplethink.netapi.auth.AuthRepositoryImp
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.LocalDataCache
import com.example.simple.simplethink.utils.URLConstant
import com.example.simple.simplethink.utils.auth.AuthInstance
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by 111 on 2019/7/18.
 */
class MainPresenter : MainContract.Presenter {

    override fun getBottomActivity() {
        repository.getBottomActivity().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({ message ->
                    view?.onGetBottomActivitySuccess(message)
                }, { error ->
                    view?.onFailure(error)
                })
    }

    override fun getSuggestedCourse(activity: Activity) {
        repository.getSuggestedCourse().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    Log.e("---","----getMyCourse:"+message)
                    LocalDataCache.save(message, URLConstant.GETMYCOURSEIMAGE)
                    for (i in 0 until message.size){
                }
                    view?.onGtSuggestedCourseSuccess(message)
                },{
                    error->
                    Log.e("---","----getMyCourse:"+error)
                    view?.onFailure(error)
                })

    }

    private var view: MainContract.View? = null
    private val repository: HttpRepository = HttpResposityImpl()
    override fun bind(view: MainContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun getSuggestedActivity(activity: Activity)  {
        repository.getSuggestedActivity().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({ message ->
                    Log.e("---","----getSuggestedActivity:"+message)
                    LocalDataCache.save(message, URLConstant.GETACTIVITYIMAGE)
                    view?.onGetSuggestedActivitySuccess(message)
                }, { error ->
                    view?.onFailure(error)
                })
    }

}