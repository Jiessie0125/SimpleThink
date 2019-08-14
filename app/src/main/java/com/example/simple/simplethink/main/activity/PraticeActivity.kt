package com.example.simple.simplethink.main.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.login.LoginActivity
import com.example.simple.simplethink.model.SubscriptionResponse
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.ResourcesUtils
import com.example.simple.simplethink.utils.SharedPreferencesUtil
import com.example.simple.simplethink.utils.auth.AuthInstance
import com.example.simple.simplethink.vip.VIPCenterContact
import com.example.simple.simplethink.vip.VIPCenterPresenter
import com.example.simple.simplethink.vip.VIPItemAdapter
import kotlinx.android.synthetic.main.title_tool.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by mobileteam on 2019/6/21.
 */
class PraticeActivity : BaseActivity(), PraticeContact.View {

    lateinit var vipItemAdapter: VIPItemAdapter
    lateinit var persenter: PraticePresenter
    var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    companion object {
        fun newIntent(context: Context?): Intent {
            var intent = Intent(context, PraticeActivity::class.java)
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
        setContentView(R.layout.activity_pratice)
        setHeader(ResourcesUtils.getString(R.string.pratice_center))
        init()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun init() {
        persenter = PraticePresenter( this)
        if(!SharedPreferencesUtil.getString(this, AuthInstance.AUTH).isNullOrEmpty()) {
           // persenter.getSubscription()
        }
    }

    override fun updateVipItem(sub: SubscriptionResponse) {

    }

}