package com.example.simple.simplethink.totle.activity.course

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import cn.sharesdk.framework.Platform
import com.example.simple.simplethink.R
import com.example.simple.simplethink.base.BaseActivity
import com.example.simple.simplethink.model.PraticeSections
import com.example.simple.simplethink.model.bean.CourseResponse
import com.example.simple.simplethink.model.bean.CourseSections
import com.example.simple.simplethink.model.bean.ShareMediaBean
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.activity.ScenePlayActivity
import com.example.simple.simplethink.totle.adapter.CourseDetailAdapter
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.ShareMediaPopupWindow
import com.example.simple.simplethink.utils.SharePicturePopupWindow
import com.example.simple.simplethink.vip.VIPCenterActivity
import kotlinx.android.synthetic.main.activity_course_detail.*


/**
 * Created by jiessie on 2019/7/28.
 */
class CourseDetailActivity: BaseActivity(), CourseDetailContact.View{

    lateinit var coursePresenter : CourseDetailContact.Presenter
    lateinit var courseDetailAdapter : CourseDetailAdapter
    private val supportMediaList = arrayOf<String>(ShareMediaPopupWindow.WECHAT, ShareMediaPopupWindow.MOMENTS, ShareMediaPopupWindow.QQ, ShareMediaPopupWindow.QQSPACE, ShareMediaPopupWindow.WEIBO)
    private var isManager =false
    private val bean = ShareMediaBean()
    var downLoadCourse : List<CourseSections>? =null

    companion object {
        const val COURSEID = "COURSEID"
        const val REQUEST_CODE = 1
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


    private fun initBean() {
        bean.shareType = Platform.SHARE_WEBPAGE
        bean.title = "简单冥想"
        bean.text = "累了？来放松一下"
        val logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)
        bean.imageData = logo
        bean.url = "http://resource.simplemeditation.cn/icon/scene/background/qingchen-bg.png"
    }

    private fun showPopFormBottom() {
        val shareMediaPopupWindow = ShareMediaPopupWindow(this, supportMediaList, bean)
        shareMediaPopupWindow.showAtLocation(findViewById<View>(R.id.ad_popup_course), Gravity.BOTTOM, 0, 0)
    }


    private fun init(){
        var intent = getIntent()
        var courseItem = intent.getSerializableExtra(COURSEID) as Int
        val httpResposityImpl = HttpResposityImpl()
        coursePresenter = CourseDetailPresenter(httpResposityImpl,this)
        coursePresenter.getCourse(courseItem)
        course_back.setOnClickListener { finish() }
        course_share.setOnClickListener {
            initBean()
            showPopFormBottom()
        }
        download_all_course.setOnClickListener {
            downLoadCourse?.let {
                courseDetailAdapter.downloadAllCourse(0)
                /*for (index in 0..(downLoadCourse as List<CourseSections>).size-1){
                    isVipItem((downLoadCourse as List<CourseSections>)[index].free,index,false)
                }*/
            }
        }
    }

    override fun setCourseAdapter(courseResponse : CourseResponse) {
        downLoadCourse = courseResponse.sections
        course_title.text = courseResponse.title
        if(courseResponse.type_new == "vip"){course_vip_img.visibility = View.VISIBLE}
        content_detail.text = courseResponse.content_new
        course_detail_totle.text = "("+ courseResponse.section_total_count+")"
        FilesUtils.showImage(courseResponse.title_img_new, this, content_bk)
        course_detail_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        courseDetailAdapter = CourseDetailAdapter(this,courseResponse.sections,courseResponse.title)
        course_detail_rv.adapter = courseDetailAdapter
        courseDetailAdapter.notifyDataSetChanged()
        courseDetailAdapter.setOnItemClickListener(object : CourseDetailAdapter.OnCourseDetailItemClickListener {
            override fun onItemClick(v: View?, position: Int) {
                when(v?.id){
                    R.id.course_download -> isVipItem(courseResponse.sections[position].free,position,true)
                    R.id.course_play -> {
                        val pratice = PraticeSections(courseResponse.sections[position].id,courseResponse.sections[position].course_id,courseResponse.sections[position].audio_id)
                        showSceneResourcePage(courseResponse.sections[position].title,
                                FilesUtils.getLocalFileUrl(courseResponse.sections[position].title,courseResponse.title),null,pratice)

                    }}
            }
        })
    }

    private fun downloadCourseMP3(position :Int){
        isManager = !isManager
        courseDetailAdapter.changetShowDelImage(isManager ,position)
    }

    private fun isVipItem(free: String,position: Int,downloadOnly: Boolean){
        when(free){
            "true" -> downloadCourseMP3(position)
            "false" -> { if(downloadOnly)showVipPage() }
        }
    }

    private fun showSceneResourcePage(sceneName : String, sceneSource : String, bkground: String?,sections: PraticeSections){
        val intent = ScenePlayActivity.newIntent(this,sceneName,sceneSource,bkground,sections)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val courseName = data?.getStringExtra("courseName")
        if(requestCode == REQUEST_CODE && resultCode == ScenePlayActivity.RESULT_CODE){
            val picturePopupWindow = SharePicturePopupWindow(this, courseName!!)
            picturePopupWindow.showAtLocation(ad_popup_pic_course, Gravity.BOTTOM, 0, 0)
        }
    }

    private fun showVipPage(){
        val intent = VIPCenterActivity.newIntent(this)
        startActivity(intent)
    }
}