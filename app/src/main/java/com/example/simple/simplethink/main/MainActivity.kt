package com.example.simple.simplethink.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
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
import com.example.simple.simplethink.main.setting.SettingActivity
import com.example.simple.simplethink.model.ActivityResponse
import com.example.simple.simplethink.model.BannerResponse
import com.example.simple.simplethink.model.BottomActivityResponse
import com.example.simple.simplethink.model.SuggestedCourse
import com.example.simple.simplethink.totle.TotleActivity
import com.example.simple.simplethink.totle.activity.RecyclerViewSpacesItemDecoration
import com.example.simple.simplethink.totle.activity.course.CourseDetailActivity
import com.example.simple.simplethink.utils.*
import com.example.simple.simplethink.welcome.Activity.AdvertisementActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Created by mobileteam on 2019/6/3.
 */
class MainActivity : Activity() , MainContract.View, PermissionInterface {


    private var presenter: MainContract.Presenter = MainPresenter()
    lateinit var courseAdapter : CourseAdapter
    var LOCATION:Int = 1
    var STORATE:Int = 2

    val arrayOfStringCall: Array<String> = arrayOf(Manifest.permission.READ_PHONE_STATE
            ,Manifest.permission.READ_CALL_LOG,Manifest.permission.CALL_PHONE,
            Manifest.permission.ADD_VOICEMAIL,Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.USE_SIP,Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION
    )
    private var handler: Handler = Handler()
    private var runnable: Runnable = Runnable {}
    private var activityCount: Int = 0

    override fun onGetSuggestedActivitySuccess(message: List<ActivityResponse>) {
        setSuggestedActivity(message)
    }

    override fun onGetBottomActivitySuccess(message: BottomActivityResponse) {
        setBottomBanner(message)
    }

    override fun onGetSuggestedCourseSuccess(message: List<SuggestedCourse>) {
        setCouseAdapter(message)
    }


    override fun onFailure(e: Throwable) {
        ErrorHandler.showErrorWithToast(this, e)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.bind(this)
        init()
        PermissionUtils.requestPermissions(arrayOfStringCall,LOCATION,this,this);
        setting.setOnClickListener { showSettingPage() }
    }


    private fun showSettingPage(){
        val settingIntent = SettingActivity.newIntent(this)
        startActivity(settingIntent)
    }

    private fun init() {
        home.setOnClickListener { showTotlePage() }
        logon_register.setOnClickListener {
            startActivity(LoginActivity.newIntent(this))
        }
        getImages()
    }

    private fun getImages(){
        presenter.getSuggestedCourse()
        presenter.getSuggestedActivity()
        presenter.getBottomActivity()
    }

    private fun setBottomBanner(message: BottomActivityResponse){
        Glide.with(this@MainActivity).load(message.imgURL).into(bottom_banner)
        bottom_banner.setOnClickListener {
            redirectorBanner(message)
        }
    }

    private fun setSuggestedActivity(message: List<ActivityResponse>){
        if(message.size ==0){
            Glide.with(this@MainActivity).load(R.drawable.sugges_activity).into(selection_image)
            return
        }
        if(message.size == 1){
            val suggestedActivity = message?.get(0)
            val date = DateUtils.DateToString(Date(), DateUtils.DATE_TO_STRING_DETAIAL_PATTERN)
            if(date >= suggestedActivity?.start_time.toString() && date <= suggestedActivity?.end_time.toString()) {
                Glide.with(this@MainActivity).load(message?.get(0).imgURL).apply(RequestOptions().placeholder(R.drawable.sugges_activity)).into(selection_image)
                selection_image.setOnClickListener {
                    redirector(message?.get(0))
                }
            }
            else{
                Glide.with(this@MainActivity).load(R.drawable.sugges_activity).into(selection_image)
            }
            return
        }
        handler = Handler()
        runnable = object : Runnable
        {
            override fun run() {
                handler.postDelayed(this, 4000)

                if(activityCount == message.size){
                    activityCount = 0
                }

                val date = DateUtils.DateToString(Date(), DateUtils.DATE_TO_STRING_DETAIAL_PATTERN)
                if(date >= message?.get(activityCount)?.start_time.toString() && date <= message?.get(activityCount)?.end_time.toString()) {
                    Glide.with(this@MainActivity).load(message?.get(activityCount).imgURL).apply(RequestOptions().placeholder(R.drawable.sugges_activity)).into(selection_image)
                    selection_title.text = message.get(activityCount).title
                    selection_sub_title.text = message.get(activityCount).subtitle
                    activityCount++
                    selection_image.setOnClickListener {
                        redirector(message?.get(activityCount))
                    }
                }
            }
        }
        handler.post(runnable)
    }

    private fun redirectorBanner(bannerResponse: BottomActivityResponse?) {
        when (bannerResponse?.tag) {
            "vip" -> {
            }
            "lessions" -> {
            }
            "sign" -> {
            }
            "advertisment" -> enterBannerActivity(AdvertisementActivity::class.java, "", bannerResponse)
        }

    }

    private fun enterBannerActivity(activity: Class<*>, from: String, bannerResponse: BottomActivityResponse?) {


        val intent = Intent(this@MainActivity, activity)

        if (from == "") {
            intent.putExtra("from", "main")
        }else{
            intent.putExtra("from", from)
        }
        bannerResponse?.let {
            intent.putExtra("BottomActivityResponse", it)
        }
        startActivity(intent)
        finish()
    }


    private fun redirector(acitivityResponse: ActivityResponse?) {
        when (acitivityResponse?.tag) {
            "vip" -> {
            }
            "lessions" -> {
            }
            "sign" -> {
            }
            "advertisment" -> enterActivity(AdvertisementActivity::class.java, "", acitivityResponse)
        }

    }

    private fun enterActivity(activity: Class<*>, from: String, acitivityResponse: ActivityResponse?) {


        val intent = Intent(this@MainActivity, activity)

        if (from == "") {
            intent.putExtra("from", "main")
        }else{
            intent.putExtra("from", from)
        }
        acitivityResponse?.let {
            intent.putExtra("ActivityResponse", it)
        }
        startActivity(intent)
        finish()
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
    override fun requestPermissionsSuccess(callBackCode: Int) {
//        when (callBackCode) {
//            LOCATION -> PermissionUtils.requestPermissions(arrayOfStringStorage,2,this,this)
//            STORATE -> PermissionUtils.requestPermissions(arrayOfStringLocation,3,this,this)
//        }

    }

    override fun requestPermissionsFail(callBackCode: Int) {
//        when (callBackCode) {
//            LOCATION -> PermissionUtils.requestPermissions(arrayOfStringStorage,2,this,this)
//            STORATE -> PermissionUtils.requestPermissions( arrayOfStringLocation,3,this,this)
//        }
    }

    override  fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?, grantResults: IntArray?) {
        PermissionUtils.requestPermissionsResult(requestCode, permissions as Array<String>
                , grantResults!!); // 接管结果判断
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
