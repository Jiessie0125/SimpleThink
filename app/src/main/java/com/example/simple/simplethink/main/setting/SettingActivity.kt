package com.example.simple.simplethink.main.setting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.simple.simplethink.R
import kotlinx.android.synthetic.main.title_tool.*

/**
 * Created by jiessie on 2019/8/6.
 */
class SettingActivity : Activity() {

    companion object {
        fun newIntent (context: Context?) : Intent {
            var intent = Intent(context,SettingActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        init()
    }
    private fun init(){
        title_tool_back.setOnClickListener { finish() }
    }
}