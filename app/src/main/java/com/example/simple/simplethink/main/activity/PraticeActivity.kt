package com.example.simple.simplethink.main.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.main.adapter.OnCourseClickListener
import com.example.simple.simplethink.main.adapter.PracticeDetailsAdapter
import com.example.simple.simplethink.model.PracticeResponse
import com.example.simple.simplethink.totle.activity.course.CourseDetailActivity
import com.example.simple.simplethink.utils.ResourcesUtils
import kotlinx.android.synthetic.main.activity_pratice.*
import kotlinx.android.synthetic.main.title_tool.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by mobileteam on 2019/6/21.
 */
class PraticeActivity : BaseActivity(){

    lateinit var sortedMap: HashMap<String, ArrayList<PracticeResponse>>
    var practiceDetailsList = ArrayList<Any>()
    lateinit var practiceAdapter: PracticeDetailsAdapter


    companion object {
        fun newIntent(context: Context?): Intent {
            var intent = Intent(context, PraticeActivity::class.java)
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
        setContentView(R.layout.activity_pratice)
        setHeader(ResourcesUtils.getString(R.string.pratice_center))
        init()
    }

    private fun init() {
        sortedMap = intent.getSerializableExtra("sortedPracticeMap") as HashMap<String, ArrayList<PracticeResponse>>
        convertMapAsListData()
        setPracticeDetailsAdapter()
    }

    private fun convertMapAsListData(){

        sortedMap.let {
            val keys = sortedMap.keys.sorted().reversed()
            var count = 0;
            for(key in keys){
                practiceDetailsList.add(key)
                sortedMap.get(key)?.let { it1 ->
                    it1.forEach { item ->
                        practiceDetailsList.add(item)
                    }
                }
                practiceDetailsList.add(count)
                count++
            }
        }
    }

    fun setPracticeDetailsAdapter(){
        practiceAdapter = PracticeDetailsAdapter(this, practiceDetailsList)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_pratice.layoutManager = layoutManager
        rv_pratice.adapter = practiceAdapter
        practiceAdapter.notifyDataSetChanged()
        practiceAdapter.setOnItemClickListener(object : OnCourseClickListener {
            override fun onItemClick(v: View?, position: Int) {
                val course = practiceDetailsList[position] as PracticeResponse
                showCourseDetail(course.course.id)
            }
        })
    }

    private fun showCourseDetail(title: Int) {
        var courseActivity = CourseDetailActivity.newIntent(title, this)
        startActivity(courseActivity)
    }
}