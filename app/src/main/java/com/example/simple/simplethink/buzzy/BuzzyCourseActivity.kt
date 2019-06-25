package com.example.simple.simplethink.buzzy

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.TotleItem
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.adapter.CourseAdapter
import kotlinx.android.synthetic.main.activity_buzzy_course.*

/**
 * Created by mobileteam on 2019/6/21.
 */
class BuzzyCourseActivity: Activity(),BuzzyCourseContact.View {

    lateinit var totleAdapter : CourseAdapter
    lateinit var buzzyCoursePresenter : BuzzyCourseContact.Presenter

    companion object {
        const val BuzzyFlag = "BUZZYFLAG"
        fun newIntent (item : Int,context: Context) :Intent{
            var intent = Intent(context,BuzzyCourseActivity::class.java)
            intent.putExtra(BuzzyFlag,item)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buzzy_course)
        initView()
    }

    fun initView(){
        var intent = getIntent()
        var buzzyItem = intent.getSerializableExtra(BuzzyFlag) as Int
        val httpResposityImpl = HttpResposityImpl()
        buzzyCoursePresenter = BuzzyCoursePresenter(httpResposityImpl,this)
        buzzyCoursePresenter.getBuzzyCourse(buzzyItem)
        setAdapter()
    }

    private fun setAdapter(){
        totleAdapter = CourseAdapter()
        buzzyCouse_tv.layoutManager = GridLayoutManager(this,2)
        buzzyCouse_tv.adapter = totleAdapter
    }

    override fun setBuzzyCourseAdapter(buzzyCourseUrlList : ArrayList<TotleItem>) {
        totleAdapter.setData(buzzyCourseUrlList)
    }
}