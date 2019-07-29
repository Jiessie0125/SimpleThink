package com.example.simple.simplethink.totle.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.Course
import com.example.simple.simplethink.model.TotleItem
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.ResourcesUtils
import com.example.simple.simplethink.utils.URLConstant.FREE
import com.example.simple.simplethink.utils.URLConstant.VIP
import java.util.ArrayList

/**
 * Created by jiessie on 2019/6/11.
 */
class CourseAdapter( val context: Activity, val totleLish : List<Course>) : RecyclerView.Adapter<CourseViewHolder>() {

    private var mClickListener : OnCoursetemClickListener ?= null
    override fun getItemCount(): Int {
        return totleLish?.size!!
    }

    override fun onBindViewHolder(holder: CourseViewHolder?, position: Int) {
        holder?.mTotleItem?.text = totleLish?.get(position)?.title
        FilesUtils.getItemIcon(totleLish?.get(position)?.title,context,holder?.mItemImage)
        when(totleLish[position].type_new){
            VIP -> {
                holder?.mCourseViptem?.visibility = View.VISIBLE
                holder?.mCourseViptem?.text = ResourcesUtils.getString(R.string.vip)
                holder?.mCourseViptem?.background = ResourcesUtils.resource.getDrawable(R.color.fisrtUse)
            }
            FREE -> {
                holder?.mCourseViptem?.visibility = View.VISIBLE
                holder?.mCourseViptem?.text = ResourcesUtils.getString(R.string.free)
                holder?.mCourseViptem?.background = ResourcesUtils.resource.getDrawable(R.color.logonButton)
            }
            else ->{
                holder?.mCourseViptem?.visibility = View.GONE
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CourseViewHolder? {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.course_item, null, false)
        return CourseViewHolder(holder,mClickListener)
    }
    fun setOnItemClickListener( listener : OnCoursetemClickListener){
        this.mClickListener = listener
    }
}

class CourseViewHolder(view : View?, private val mListener: OnCoursetemClickListener?): RecyclerView.ViewHolder(view),View.OnClickListener {
    var mTotleItem : TextView?
    var mItemImage : ImageView?
    var mCourseViptem : TextView?

    init {
        mTotleItem = view?.findViewById(R.id.recycle_course_tv)
        mItemImage = view?.findViewById(R.id.recycle_course_img)
        mCourseViptem = view?.findViewById(R.id.recycle_course_vip)
        view?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        mListener?.onItemClick(v, getPosition())
    }

}
interface OnCoursetemClickListener {
    fun onItemClick(v: View?, position: Int)
}

