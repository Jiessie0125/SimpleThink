package com.example.simple.simplethink.wxapi

import android.app.Activity
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import android.support.v4.media.session.MediaButtonReceiver.handleIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.simple.simplethink.vip.VIPCenterActivity
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.constants.ConstantsAPI






/**
 * Created by jiessie on 2019/8/21.
 */
class WXPayEntryActivity : Activity(), IWXAPIEventHandler {

    private var  api: IWXAPI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api = WXAPIFactory.createWXAPI(this, "wx19435760a0e8b89e");
        api?.handleIntent(getIntent(), this)
    }

    override fun onReq(p0: BaseReq?) {
         }

    override fun onResp(p0: BaseResp?) {

        if (p0?.getType() === ConstantsAPI.COMMAND_PAY_BY_WX) {
            val code = p0?.errCode
            var resultStr = "支付取消"
            when(code){
                0 -> {
                    Log.e("---", "----wechatPay--支付成功--" + 0)
                    resultStr = "支付成功"
                }
                -1 -> {
                    Log.e("---", "----wechatPay--支付失败--" + -1)
                    resultStr = "支付失败"
                }
                -2 -> {
                    Log.e("---", "----wechatPay--支付取消--" + -2)
                    resultStr = "支付取消"
                }
                else -> {
                    Log.e("---", "----wechatPay---支付失败--else" )
                }
            }
            showVipPage(resultStr)
        }
    }

    private fun showVipPage(reslut : String?){
        val vipIntent = VIPCenterActivity.newIntent(this,reslut)
        startActivity(vipIntent)
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        api?.handleIntent(intent, this)
    }
}