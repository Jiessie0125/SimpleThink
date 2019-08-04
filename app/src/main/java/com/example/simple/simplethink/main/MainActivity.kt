package com.example.simple.simplethink.main

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.simple.simplethink.R
import com.example.simple.simplethink.login.LoginViaPhoneNumActivity
import com.example.simple.simplethink.main.adapter.CourseAdapter
import com.example.simple.simplethink.main.adapter.OnCoursetemClickListener
import com.example.simple.simplethink.model.ActivityResponse
import com.example.simple.simplethink.model.BottomActivityResponse
import com.example.simple.simplethink.model.SuggestedCourse
import com.example.simple.simplethink.totle.TotleActivity
import com.example.simple.simplethink.totle.activity.RecyclerViewSpacesItemDecoration
import com.example.simple.simplethink.totle.activity.course.CourseDetailActivity
import com.example.simple.simplethink.utils.ErrorHandler
import com.example.simple.simplethink.utils.LocalDataCache
import com.example.simple.simplethink.utils.URLConstant
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by mobileteam on 2019/6/3.
 */
class MainActivity : Activity() , MainContract.View{

    private var presenter: MainContract.Presenter = MainPresenter()
    lateinit var courseAdapter : CourseAdapter

    override fun onGetSuggestedActivitySuccess(message: ActivityResponse) {
    }

    override fun onGetBottomActivitySuccess(message: BottomActivityResponse) {

    }

    override fun onGtSuggestedCourseSuccess(message: List<SuggestedCourse>) {
        setCouseAdapter(message)
    }


    override fun onFailure(e: Throwable) {
        ErrorHandler.showErrorWithToast(this, e)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        home.setOnClickListener { showTotlePage() }
        logon_register.setOnClickListener {
            startActivity(LoginViaPhoneNumActivity.newIntent(this))
        }
        getImages()
    }

    private fun getImages(){
        var myCourse = LocalDataCache.getLocalData(URLConstant.GETMYCOURSEIMAGE)
        if(myCourse == null){
            presenter.getSuggestedCourse(this)
        }else{
            setCouseAdapter(myCourse as List<SuggestedCourse>)
        }
    }


    private fun setCouseAdapter(totalList : List<SuggestedCourse>){
        courseAdapter = CourseAdapter(this, totalList)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recommend_course.layoutManager = layoutManager
        val stringIntegerHashMap = HashMap< String, Int>()
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, 25)
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION, 50)
        recommend_course.addItemDecoration(RecyclerViewSpacesItemDecoration(stringIntegerHashMap))
        recommend_course.adapter = courseAdapter
        courseAdapter.notifyDataSetChanged()
        courseAdapter.setOnItemClickListener(object : OnCoursetemClickListener {
            override fun onItemClick(v: View?, position: Int) {
                 showCourseDetail(totalList[position].id)
            }
        })
        }

    private fun showTotlePage(){
        val totlepage = TotleActivity.newIntent(this)
        startActivity(totlepage)
    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }

     private fun showCourseDetail(title: Int) {
         var courseActivity = CourseDetailActivity.newIntent(title,this)
         startActivity(courseActivity)
     }


}
