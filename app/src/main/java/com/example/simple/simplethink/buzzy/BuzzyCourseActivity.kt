package com.example.simple.simplethink.buzzy

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.TotleItem
import com.example.simple.simplethink.totle.adapter.CourseAdapter
import kotlinx.android.synthetic.main.activity_buzzy_course.*

/**
 * Created by mobileteam on 2019/6/21.
 */
class BuzzyCourseActivity: Activity(),BuzzyCourseContact.View {

    lateinit var totleAdapter : CourseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buzzy_course)
        initView()
    }

    fun initView(){
        setAdapter()
    }

    private fun setAdapter(){
        totleAdapter = CourseAdapter()
        buzzyCouse_tv.layoutManager = GridLayoutManager(this,2)
        buzzyCouse_tv.adapter = totleAdapter
    }

    override fun setBuzzyCourseAdapter(buzzyCourseUrlList: ArrayList<TotleItem>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}