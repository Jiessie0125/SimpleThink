package com.example.simple.simplethink.vip

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.buzzy.BuzzyCourseContact
import com.example.simple.simplethink.buzzy.adapter.BuzzyCourseAdapter
import com.example.simple.simplethink.model.VIPItem
import com.example.simple.simplethink.utils.ResourcesUtils
import kotlinx.android.synthetic.main.activity_vip_center.*
import kotlinx.android.synthetic.main.title_tool.*

/**
 * Created by mobileteam on 2019/6/21.
 */
class VIPCenterActivity : BaseActivity() {

    lateinit var vipItemAdapter : VIPItemAdapter
    var vipArray = ArrayList<VIPItem>()

    companion object {
        fun newIntent ( context: Context?) : Intent {
            var intent = Intent(context, VIPCenterActivity::class.java)
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

    private fun init(){
        var vip = VIPItem("1个月","¥10")
        vipArray.add(vip)
        vipItemAdapter = VIPItemAdapter(this,vipArray)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        vip_detail.layoutManager = layoutManager
        vip_detail.adapter = vipItemAdapter
        vipItemAdapter.notifyDataSetChanged()
    }
}