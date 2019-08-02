package com.example.simple.simplethink.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import com.example.simple.simplethink.R
import kotlinx.android.synthetic.main.activity_login.*
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.sina.weibo.SinaWeibo
import cn.sharesdk.tencent.qq.QQ
import cn.sharesdk.wechat.friends.Wechat
import com.example.simple.simplethink.utils.ErrorHandler
import com.example.simple.simplethink.widget.WaitDialog
import java.util.HashMap


/**
 * Created by mobileteam on 2019/5/30.
 */
class LoginActivity : Activity(), PlatformActionListener, LoginContract.View {
    override fun loading() {
        waitDialog?.show()
    }

    override fun dismiss() {
        waitDialog?.dismiss()
    }

    val presenter = LoginPresenter()
    var waitDialog: WaitDialog? = null

    companion object {
        const val REQUEST_LOGIN = 1
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_policy.text = Html.fromHtml(getString(R.string.login_policy))
        initView()
        presenter.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }

    private fun initView() {
        waitDialog = WaitDialog(this)
        login_via_phone.setOnClickListener { loginViaPhone() }
        login_via_wechat.setOnClickListener { loginViaWechat() }
        login_via_qq.setOnClickListener { loginViaQQ() }
        login_via_weibo.setOnClickListener { loginViaWeibo() }
        login_close_btn.setOnClickListener { close() }
        register_btn.setOnClickListener { register() }
    }

    private fun loginViaWechat() {
        val platform = ShareSDK.getPlatform(Wechat.NAME)
        authorize(platform)
    }

    private fun loginViaPhone() {
        val intent = LoginViaPhoneNumActivity.newIntent(this)
        startActivityForResult(intent, REQUEST_LOGIN)
    }

    private fun loginViaQQ() {
        val platform = ShareSDK.getPlatform(QQ.NAME)
        authorize(platform)
    }

    private fun loginViaWeibo() {
        val platform = ShareSDK.getPlatform(SinaWeibo.NAME)
        authorize(platform)
    }

    private fun register() {

    }

    private fun close() {
        finish()
    }

    private fun authorize(plat: Platform) {
        plat.platformActionListener = this
        plat.SSOSetting(true)
        ShareSDK.setActivity(this)
        plat.authorize()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_LOGIN && resultCode == RESULT_OK) {
            finish()
        }
    }

    override fun onComplete(p0: Platform?, p1: Int, p2: HashMap<String, Any>?) {
        val userId = p0?.db?.userId
        userId?.let {
            runOnUiThread { presenter.login(it) }
            return
        }
        runOnUiThread { ErrorHandler.showErrorWithToast(this, R.string.login_3rd_party_auth_fail) }
    }

    override fun onCancel(p0: Platform?, p1: Int) {
        runOnUiThread { ErrorHandler.showErrorWithToast(this, R.string.login_3rd_party_auth_cancel) }
    }

    override fun onError(p0: Platform?, p1: Int, p2: Throwable?) {
        runOnUiThread { ErrorHandler.showErrorWithToast(this, R.string.login_3rd_party_auth_fail) }
    }

    override fun onSuccess() {
        finish()
    }

    override fun onFailure(e: Throwable) {
        ErrorHandler.showErrorWithToast(this, e)
    }

}