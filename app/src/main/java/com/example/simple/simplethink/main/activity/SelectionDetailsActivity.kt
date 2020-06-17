package com.example.simple.simplethink.main.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.main.adapter.OnSelectionitemClickListener
import com.example.simple.simplethink.main.adapter.SelectionAdapter
import com.example.simple.simplethink.model.ActivityResponse
import com.example.simple.simplethink.totle.activity.course.CourseDetailActivity
import com.example.simple.simplethink.utils.ResourcesUtils
import com.example.simple.simplethink.vip.VIPCenterActivity
import com.example.simple.simplethink.welcome.Activity.AdvertisementActivity
import kotlinx.android.synthetic.main.activity_selection_detail.*
import kotlinx.android.synthetic.main.title_tool.*

/**
 * Created by mobileteam on 2019/8/23.
 */
class SelectionDetailsActivity: BaseActivity() {

    lateinit var selectionList: ArrayList<ActivityResponse>
    lateinit var selectionAdapter: SelectionAdapter

    companion object {
        fun newIntent(context: Context?): Intent {
            var intent = Intent(context, SelectionDetailsActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_detail)
        setHeader(ResourcesUtils.getString(R.string.selection_edit))
        selectionList = intent.getSerializableExtra("selectionList") as ArrayList<ActivityResponse>
        setSelectionAdapter()
    }

    override fun setHeader(title: String) {
        super.setHeader(title)
        title_tool_id.text = title
        title_tool_back_all.setOnClickListener { finish() }
    }

    private fun setSelectionAdapter(){
        selectionAdapter = SelectionAdapter(this, selectionList)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_selection.layoutManager = layoutManager
        rv_selection.adapter = selectionAdapter
        selectionAdapter.notifyDataSetChanged()
        selectionAdapter.setOnItemClickListener(object : OnSelectionitemClickListener {
            override fun onItemClick(v: View?, position: Int) {
                val selection = selectionList[position] as ActivityResponse
                redirector(selection)
            }
        })
    }

    private fun redirector(acitivityResponse: ActivityResponse?) {
        when (acitivityResponse?.tag) {
            "vip" -> {
                val intent = VIPCenterActivity.newIntent(this)
                startActivity(intent)
            }
            "lessions" -> {
                val intent = CourseDetailActivity.newIntent(acitivityResponse.lessionsID.toInt(),this)
                startActivity(intent)

            }
            "sign" -> {
            }
            "advertisment" -> enterActivity(AdvertisementActivity::class.java, "", acitivityResponse)
        }

    }

    private fun enterActivity(activity: Class<*>, from: String, acitivityResponse: ActivityResponse?) {


        val intent = Intent(this, activity)

       /* if (from == "") {
            intent.putExtra("from", "main")
        } else {*/
            intent.putExtra("from", from)
        //}
        acitivityResponse?.let {
            intent.putExtra("ActivityResponse", it)
        }
        startActivity(intent)
       // finish()
    }

}