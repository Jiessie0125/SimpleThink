package com.example.simple.simplethink.buzzy.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.BuzzyCourseResponse
import com.example.simple.simplethink.model.TotleItem
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.ResourcesUtils
import com.example.simple.simplethink.utils.URLConstant.FREE
import com.example.simple.simplethink.utils.URLConstant.VIP
import java.util.ArrayList

/**
 * Created by jiessie on 2019/6/11.
 */
class BuzzyCourseAdapter(val context : Activity,val totleLish : List<BuzzyCourseResponse> ) : RecyclerView.Adapter<CourseViewHolder>() {

    private var mClickListener : OnBuzzyItemClickListener ?= null
    override fun getItemCount(): Int {
        return totleLish?.size!!
    }

    override fun onBindViewHolder(holder: CourseViewHolder?, position: Int) {
        holder?.mTotleItem?.text = totleLish?.get(position)?.title
        FilesUtils.showImage(totleLish?.get(position)?.title_img_new,context,holder?.mItemImage)
        when(totleLish[position].type_new){
            VIP -> {
                holder?.mBuzzyVip?.visibility = View.VISIBLE
                holder?.mBuzzyVip?.text = ResourcesUtils.getString(R.string.vip)
                holder?.mBuzzyVip?.background = ResourcesUtils.resource.getDrawable(R.color.fisrtUse)
            }
            FREE -> {
                holder?.mBuzzyVip?.visibility = View.VISIBLE
                holder?.mBuzzyVip?.text = ResourcesUtils.getString(R.string.free)
                holder?.mBuzzyVip?.background = ResourcesUtils.resource.getDrawable(R.color.logonButton)
            }
            else ->{
                holder?.mBuzzyVip?.visibility = View.GONE
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CourseViewHolder? {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.buzzy_course_item, null, false)
        return CourseViewHolder(holder,mClickListener)
    }

    fun setOnItemClickListener( listener : OnBuzzyItemClickListener){
        this.mClickListener = listener
    }
}

class CourseViewHolder(view : View?, private val mListener: OnBuzzyItemClickListener?): RecyclerView.ViewHolder(view),View.OnClickListener {
    var mTotleItem : TextView?
    var mItemImage : ImageView?
    var mBuzzyVip : TextView?

    init {
        mTotleItem = view?.findViewById(R.id.recycle_buzzy_course_tv)
        mItemImage = view?.findViewById(R.id.recycle_buzzy_course_img)
        mBuzzyVip = view?.findViewById(R.id.recycle_buzzy_vip)

        view?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        mListener?.onItemClick(v, getPosition())
    }

}
interface OnBuzzyItemClickListener {
    fun onItemClick(v: View?, position: Int)
}