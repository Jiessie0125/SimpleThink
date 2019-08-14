package com.example.simple.simplethink.main.fragment

import android.content.Intent
import android.os.Handler
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.simple.simplethink.R
import com.example.simple.simplethink.main.MainContract
import com.example.simple.simplethink.main.MainPresenter
import com.example.simple.simplethink.main.UserInfoActivity.UserInfoActivity
import com.example.simple.simplethink.model.ActivityResponse
import com.example.simple.simplethink.model.BottomActivityResponse
import com.example.simple.simplethink.model.SuggestedCourse
import com.example.simple.simplethink.totle.activity.course.CourseDetailActivity
import com.example.simple.simplethink.utils.DateUtils
import com.example.simple.simplethink.utils.ErrorHandler
import com.example.simple.simplethink.utils.auth.AuthInstance
import com.example.simple.simplethink.vip.VIPCenterActivity
import com.example.simple.simplethink.welcome.Activity.AdvertisementActivity
import kotlinx.android.synthetic.main.fragment_main_postlogon.*
import java.util.*

/**
 * Created by Ashur on 2019/8/7.
 */
class PostLogonFragment : LogonBaseFragment(),MainContract.View {

    private var presenter: MainContract.Presenter = MainPresenter()
    private var handler: Handler = Handler()
    private var runnable: Runnable = Runnable {}
    private var activityCount: Int = 0


    override fun onGetSuggestedActivitySuccess(message: List<ActivityResponse>) {
        setSuggestedActivity(message)
    }

    override fun onGetBottomActivitySuccess(message: BottomActivityResponse) {
    }

    override fun onGetSuggestedCourseSuccess(message: List<SuggestedCourse>) {
    }

    override fun onFailure(e: Throwable) {
        ErrorHandler.showErrorWithToast(context!!, e)
    }

    companion object {
        fun newInstance(): LogonBaseFragment {
            return PostLogonFragment()
        }
    }

    private fun setSuggestedActivity(message: List<ActivityResponse>) {
        if (message.size == 0) {
            Glide.with(context!!).load(R.drawable.sugges_activity).into(selection_image1)
            return
        }
        if (message.size == 1) {
            val suggestedActivity = message?.get(0)
            val date = DateUtils.DateToString(Date(), DateUtils.DATE_TO_STRING_DETAIAL_PATTERN)
            if (date >= suggestedActivity?.start_time.toString() && date <= suggestedActivity?.end_time.toString()) {
                Glide.with(context!!).load(message?.get(0).imgURL).apply(RequestOptions().placeholder(R.drawable.sugges_activity)).into(selection_image1)
                selection_title1.text = message.get(activityCount).title
                selection_sub_title1.text = message.get(activityCount).subtitle

                selection_image1.setOnClickListener {
                    redirector(message?.get(0))
                }
            } else {
                Glide.with(context!!).load(R.drawable.sugges_activity).into(selection_image1)
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
                    Glide.with(context!!).load(message?.get(activityCount).imgURL).apply(RequestOptions().placeholder(R.drawable.sugges_activity)).into(selection_image1)
                    selection_title1.text = message.get(activityCount).title
                    selection_sub_title1.text = message.get(activityCount).subtitle
                    activityCount++
                    selection_image1.setOnClickListener {
                        redirector(message?.get(activityCount))
                    }
                }
            }
        }
        handler.post(runnable)
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

    override fun getLayoutId(): Int = R.layout.fragment_main_postlogon

    override fun initView() {
        initUserInfoView()
        presenter.bind(this)
        getImages()
    }

    private fun getImages() {
        presenter.getSuggestedActivity()
    }

    private fun initUserInfoView() {
        val userInfo = AuthInstance.getInstance().userInfo
        Glide.with(context!!).load(userInfo?.avatar).apply(RequestOptions().placeholder(R.drawable.photo)).into(avatar)
        user_name.text = userInfo?.nickName
        avatar.setOnClickListener {
            val intent = UserInfoActivity.newIntent(context)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        presenter.unbind()
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }
}