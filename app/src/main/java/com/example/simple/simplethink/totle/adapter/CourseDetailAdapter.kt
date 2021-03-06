package com.example.simple.simplethink.totle.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.PraticeSections
import com.example.simple.simplethink.model.bean.CourseSections
import com.example.simple.simplethink.totle.activity.view.ProgressBarView
import com.example.simple.simplethink.utils.DownloadHelper
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.FilesUtils.isHaveFile
import com.example.simple.simplethink.utils.ResourcesUtils
import com.example.simple.simplethink.utils.SharedPreferencesUtil
import java.io.File

/**
 * Created by jiessie on 2019/6/11.
 */
class CourseDetailAdapter(val context: Activity, val totleLish : List<CourseSections>, val courseTitle: String) : RecyclerView.Adapter<CourseDetailAdapter.CourseDetailViewHolder>() , View.OnClickListener {

    private var mClickListener : OnCourseDetailItemClickListener?= null
    private var mPosition = -1
    private var isShow = false
    private var mPratice : PraticeSections? = null
    var downloadAllPositon = 0
    var IsDownloadAllCourse = false

    override fun getItemCount(): Int {
        return totleLish?.size!!
    }

    override fun onBindViewHolder(holder: CourseDetailViewHolder?, position: Int) {
        holder?.mTotleItem?.text = totleLish?.get(position)?.title
        holder?.mItemName?.text = FilesUtils.dateCovent(totleLish[position].main_duration)
        holder?.mCourseViptem?.tag = position
        holder?.mCoursePlay?.tag = position
        if(isHaveFile(totleLish?.get(position)?.title,courseTitle)){
            holder?.mRelativeLayout?.background = ResourcesUtils.resource.getDrawable(R.color.courseDetailDownload)
            holder?.mCoursePlay?.visibility = View.VISIBLE
            holder?.mCourseViptem?.visibility = View.GONE
        }else if(/*isShow &&*/ mPosition == position){
            holder?.mItemPercent?.visibility = View.VISIBLE
            holder?.mCourseViptem?.visibility = View.GONE
            updateProcessBar(totleLish[position].url,totleLish[position].title,holder?.mProgressItem)
        }
        else{
            holder?.mItemPercent?.visibility = View.GONE
            holder?.mRelativeLayout?.background = ResourcesUtils.resource.getDrawable(R.color.mainColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CourseDetailViewHolder? {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.course_detail_item, null, false)
        return CourseDetailViewHolder(holder)
    }
    fun setOnItemClickListener( listener : OnCourseDetailItemClickListener){
        this.mClickListener = listener
    }

    fun changetShowDelImage(isShow :Boolean ,position: Int,pratice : PraticeSections){
        mPosition = position
        this.isShow = isShow
        mPratice = pratice
        notifyDataSetChanged()
    }

    inner class CourseDetailViewHolder(view : View?): RecyclerView.ViewHolder(view) {
        var mTotleItem : TextView?
        var mItemName : TextView?
        var mCourseViptem : LinearLayout?
        var mItemPercent : TextView?
        var mProgressItem : ProgressBarView?
        var mCoursePlay : LinearLayout?
        var mRelativeLayout : RelativeLayout?

        init {
            mTotleItem = view?.findViewById(R.id.recycle_course_name)
            mItemName = view?.findViewById(R.id.recycle_course_time)
            mCourseViptem = view?.findViewById(R.id.course_download)
            mItemPercent = view?.findViewById(R.id.course_download_percent)
            mProgressItem = view?.findViewById(R.id.mProgessBar)
            mCoursePlay = view?.findViewById(R.id.course_play)
            mRelativeLayout = view?.findViewById(R.id.course_detail_item)

            mCourseViptem?.setOnClickListener(this@CourseDetailAdapter)
            mCoursePlay?.setOnClickListener(this@CourseDetailAdapter)
        }


    }

    interface OnCourseDetailItemClickListener {
        fun onItemClick(v: View?, position: Int)
    }

    override fun onClick(v: View?) {
        val position = v?.getTag()      //getTag()获取数据
        if (mClickListener != null) {
            when (v?.getId()) {
                R.id.course_download -> mClickListener?.onItemClick(v, position as Int)
                R.id.course_play -> mClickListener?.onItemClick(v, position as Int)
            }
        }
    }
    fun updateProcessBar(url: String,FILE_NAME: String, processBar: ProgressBarView?){
        val folder = context.getExternalFilesDir(courseTitle)
        if (!folder.exists()) {
            folder.mkdirs()
        }
        var localPath = folder.getPath() + File.separator + FILE_NAME
        DownloadHelper.download(url, localPath, object : DownloadHelper.OnDownloadListener {
            override fun onFail(file: File, failInfo: String?) {
                if(IsDownloadAllCourse){
                    downloadAllPositon = downloadAllPositon + 1
                    downloadAllCourse(downloadAllPositon)
                }
            }

            override fun onProgress(progress: Int?) {
                processBar?.progress = progress!!
            }

            override fun onStart() {
                processBar?.progress =0
            }

            override fun onSuccess(file: File) {
                notifyDataSetChanged()
                SharedPreferencesUtil.setGsonString(context,FILE_NAME,mPratice)
                if(IsDownloadAllCourse){
                    downloadAllPositon = downloadAllPositon + 1
                    downloadAllCourse(downloadAllPositon)
                }
            }
        })
    }

    fun downloadAllCourse(positon : Int){
        IsDownloadAllCourse = true
        if(positon == totleLish.size){
            return
        }else{
            val pratice = PraticeSections(totleLish[positon].id,totleLish[positon].course_id,totleLish[positon].audio_id)
            if(totleLish[positon].free == "true") changetShowDelImage(true,positon,pratice)
        }
    }

}
