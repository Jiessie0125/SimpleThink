package com.example.simple.simplethink.main.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import kotlinx.android.synthetic.main.title_tool.*

/**
 * Created by mobileteam on 2019/8/8.
 */
class FeedBackActivity : BaseActivity() {
    companion object {
        fun newIntent (context: Context?) : Intent {
            var intent = Intent(context,FeedBackActivity::class.java)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        setHeader(getString(R.string.app_query_title))
    }


    override fun setHeader(title: String) {
        super.setHeader(title)
        title_tool_id.text = title
        title_tool_back.setOnClickListener { finish() }
    }
}