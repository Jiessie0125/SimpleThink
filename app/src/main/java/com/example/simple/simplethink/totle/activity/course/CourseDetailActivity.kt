package com.example.simple.simplethink.totle.activity.course

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.model.bean.CourseResponse
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.activity.ScenePlayActivity
import com.example.simple.simplethink.totle.adapter.CourseDetailAdapter
import com.example.simple.simplethink.totle.adapter.CourseDetailAdapter.Companion.COURSEDETAIL
import com.example.simple.simplethink.utils.FilesUtils
import kotlinx.android.synthetic.main.activity_course_detail.*

/**
 * Created by jiessie on 2019/7/28.
 */
class CourseDetailActivity: BaseActivity(), CourseDetailContact.View{

    lateinit var coursePresenter : CourseDetailContact.Presenter
    lateinit var courseDetailAdapter : CourseDetailAdapter
    private var isManager =false

    companion object {
        const val COURSEID = "COURSEID"
        fun newIntent (item : Int, context: Context?) : Intent {
            var intent = Intent(context,CourseDetailActivity::class.java)
            intent.putExtra(COURSEID,item)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)
        init()
    }

    private fun init(){
        var intent = getIntent()
        var courseItem = intent.getSerializableExtra(COURSEID) as Int
        val httpResposityImpl = HttpResposityImpl()
        coursePresenter = CourseDetailPresenter(httpResposityImpl,this)
        coursePresenter.getCourse(courseItem)
        course_back.setOnClickListener { finish() }
    }

    override fun setCourseAdapter(courseResponse : CourseResponse) {
        course_title.text = courseResponse.title
        content_detail.text = courseResponse.content_new
        course_detail_totle.text = "("+ courseResponse.section_total_count+")"
        FilesUtils.showImage(courseResponse.title_img_new, this, content_bk)
        course_detail_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        courseDetailAdapter = CourseDetailAdapter(this,courseResponse.sections)
        course_detail_rv.adapter = courseDetailAdapter
        courseDetailAdapter.notifyDataSetChanged()
        courseDetailAdapter.setOnItemClickListener(object : CourseDetailAdapter.OnCourseDetailItemClickListener {
            override fun onItemClick(v: View?, position: Int) {
                when(v?.id){
                    R.id.course_download -> downloadCourseMP3(position)
                    R.id.course_play -> showSceneResourcePage(courseResponse.sections[position].title,
                            FilesUtils.getLocalFileUrl(courseResponse.sections[position].title,COURSEDETAIL),null)
                }
            }
        })
    }

    private fun downloadCourseMP3(position :Int){
        isManager = !isManager
        courseDetailAdapter.changetShowDelImage(isManager ,position)
    }

    private fun showSceneResourcePage(sceneName : String, sceneSource : String, bkground: String?){
        val intent = ScenePlayActivity.newIntent(this,sceneName,sceneSource,bkground)
        startActivity(intent)
    }
}