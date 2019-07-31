package com.example.simple.simplethink.main

import android.app.Activity
import android.os.Bundle
import com.example.simple.simplethink.R
import com.example.simple.simplethink.login.LoginViaPhoneNumActivity
import com.example.simple.simplethink.totle.TotleActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by mobileteam on 2019/6/3.
 */
class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        home.setOnClickListener { showTotlePage() }
        logon_register.setOnClickListener {
            startActivity(LoginViaPhoneNumActivity.newIntent(this))
        }
    }

    private fun showTotlePage(){
        val totlepage = TotleActivity.newIntent(this)
        startActivity(totlepage)
    }
}