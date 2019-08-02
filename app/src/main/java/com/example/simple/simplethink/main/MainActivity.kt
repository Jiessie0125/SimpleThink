package com.example.simple.simplethink.main

import android.Manifest
import android.app.Activity
import android.os.Bundle
import com.example.simple.simplethink.R
import com.example.simple.simplethink.login.LoginActivity
import com.example.simple.simplethink.login.LoginViaPhoneNumActivity
import com.example.simple.simplethink.totle.TotleActivity
import com.example.simple.simplethink.utils.PermissionInterface
import com.example.simple.simplethink.utils.PermissionUtils
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by mobileteam on 2019/6/3.
 */
class MainActivity : Activity(), PermissionInterface {
    override fun requestPermissionsSuccess(callBackCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun requestPermissionsFail(callBackCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        //PermissionUtils.requestPermissions(Manifest.permission.CAMERA,REQ_CODE_PICK_PHOTO);
    }

    private fun init() {
        home.setOnClickListener { showTotlePage() }
        logon_register.setOnClickListener {
            startActivity(LoginActivity.newIntent(this))
        }
    }

    private fun showTotlePage(){
        val totlepage = TotleActivity.newIntent(this)
        startActivity(totlepage)
    }
}