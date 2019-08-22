package com.example.simple.simplethink.vip

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.login.LoginActivity
import com.example.simple.simplethink.model.CreateSubRequest
import com.example.simple.simplethink.model.OrderAliPayResponse
import com.example.simple.simplethink.model.OrderWXResponse
import com.example.simple.simplethink.model.SubscriptionResponse
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.utils.FilesUtils.belongCalendar
import com.example.simple.simplethink.utils.ResourcesUtils
import com.example.simple.simplethink.utils.SharedPreferencesUtil
import com.example.simple.simplethink.utils.auth.AuthInstance
import com.example.simple.simplethink.utils.auth.AuthInstance.Companion.AUTH
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import kotlinx.android.synthetic.main.activity_vip_center.*
import kotlinx.android.synthetic.main.title_tool.*
import java.text.SimpleDateFormat
import java.util.*
import com.mob.tools.utils.UIHandler.sendMessage
import com.mob.wrappers.PaySDKWrapper.pay
import com.alipay.sdk.app.PayTask
import android.text.TextUtils
import android.annotation.SuppressLint
import android.os.Handler


/**
 * Created by mobileteam on 2019/6/21.
 */
class VIPCenterActivity : BaseActivity(), VIPCenterContact.View {

    lateinit var vipItemAdapter: VIPItemAdapter
    lateinit var persenter: VIPCenterContact.Presenter
    var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    companion object {
        const val PAYRESULT = "PAYRESULT"
        fun newIntent(context: Context?): Intent {
            var intent = Intent(context, VIPCenterActivity::class.java)
            return intent
        }
        fun newIntent(context: Context?,reslut : String?): Intent {
            var intent = Intent(context, VIPCenterActivity::class.java)
            intent.putExtra(PAYRESULT,reslut)
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val result = intent?.getSerializableExtra(PAYRESULT)
        result?.let { Toast.makeText(this, result as String, Toast.LENGTH_LONG).show() }
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
        if(belongCalendar(date,startTime,endTime)) {
            userInfo.text = String.format(getString(R.string.vip_date), endTime)
            AuthInstance.getInstance().isVip = true
        }else{
            userInfo.text = ResourcesUtils.getString(R.string.not_vip)
        }
        vipItemAdapter = VIPItemAdapter(this, sub.common,AuthInstance.getInstance().isVip)
        vip_detail.layoutManager = LinearLayoutManager(this)
        vip_detail.adapter = vipItemAdapter
        vipItemAdapter.notifyDataSetChanged()
        vipItemAdapter.setOnItemClickListener(object : OnVIPItemClickListener{
            override fun onItemClick(v: View?, position: Int) {
                when (v?.getId()) {
                    R.id.course_play -> {
                        val param = CreateSubRequest(
                                subscription_id = sub.common[position].id,
                                platform = "android"
                        )
                        persenter.createSubscription(param)
                    }
                }
            }
        })
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

    override fun sendPreWxPay(message: OrderWXResponse) {
        val api = WXAPIFactory.createWXAPI(this, message.appid)
        val req = PayReq()
        req.appId = message.appid
        req.partnerId = message.partnerid
        req.prepayId = message.prepayid
        req.packageValue = message.packageValue
        req.nonceStr = message.noncestr
        req.timeStamp = message.timestamp.toString()
        req.sign = message.sign

        api.registerApp(message.appid)
        api.sendReq(req)
    }

    override fun sendAliPay(message: OrderAliPayResponse) {
        // 必须异步调用
        /*val payThread = Thread(object : Runnable{
            override fun run() {
                val alipay = PayTask(this@VIPCenterActivity)
                val result = alipay.payV2(message.str, true)

                val msg = Message()
                msg.what = SDK_PAY_FLAG
                msg.obj = result
                mHandler.sendMessage(msg)
            }
        })
        payThread.start()*/
    }

    @SuppressLint("HandlerLeak")
    private val mHandler = object : Handler() {

        override fun handleMessage(msg: Message) {
            when (msg.what) {

               /* SDK_PAY_FLAG -> {
                    val payResult = PayResult(msg.obj as Map<String, String>)
                    *//**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     *//*
                    val resultInfo = payResult.getResult()// 同步返回需要验证的信息
                    val resultStatus = payResult.getResultStatus()
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(this@VIPCenterActivity, "支付成功", Toast.LENGTH_SHORT).show()
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(this@VIPCenterActivity, "支付失败", Toast.LENGTH_SHORT).show()
                    }
                }

                SDK_AUTH_FLAG -> {
                    val authResult = AuthResult(msg.obj as Map<String, String>, true)
                    val resultStatus = authResult.getResultStatus()

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(this@VIPCenterActivity,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show()
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(this@VIPCenterActivity,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show()

                    }
                }

                else -> {
                }*/
            }
        }
    }
}