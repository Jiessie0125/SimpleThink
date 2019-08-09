package com.example.simple.simplethink.main.setting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import com.example.simple.simplethink.R
import com.example.simple.simplethink.login.ProtocolActivity
import kotlinx.android.synthetic.main.activity_app_version.*

/**
 * Created by mobileteam on 2019/8/8.
 */
class AppVersionActivity : Activity() {
    companion object {
        fun newIntent (context: Context?) : Intent {
            var intent = Intent(context,AppVersionActivity::class.java)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_version)
        registerTitle.setOnClickListener {
            finish();
        }
        login_policy.text = Html.fromHtml(getString(R.string.login_policy))
        login_policy.setOnClickListener {
            val intent = ProtocolActivity.newIntent(this)
            startActivity(intent)
        }
    }
}