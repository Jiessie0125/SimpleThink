package com.example.simple.simplethink.buzzy

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.buzzy.adapter.BuzzyCourseAdapter
import com.example.simple.simplethink.model.TotleItem
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.adapter.CourseAdapter
import com.example.simple.simplethink.utils.ResourcesUtils
import kotlinx.android.synthetic.main.activity_buzzy_course.*
import kotlinx.android.synthetic.main.title_tool.*

/**
 * Created by mobileteam on 2019/6/21.
 */
class BuzzyCourseActivity: BaseActivity(),BuzzyCourseContact.View {

    lateinit var totleAdapter : BuzzyCourseAdapter
    lateinit var buzzyCoursePresenter : BuzzyCourseContact.Presenter

    companion object {
        const val BuzzyFlag = "BUZZYFLAG"
        fun newIntent (item : Int,context: Context?) :Intent{
            var intent = Intent(context,BuzzyCourseActivity::class.java)
            intent.putExtra(BuzzyFlag,item)
            return intent
        }
    }

    override fun setHeader() {
        super.setHeader()
        title_tool_id.text = ResourcesUtils.getString(R.string.suggestion_course)
        title_tool_back.setOnClickListener { finish() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buzzy_course)
        initView()
    }

    fun initView(){
        setHeader()
        var intent = getIntent()
        var buzzyItem = intent.getSerializableExtra(BuzzyFlag) as Int
        setAdapter()
        val httpResposityImpl = HttpResposityImpl()
        buzzyCoursePresenter = BuzzyCoursePresenter(httpResposityImpl,this)
        buzzyCoursePresenter.getBuzzyCourse(buzzyItem)
    }

    private fun setAdapter(){
        totleAdapter = BuzzyCourseAdapter(this)
        buzzyCouse_tv.layoutManager = GridLayoutManager(this,2)
        buzzyCouse_tv.adapter = totleAdapter
    }

    override fun setBuzzyCourseAdapter(buzzyCourseUrlList : ArrayList<TotleItem>) {
        totleAdapter.setData(buzzyCourseUrlList)
    }
}