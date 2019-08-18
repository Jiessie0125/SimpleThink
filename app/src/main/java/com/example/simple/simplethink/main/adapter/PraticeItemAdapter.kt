package com.example.simple.simplethink.main.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.SuggestedCourse
import com.example.simple.simplethink.utils.ResourcesUtils
import com.example.simple.simplethink.utils.URLConstant

/**
 * Created by jiessie on 2019/6/11.
 */
class PraticeItemAdapter(val context: Activity, val totallist : List<SuggestedCourse>) : RecyclerView.Adapter<PraticeItemViewHolder>() {

    private var mClickListener : OnPraticeItemClickListener?= null
    override fun getItemCount(): Int {
        return totallist?.size!!
    }

    override fun onBindViewHolder(holder: PraticeItemViewHolder?, position: Int) {
        holder?.mTotleItem?.text = totallist?.get(position)?.title
        Glide.with(context).load(totallist?.get(position)?.title_img_new).into(holder?.mItemImage!!)
        when(totallist[position].type_new){
            URLConstant.VIP -> {
                holder?.mCourseViptem?.visibility = View.VISIBLE
                holder?.mCourseViptem?.text = ResourcesUtils.getString(R.string.vip)
                holder?.mCourseViptem?.background = ResourcesUtils.resource.getDrawable(R.color.fisrtUse)
            }
            URLConstant.FREE -> {
                holder?.mCourseViptem?.visibility = View.VISIBLE
                holder?.mCourseViptem?.text = ResourcesUtils.getString(R.string.free)
                holder?.mCourseViptem?.background = ResourcesUtils.resource.getDrawable(R.color.logonButton)
            }
            else ->{
                holder?.mCourseViptem?.visibility = View.GONE
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PraticeItemViewHolder? {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.course_item, null, false)
        return PraticeItemViewHolder(holder, mClickListener)
    }
    fun setOnItemClickListener( listener : OnPraticeItemClickListener){
        this.mClickListener = listener
    }
}

class PraticeItemViewHolder(view : View?, private val mListener: OnPraticeItemClickListener?): RecyclerView.ViewHolder(view), View.OnClickListener {
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

interface OnPraticeItemClickListener {
    fun onItemClick(v: View?, position: Int)
}