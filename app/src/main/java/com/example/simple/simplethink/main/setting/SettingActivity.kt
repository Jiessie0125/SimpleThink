package com.example.simple.simplethink.main.setting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.View
import com.example.simple.simplethink.MyApp
import com.example.simple.simplethink.R
import com.example.simple.simplethink.main.MainActivity
import com.example.simple.simplethink.main.SettingContract
import com.example.simple.simplethink.main.SettingPresenter
import com.example.simple.simplethink.model.SuggestedCourse
import com.example.simple.simplethink.utils.ErrorHandler
import com.example.simple.simplethink.utils.SharedPreferencesUtil
import com.example.simple.simplethink.utils.auth.AuthInstance
import com.example.simple.simplethink.vip.VIPCenterActivity
import kotlinx.android.synthetic.main.activity_setting.*
import okhttp3.ResponseBody
import kotlinx.android.synthetic.main.title_tool.*

/**
 * Created by jiessie on 2019/8/6.
 */
class SettingActivity : Activity(), SettingContract.View {

    private val presenter = SettingPresenter()

    override fun onLogoffSuccess(message: ResponseBody) {
        AuthInstance.getInstance().clear()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onFailure(e: Throwable) {
        ErrorHandler.showErrorWithToast(this, e)
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
        init()
        presenter.bind(this)

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

        initLogoffField()
        setting_logoff.setOnClickListener {
            presenter.appLogoff()
        }
    }

    fun initLogoffField(){
        setting_logoff.visibility = View.INVISIBLE
        AuthInstance.getInstance().accessToken?.let {
            setting_logoff.visibility = View.VISIBLE
        }
    }

    fun getAppVersion(): String {
        val pkName = this.getPackageName()
        return this.getPackageManager().getPackageInfo(
                pkName, 0).versionName
    }

    private fun init(){
        title_tool_back.setOnClickListener { finish() }
    }
}