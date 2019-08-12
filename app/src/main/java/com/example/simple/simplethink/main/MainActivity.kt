package com.example.simple.simplethink.main

import android.Manifest
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.example.simple.simplethink.R
import com.example.simple.simplethink.login.LoginPresenter
import com.example.simple.simplethink.main.fragment.LogonBaseFragment
import com.example.simple.simplethink.main.fragment.PostLogonFragment
import com.example.simple.simplethink.main.fragment.PreLogonFragment
import com.example.simple.simplethink.main.setting.SettingActivity
import com.example.simple.simplethink.totle.TotleActivity
import com.example.simple.simplethink.utils.*
import com.example.simple.simplethink.utils.auth.AuthInstance
import com.example.simple.simplethink.widget.WaitDialog
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by mobileteam on 2019/6/3.
 */
class MainActivity : FragmentActivity(), PermissionInterface {
    var LOCATION: Int = 1
    var STORATE: Int = 2
    var showingFragment: LogonBaseFragment? = null
    val mainPersent = MainPresenter()
    val arrayOfStringCall: Array<String> = arrayOf(Manifest.permission.READ_PHONE_STATE
            , Manifest.permission.READ_CALL_LOG, Manifest.permission.CALL_PHONE,
            Manifest.permission.ADD_VOICEMAIL, Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.USE_SIP, Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PermissionUtils.requestPermissions(arrayOfStringCall, LOCATION, this, this)
        setting.setOnClickListener { showSettingPage() }
        home.setOnClickListener { showTotlePage() }
    }

    override fun onResume() {
        super.onResume()
        showMainContent()
    }

    private fun showMainContent() {
        showingFragment = if (AuthInstance.getInstance().userInfo != null) {
            PostLogonFragment.newInstance()
        } else {
            PreLogonFragment.newInstance()
        }
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_container, showingFragment)
        transaction.commit()
    }

    private fun showTotlePage() {
        val totlepage = TotleActivity.newIntent(this)
        startActivity(totlepage)
    }

    private fun showSettingPage() {
        val settingIntent = SettingActivity.newIntent(this)
        startActivity(settingIntent)
    }

    override fun requestPermissionsSuccess(callBackCode: Int) {
//        when (callBackCode) {
//            LOCATION -> PermissionUtils.requestPermissions(arrayOfStringStorage,2,this,this)
//            STORATE -> PermissionUtils.requestPermissions(arrayOfStringLocation,3,this,this)
//        }

    }

    override fun requestPermissionsFail(callBackCode: Int) {
//        when (callBackCode) {
//            LOCATION -> PermissionUtils.requestPermissions(arrayOfStringStorage,2,this,this)
//            STORATE -> PermissionUtils.requestPermissions( arrayOfStringLocation,3,this,this)
//        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        PermissionUtils.requestPermissionsResult(requestCode, permissions as Array<String>
                , grantResults!!); // 接管结果判断
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
