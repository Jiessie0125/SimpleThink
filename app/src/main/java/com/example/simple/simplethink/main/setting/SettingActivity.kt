package com.example.simple.simplethink.main.setting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.View
import com.example.simple.simplethink.MyApp
import com.example.simple.simplethink.R
import com.example.simple.simplethink.main.SettingContract
import com.example.simple.simplethink.main.SettingPresenter
import com.example.simple.simplethink.model.SuggestedCourse
import com.example.simple.simplethink.utils.SharedPreferencesUtil
import com.example.simple.simplethink.vip.VIPCenterActivity
import kotlinx.android.synthetic.main.activity_setting.*
import okhttp3.ResponseBody

/**
 * Created by jiessie on 2019/8/6.
 */
class SettingActivity : Activity(), SettingContract.View {

    private val presenter = SettingPresenter()

    override fun onLogoffSuccess(message: ResponseBody) {
    }

    override fun onFailure(e: Throwable) {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }

    companion object {
        fun newIntent (context: Context?) : Intent {
            var intent = Intent(context,SettingActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        versionInfo.text = "V" + getAppVersion() + "版本"
        setting_version.setOnClickListener {
            val intent = AppVersionActivity.newIntent(this)
            startActivity(intent)
        }
        setting_feedback.setOnClickListener {
            val intent = FeedBackActivity.newIntent(this)
            startActivity(intent)
        }
        setting_vip.setOnClickListener {
            val intent= VIPCenterActivity.newIntent(this)
            startActivity(intent)
        }

        val from = intent.getStringExtra("from")
//        SharedPreferencesUtil.getString(MyApp.context, )
        if(from.equals("prelogon")){
            setting_logoff.visibility = View.INVISIBLE
        }else{
            setting_logoff.visibility = View.VISIBLE
        }
    }

    fun getAppVersion(): String {
        val pkName = this.getPackageName()
        return this.getPackageManager().getPackageInfo(
                pkName, 0).versionName
    }
}