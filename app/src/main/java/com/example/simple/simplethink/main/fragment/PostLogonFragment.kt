package com.example.simple.simplethink.main.fragment

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.simple.simplethink.R
import com.example.simple.simplethink.utils.auth.AuthInstance
import kotlinx.android.synthetic.main.fragment_main_postlogon.*

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
    }
}