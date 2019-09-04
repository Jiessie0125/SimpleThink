package com.example.simple.simplethink.main

import android.util.Log
import com.example.simple.simplethink.model.PracticeResponse
import com.example.simple.simplethink.netapi.HttpRepository
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.utils.FilesUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


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
                    val coursePratice = FilesUtils.getMap(message.string())
                    Log.e("---coursePratice--","----response---:"+coursePratice)
                    view?.onGetPracticeSuccess(coursePratice)

                }, { error ->
                    view?.onFailure(error)
                })
    }
    override fun getSubscription(){
        repository.getSubscription().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    view?.updateVipItem(message)
                },{
                    error->
                    view?.onFailure(error)
                })
    }
}