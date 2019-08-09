package com.example.simple.simplethink.vip

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.login.LoginActivity
import com.example.simple.simplethink.model.BuzzyCourseResponse
import com.example.simple.simplethink.model.VIPItem
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.utils.DateUtils
import com.example.simple.simplethink.utils.ResourcesUtils
import com.example.simple.simplethink.utils.auth.AuthInstance
import kotlinx.android.synthetic.main.activity_vip_center.*
import kotlinx.android.synthetic.main.fragment_main_postlogon.*
import kotlinx.android.synthetic.main.title_tool.*
import me.iwf.photopicker.PhotoPicker
import java.io.File

/**
 * Created by mobileteam on 2019/6/21.
 */
class VIPCenterActivity : BaseActivity(), VIPCenterContact.View {

    lateinit var vipItemAdapter: VIPItemAdapter
    var vipArray = ArrayList<VIPItem>()
    lateinit var persenter: VIPCenterContact.Presenter

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
        var vip = VIPItem("1个月", "¥10")
        var vip1 = VIPItem("3个月", "¥60")
        var vip2 = VIPItem("12个月", "¥240")
        var vip3 = VIPItem("永久", "¥1288")
        vipArray.add(vip)
        vipArray.add(vip1)
        vipArray.add(vip2)
        vipArray.add(vip3)
        vipItemAdapter = VIPItemAdapter(this, vipArray)
        vip_detail.layoutManager = LinearLayoutManager(this)
        vip_detail.adapter = vipItemAdapter
        vipItemAdapter.notifyDataSetChanged()

        val httpResposityImpl = HttpResposityImpl()
        persenter = VIPCenterPresenter(httpResposityImpl, this)
        AuthInstance.getInstance().accessToken?.let {
            persenter.getSubscription(AuthInstance.getInstance().accessToken!!)
        }

        user_avatar.setOnClickListener {
            PhotoPicker.builder()
                    .setPhotoCount(1)
                    .setShowCamera(true)
                    .setShowGif(false)
                    .setPreviewEnabled(false)
                    .start(this, PhotoPicker.REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PhotoPicker.REQUEST_CODE) {
                val photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS)
                val photoPath = photos[0]
                val inputUri = Uri.fromFile(File(photoPath))
                Glide.with(this).load(inputUri).apply(RequestOptions().placeholder(R.drawable.photo)).into(user_avatar)
            }
        }
    }


    override fun updateVipItem(buzzyCourseUrlList: List<BuzzyCourseResponse>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initUserInfoView() {
        AuthInstance.getInstance().userInfo?.let {
            Glide.with(this).load(it.avatar).apply(RequestOptions().placeholder(R.drawable.photo)).into(user_avatar)
            userName.text = it.nickName
            return
        }
        val intent = LoginActivity.newIntent(this)
        startActivity(intent)
    }
}