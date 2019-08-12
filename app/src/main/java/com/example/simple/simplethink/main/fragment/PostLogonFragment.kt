package com.example.simple.simplethink.main.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.simple.simplethink.R
import com.example.simple.simplethink.utils.auth.AuthInstance
import kotlinx.android.synthetic.main.fragment_main_postlogon.*
import me.iwf.photopicker.PhotoPicker
import java.io.File

/**
 * Created by Ashur on 2019/8/7.
 */
class PostLogonFragment : LogonBaseFragment() {
    companion object {
        fun newInstance(): LogonBaseFragment {
            return PostLogonFragment()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_main_postlogon

    override fun initView() {
        initUserInfoView()
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

}