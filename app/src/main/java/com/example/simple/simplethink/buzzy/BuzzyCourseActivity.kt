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
import com.example.simple.simplethink.totle.activity.RecyclerViewSpacesItemDecoration
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
        const val BUZZYFLAG = "BUZZYFLAG"
        const val BUZZYTITLE = "BUZZYTITLE"
        const val ISBUZZY = "ISBUZZY"
        fun newIntent (item : Int,context: Context?,title: String,isBuzzy: Boolean) :Intent{
            var intent = Intent(context,BuzzyCourseActivity::class.java)
            intent.putExtra(BUZZYFLAG,item)
            intent.putExtra(BUZZYTITLE,title)
            intent.putExtra(ISBUZZY,isBuzzy)
            return intent
        }
    }

    override fun setHeader(title: String) {
        super.setHeader(title)
        title_tool_id.text = title
        title_tool_back.setOnClickListener { finish() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buzzy_course)
        initView()
    }

    fun initView(){
        var intent = getIntent()
        var buzzyItem = intent.getSerializableExtra(BUZZYFLAG) as Int
        var buzzyTitle = intent.getSerializableExtra(BUZZYTITLE) as String
        var isbuzzy = intent.getSerializableExtra(ISBUZZY) as Boolean
        setHeader(buzzyTitle)
        setAdapter()
        val httpResposityImpl = HttpResposityImpl()
        buzzyCoursePresenter = BuzzyCoursePresenter(httpResposityImpl,this)
        if (isbuzzy) buzzyCoursePresenter.getBuzzyCourse(buzzyItem) else buzzyCoursePresenter.getSortCouse(buzzyItem)
    }

    private fun setAdapter(){
        totleAdapter = BuzzyCourseAdapter(this)
        val stringIntegerHashMap = HashMap< String, Int>()
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, 25)//右间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION, 25)
        buzzyCouse_tv.addItemDecoration(RecyclerViewSpacesItemDecoration(stringIntegerHashMap))
        buzzyCouse_tv.layoutManager = GridLayoutManager(this,2)
        buzzyCouse_tv.adapter = totleAdapter
    }

    override fun setBuzzyCourseAdapter(buzzyCourseUrlList : ArrayList<TotleItem>) {
        totleAdapter.setData(buzzyCourseUrlList)
    }
}