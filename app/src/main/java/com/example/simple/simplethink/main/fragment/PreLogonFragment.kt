package com.example.simple.simplethink.main.fragment

import android.content.Intent
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.simple.simplethink.R
import com.example.simple.simplethink.login.LoginActivity
import com.example.simple.simplethink.main.MainContract
import com.example.simple.simplethink.main.MainPresenter
import com.example.simple.simplethink.main.adapter.CourseAdapter
import com.example.simple.simplethink.main.adapter.OnCoursetemClickListener
import com.example.simple.simplethink.model.ActivityResponse
import com.example.simple.simplethink.model.BottomActivityResponse
import com.example.simple.simplethink.model.SuggestedCourse
import com.example.simple.simplethink.totle.activity.RecyclerViewSpacesItemDecoration
import com.example.simple.simplethink.totle.activity.course.CourseDetailActivity
import com.example.simple.simplethink.utils.DateUtils
import com.example.simple.simplethink.utils.ErrorHandler
import com.example.simple.simplethink.vip.VIPCenterActivity
import com.example.simple.simplethink.welcome.Activity.AdvertisementActivity
import kotlinx.android.synthetic.main.fragment_main_prelogon.*
import java.util.*

/**
 * Created by Ashur on 2019/8/7.
 */
class PreLogonFragment : LogonBaseFragment(), MainContract.View {
    private var presenter: MainContract.Presenter = MainPresenter()
    lateinit var courseAdapter: CourseAdapter
    private var handler: Handler = Handler()
    private var runnable: Runnable = Runnable {}
    private var activityCount: Int = 0

    companion object {
        fun newInstance(): LogonBaseFragment {
            return PreLogonFragment()
        }
    }

    override fun onGetPracticeSuccess() {
    }

    override fun getLayoutId(): Int = R.layout.fragment_main_prelogon

    override fun initView() {
        presenter.bind(this)
        init()
    }

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
        ErrorHandler.showErrorWithToast(context!!, e)

    }

    private fun init() {
        logon_register.setOnClickListener {
            startActivity(LoginActivity.newIntent(context!!))
        }
        getImages()
    }

    private fun getImages() {
        presenter.getSuggestedCourse()
        presenter.getSuggestedActivity()
        presenter.getBottomActivity()
    }

    private fun setBottomBanner(message: BottomActivityResponse) {
        Glide.with(context!!).load(message.imgURL).into(bottom_banner)
        bottom_banner.setOnClickListener {
            redirectorBanner(message)
        }
    }

    private fun setSuggestedActivity(message: List<ActivityResponse>) {
        if (message.size == 0) {
            Glide.with(context!!).load(R.drawable.sugges_activity).into(selection_image)
            return
        }
        if (message.size == 1) {
            val suggestedActivity = message?.get(0)
            val date = DateUtils.DateToString(Date(), DateUtils.DATE_TO_STRING_DETAIAL_PATTERN)
            if (date >= suggestedActivity?.start_time.toString() && date <= suggestedActivity?.end_time.toString()) {
                Glide.with(context!!).load(message?.get(0).imgURL).apply(RequestOptions().placeholder(R.drawable.sugges_activity)).into(selection_image)
                selection_title.text = message.get(activityCount).title
                selection_sub_title.text = message.get(activityCount).subtitle
                selection_image.setOnClickListener {
                    redirector(message?.get(0))
                }
            } else {
                Glide.with(context!!).load(R.drawable.sugges_activity).into(selection_image)
            }
            return
        }
        handler = Handler()
        runnable = object : Runnable {
            override fun run() {
                handler.postDelayed(this, 4000)

                if (activityCount == message.size) {
                    activityCount = 0
                }

                val date = DateUtils.DateToString(Date(), DateUtils.DATE_TO_STRING_DETAIAL_PATTERN)
                if (date >= message?.get(activityCount)?.start_time.toString() && date <= message?.get(activityCount)?.end_time.toString()) {
                    Glide.with(context!!).load(message?.get(activityCount).imgURL).apply(RequestOptions().placeholder(R.drawable.sugges_activity)).into(selection_image)
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
                val intent = VIPCenterActivity.newIntent(context)
                startActivity(intent)
            }
            "lessions" -> {
                val intent = CourseDetailActivity.newIntent(bannerResponse.lessionsID!!.toInt(),context)
                startActivity(intent)
            }
            "sign" -> {
            }
            "advertisment" -> enterBannerActivity(AdvertisementActivity::class.java, "", bannerResponse)
        }

    }

    private fun enterBannerActivity(activity: Class<*>, from: String, bannerResponse: BottomActivityResponse?) {


        val intent = Intent(context, activity)

        if (from == "") {
            intent.putExtra("from", "main")
        } else {
            intent.putExtra("from", from)
        }
        bannerResponse?.let {
            intent.putExtra("BottomActivityResponse", it)
        }
        startActivity(intent)
        this.activity?.finish()
    }


    private fun redirector(acitivityResponse: ActivityResponse?) {
        when (acitivityResponse?.tag) {
            "vip" -> {
                val intent = VIPCenterActivity.newIntent(context)
                startActivity(intent)
            }
            "lessions" -> {
                val intent = CourseDetailActivity.newIntent(acitivityResponse.lessionsID.toInt(),context)
                startActivity(intent)

            }
            "sign" -> {
            }
            "advertisment" -> enterActivity(AdvertisementActivity::class.java, "", acitivityResponse)
        }

    }

    private fun enterActivity(activity: Class<*>, from: String, acitivityResponse: ActivityResponse?) {


        val intent = Intent(context, activity)

        if (from == "") {
            intent.putExtra("from", "main")
        } else {
            intent.putExtra("from", from)
        }
        acitivityResponse?.let {
            intent.putExtra("ActivityResponse", it)
        }
        startActivity(intent)
        this.activity?.finish()
    }


    private fun setCouseAdapter(totalList: List<SuggestedCourse>) {
        courseAdapter = CourseAdapter(activity!!, totalList)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recommend_course.layoutManager = layoutManager
        val stringIntegerHashMap = HashMap<String, Int>()
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



    override fun onDestroy() {
        presenter.unbind()
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }

    private fun showCourseDetail(title: Int) {
        var courseActivity = CourseDetailActivity.newIntent(title, context)
        startActivity(courseActivity)
    }
}