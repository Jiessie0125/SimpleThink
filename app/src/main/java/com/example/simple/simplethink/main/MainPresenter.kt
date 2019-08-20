package com.example.simple.simplethink.main

import android.app.Activity
import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.example.simple.simplethink.model.PracticesResponse
import com.example.simple.simplethink.netapi.HttpRepository
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.netapi.auth.AuthRepository
import com.example.simple.simplethink.netapi.auth.AuthRepositoryImp
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.LocalDataCache
import com.example.simple.simplethink.utils.URLConstant
import com.example.simple.simplethink.utils.auth.AuthInstance
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import org.json.JSONArray
import org.json.JSONObject

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

    override fun getSuggestedCourse() {
        repository.getSuggestedCourse().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    Log.e("---","----getMyCourse:"+message)
                    view?.onGetSuggestedCourseSuccess(message)
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

    override fun getSuggestedActivity()  {
        repository.getSuggestedActivity().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({ message ->
                    Log.e("---","----getSuggestedActivity:"+message)
                    view?.onGetSuggestedActivitySuccess(message)
                }, { error ->
                    view?.onFailure(error)
                })
    }

    override fun getPracticeList() {
        repository.getCourseLogs().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({ message ->
                    val gson = Gson()
                   // val response : PracticesResponse = Gson().fromJson(message.toString())
                    var msg = message.toString()
                    val response : List<PracticesResponse> = gson.fromJson(message.toString(), object :TypeToken<List<PracticesResponse>>(){}.type)
                    Log.e("---","----response---:"+response)
                }, { error ->
                    view?.onFailure(error)
                })
    }

    inline fun <reified PracticesResponse : Any> Gson.fromJson(json: String): List<PracticesResponse> {
        return Gson().fromJson(json, object :TypeToken<List<PracticesResponse>>(){}.type)
    }

}