package com.example.simple.simplethink.main

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.request.RequestOptions
import com.example.simple.simplethink.R
import com.example.simple.simplethink.login.LoginActivity
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
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.LocalDataCache
import com.example.simple.simplethink.utils.URLConstant
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by mobileteam on 2019/6/3.
 */
 class MainActivity : Activity() , MainContract.View{

    private var presenter: MainContract.Presenter = MainPresenter()
    lateinit var courseAdapter : CourseAdapter
    private var handler: Handler = Handler()
    private var runnable: Runnable = Runnable {}
    private var activityCount: Int = 0

    override fun onGetSuggestedActivitySuccess(message: List<ActivityResponse>) {
        setSuggestedActivity(message)
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
        selection_more.setOnClickListener {

        }
        //PermissionUtils.requestPermissions(Manifest.permission.CAMERA,REQ_CODE_PICK_PHOTO);
    }

    private fun init() {
        home.setOnClickListener { showTotlePage() }
        logon_register.setOnClickListener {
            startActivity(LoginActivity.newIntent(this))
        }
        getImages()
    }

    private fun getImages(){
        var myCourse = LocalDataCache.getLocalData(URLConstant.GETMYCOURSEIMAGE)
        var suggestedActivity = LocalDataCache.getLocalData(URLConstant.GETACTIVITYIMAGE)
        if(myCourse == null){
            presenter.getSuggestedCourse(this)
        }else{
            setCouseAdapter(myCourse as List<SuggestedCourse>)
        }
        if(suggestedActivity == null){
            presenter.getSuggestedActivity(this)
        }else{
            setSuggestedActivity(suggestedActivity as List<ActivityResponse>)
        }
    }

    private fun setSuggestedActivity(message: List<ActivityResponse>){
        if(message.size ==0){
            Glide.with(this@MainActivity).load(R.drawable.sugges_activity).into(selection_image)
            return
        }
//        if(message.size == 1){
//            Glide.with(this@MainActivity).load(message?.get(0).imgURL).apply(RequestOptions().placeholder(R.drawable.sugges_activity)).into(selection_image)
//            return
//        }
        handler = Handler()
        runnable = object : Runnable
        {
            override fun run() {
                handler.postDelayed(this, 4000)

                if(activityCount == message.size){
                    activityCount = 0
                }

                Glide.with(this@MainActivity).load(message?.get(activityCount).imgURL).apply(RequestOptions().placeholder(R.drawable.sugges_activity)).into(selection_image)
                selection_title.text = message.get(activityCount).title
                selection_sub_title.text = message.get(activityCount).subtitle
                activityCount++
            }
        }
        handler.post(runnable)
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
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }

     private fun showCourseDetail(title: Int) {
         var courseActivity = CourseDetailActivity.newIntent(title,this)
         startActivity(courseActivity)
     }


}
