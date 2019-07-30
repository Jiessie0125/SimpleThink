package com.example.simple.simplethink.totle.activity.course

import android.util.Log
import com.example.simple.simplethink.buzzy.BuzzyCourseActivity
import com.example.simple.simplethink.buzzy.BuzzyCourseContact
import com.example.simple.simplethink.model.TotleItem
import com.example.simple.simplethink.netapi.HttpResposityImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by mobileteam on 2019/6/21.
 */
class CourseDetailPresenter(val httpResposityImpl : HttpResposityImpl, val view: CourseDetailActivity) : CourseDetailContact.Presenter {

    override fun getCourse(id: Int) {
        httpResposityImpl.getCourseDetail(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { result -> result }
                .subscribe({message ->
                    view.setCourseAdapter(message)
                },{
                    error->
                    Log.e("---", "----getTotleSortfail:" + error)
                })
    }

}