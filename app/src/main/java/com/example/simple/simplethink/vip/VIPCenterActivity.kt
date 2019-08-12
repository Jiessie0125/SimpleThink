package com.example.simple.simplethink.vip

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.login.LoginActivity
import com.example.simple.simplethink.model.SubscriptionResponse
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.utils.FilesUtils.belongCalendar
import com.example.simple.simplethink.utils.ResourcesUtils
import com.example.simple.simplethink.utils.SharedPreferencesUtil
import com.example.simple.simplethink.utils.auth.AuthInstance
import com.example.simple.simplethink.utils.auth.AuthInstance.Companion.AUTH
import kotlinx.android.synthetic.main.activity_vip_center.*
import kotlinx.android.synthetic.main.title_tool.*
import me.iwf.photopicker.PhotoPicker
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by mobileteam on 2019/6/21.
 */
class VIPCenterActivity : BaseActivity(), VIPCenterContact.View {

    lateinit var vipItemAdapter: VIPItemAdapter
    lateinit var persenter: VIPCenterContact.Presenter
    var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    companion object {
        fun newIntent(context: Context?): Intent {
            var intent = Intent(context, VIPCenterActivity::class.java)
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
        setContentView(R.layout.activity_vip_center)
        setHeader(ResourcesUtils.getString(R.string.vip_center))
        init()
    }

    override fun onResume() {
        super.onResume()
        initUserInfoView()
    }

    private fun init() {
        val httpResposityImpl = HttpResposityImpl()
        persenter = VIPCenterPresenter(httpResposityImpl, this)
        if(!SharedPreferencesUtil.getString(this,AUTH).isNullOrEmpty()) {
            persenter.getSubscription()
        }
    }

    override fun updateVipItem(sub : SubscriptionResponse) {
        val date = Date(System.currentTimeMillis())
        val startTime = sdf.parse(sub.user.start_at)
        val endTime = sdf.parse(sub.user.end_at)
        var isVip = false
        if(belongCalendar(date,startTime,endTime)) {
            userInfo.text = String.format(getString(R.string.vip_date), endTime)
            isVip = true
        }else{
            userInfo.text = ResourcesUtils.getString(R.string.not_vip)
        }
        vipItemAdapter = VIPItemAdapter(this, sub.common,isVip)
        vip_detail.layoutManager = LinearLayoutManager(this)
        vip_detail.adapter = vipItemAdapter
        vipItemAdapter.notifyDataSetChanged()
    }

    private fun initUserInfoView() {
        if(!SharedPreferencesUtil.getString(this,AUTH).isNullOrEmpty()) {
            AuthInstance.getInstance().userInfo?.let {
                Glide.with(this).load(it.avatar).apply(RequestOptions().placeholder(R.drawable.photo)).into(user_avatar)
                userName.text = it.nickName
                return
            }
        }else{
            val intent = LoginActivity.newIntent(this)
            startActivity(intent)
        }
    }
}