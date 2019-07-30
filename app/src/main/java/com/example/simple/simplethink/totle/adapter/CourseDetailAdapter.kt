package com.example.simple.simplethink.totle.adapter

import android.app.Activity
import android.os.Environment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.Course
import com.example.simple.simplethink.model.bean.CourseSections
import com.example.simple.simplethink.utils.DownloadHelper
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.ResourcesUtils
import com.example.simple.simplethink.utils.URLConstant
import java.io.File

/**
 * Created by jiessie on 2019/6/11.
 */
class CourseDetailAdapter(val context: Activity, val totleLish : List<CourseSections>) : RecyclerView.Adapter<CourseDetailAdapter.CourseDetailViewHolder>() , View.OnClickListener {

    companion object {
        const val COURSEDETAIL = "COURSEDETAIL"
    }

    private var mClickListener : OnCourseDetailItemClickListener?= null
    private var mPosition = -1
    private var isShow = false

    override fun getItemCount(): Int {
        return totleLish?.size!!
    }

    override fun onBindViewHolder(holder: CourseDetailViewHolder?, position: Int) {
        holder?.mTotleItem?.text = totleLish?.get(position)?.title
        holder?.mItemName?.text = FilesUtils.timeParse(totleLish[position].main_duration.toLong())
        holder?.mCourseViptem?.tag = position
        if(isShow && mPosition == position){
            holder?.mItemPercent?.visibility = View.VISIBLE
            holder?.mCourseViptem?.visibility = View.GONE
            updateProcessBar(totleLish[position].url,totleLish[position].title)
        }else{
            holder?.mItemPercent?.visibility = View.GONE
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

    fun changetShowDelImage(isShow :Boolean ,position: Int){
        mPosition = position
        this.isShow = isShow
        notifyDataSetChanged()
    }

    inner class CourseDetailViewHolder(view : View?): RecyclerView.ViewHolder(view) {
        var mTotleItem : TextView?
        var mItemName : TextView?
        var mCourseViptem : ImageView?
        var mItemPercent : TextView?

        init {
            mTotleItem = view?.findViewById(R.id.recycle_course_name)
            mItemName = view?.findViewById(R.id.recycle_course_time)
            mCourseViptem = view?.findViewById(R.id.course_download)
            mItemPercent = view?.findViewById(R.id.course_download_percent)

            mCourseViptem?.setOnClickListener(this@CourseDetailAdapter)
        }


    }

    interface OnCourseDetailItemClickListener {
        fun onItemClick(v: View?, position: Int)
    }

    override fun onClick(v: View?) {
        val position = v?.getTag()      //getTag()获取数据
        if (mClickListener != null) {
            when (v?.getId()) {
                R.id.scene_download -> mClickListener?.onItemClick(v, position as Int)
            }
        }
    }
    fun updateProcessBar(url: String,FILE_NAME: String){
        var filePath = Environment.getExternalStorageDirectory().toString() + File.separator +COURSEDETAIL
        val folder = File(filePath)
        if (!folder.exists()) {
            folder.mkdirs()
        }
        var localPath = folder.getPath() + File.separator + FILE_NAME
        DownloadHelper.download(url, localPath, object : DownloadHelper.OnDownloadListener {
            override fun onFail(file: File, failInfo: String?) {
            }

            override fun onProgress(progress: Int?) {

            }

            override fun onStart() {

            }

            override fun onSuccess(file: File) {
            }
        })
    }
}