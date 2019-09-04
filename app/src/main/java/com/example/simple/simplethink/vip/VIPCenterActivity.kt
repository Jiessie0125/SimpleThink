package com.example.simple.simplethink.vip

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.alipay.sdk.app.PayTask
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.login.LoginActivity
import com.example.simple.simplethink.model.*
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


/**
 * Created by mobileteam on 2019/6/21.
 */
class VIPCenterActivity : BaseActivity(), VIPCenterContact.View {

    lateinit var vipItemAdapter: VIPItemAdapter
    lateinit var persenter: VIPCenterContact.Presenter
    var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private val SDK_PAY_FLAG = 1
    private val SDK_AUTH_FLAG = 2

    companion object {
        const val PAYRESULT = "PAYRESULT"
        fun newIntent(context: Context?): Intent {
            var intent = Intent(context, VIPCenterActivity::class.java)
            return intent
        }
        fun newIntent(context: Context?,code : String?): Intent {
            var intent = Intent(context, VIPCenterActivity::class.java)
            intent.putExtra(PAYRESULT,code)
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
        result?.let {

            if(result == "支付成功" ){persenter.confirmWechatOrder(AuthInstance.getInstance().orderId)}
            else{Toast.makeText(this, result as String, Toast.LENGTH_LONG).show()}
             }
    }

    private fun init() {
        val httpResposityImpl = HttpResposityImpl()
        persenter = VIPCenterPresenter(httpResposityImpl, this)
        if(!SharedPreferencesUtil.getString(this,AUTH).isNullOrEmpty()) {
            persenter.getSubscription()
        }
    }

    override fun updateVipItem(sub : SubscriptionResponse) {
        var isVip: Boolean= false
        val date = Date(System.currentTimeMillis())
        val startTime = sdf.parse(sub.user.start_at)
        val endTime = sdf.parse(sub.user.end_at)
        val expireTime = sub.user.end_at.substring(0,sub.user.end_at.indexOf(" "))
        if(belongCalendar(date,startTime,endTime)) {
            userInfo.text = String.format(getString(R.string.vip_date), expireTime)
            isVip = true
        }else{
            userInfo.text = ResourcesUtils.getString(R.string.not_vip)
        }
        vipItemAdapter = VIPItemAdapter(this, sub.common,isVip)
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

    override fun showPayDialog(orderId: String) {
        val items = arrayOf("微信", "支付宝")
        val alertBuilder = AlertDialog.Builder(this)
                .setTitle("支付选择：")
                .setSingleChoiceItems(items, -1 , object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface, which : Int) {
                        when (which){
                            0 -> {persenter.wechatPay(orderId)}
                            1 -> {persenter.aliPay(orderId)}
                        }
                        dialog.dismiss()
                    }
                }).create()
        alertBuilder.show()
             /*   .setSingleChoiceItems(items, 0, DialogInterface.OnClickListener {
                    dialogInterface, i ->
                    AuthInstance.getInstance().orderId = orderId
                    when (i){
                        0 -> {persenter.wechatPay(orderId)}
                        1 -> {persenter.aliPay(orderId)}
                    }
                   // Toast.makeText(this@MainActivity, items[i], Toast.LENGTH_SHORT).show()
        })*/
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

    @SuppressLint("HandlerLeak")
    private val mHandler = object : Handler() {

        override fun handleMessage(msg: Message) {
            when (msg.what) {

                SDK_PAY_FLAG -> {
                    val payResult = PayResult(msg.obj as Map<String, String>)

                    val resultInfo = payResult.result// 同步返回需要验证的信息
                    val resultStatus = payResult.resultStatus
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {

                        persenter.confirmAlipayOrder(AuthInstance.getInstance().orderId)
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                       // Toast.makeText(this@VIPCenterActivity, "支付成功", Toast.LENGTH_SHORT).show()
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                       // Toast.makeText(this@VIPCenterActivity, "支付失败", Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {
                }
            }
        }
    }

    override fun sendAliPay(message: OrderAliPayResponse) {
        // 必须异步调用
        val payRunnable = Runnable {
            val alipay = PayTask(this)
            val result = alipay.payV2(message.str, true)
            Log.i("msp", result.toString())

            val msg = Message()
            msg.what = SDK_PAY_FLAG
            msg.obj = result
            mHandler.sendMessage(msg)
        }

        // 必须异步调用
        val payThread = Thread(payRunnable)
        payThread.start()
    }

}