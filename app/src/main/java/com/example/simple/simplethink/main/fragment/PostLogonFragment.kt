package com.example.simple.simplethink.main.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Handler
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.simple.simplethink.R
import com.example.simple.simplethink.main.MainContract
import com.example.simple.simplethink.main.MainPresenter
import com.example.simple.simplethink.model.ActivityResponse
import com.example.simple.simplethink.model.BottomActivityResponse
import com.example.simple.simplethink.model.SuggestedCourse
import com.example.simple.simplethink.utils.DateUtils
import com.example.simple.simplethink.utils.ErrorHandler
import com.example.simple.simplethink.utils.auth.AuthInstance
import com.example.simple.simplethink.welcome.Activity.AdvertisementActivity
import kotlinx.android.synthetic.main.fragment_main_postlogon.*
import kotlinx.android.synthetic.main.fragment_main_prelogon.*
import me.iwf.photopicker.PhotoPicker
import java.io.File
import java.util.*

/**
 * Created by Ashur on 2019/8/7.
 */
class PostLogonFragment : LogonBaseFragment(),MainContract.View{
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

    private var presenter: MainContract.Presenter = MainPresenter()
    private var handler: Handler = Handler()
    private var runnable: Runnable = Runnable {}
    private var activityCount: Int = 0

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
            }
            "lessions" -> {
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
            PhotoPicker.builder()
                    .setPhotoCount(1)
                    .setShowCamera(true)
                    .setShowGif(false)
                    .setPreviewEnabled(false)
                    .start(this.activity, PhotoPicker.REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PhotoPicker.REQUEST_CODE) {
                val photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS)
                val photoPath = photos[0]
                val inputUri = Uri.fromFile(File(photoPath))
                Glide.with(this).load(inputUri).apply(RequestOptions().placeholder(R.drawable.photo)).into(avatar)
            }
        }
    }

    override fun onDestroy() {
        presenter.unbind()
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }
}