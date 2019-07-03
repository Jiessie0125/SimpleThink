package com.example.simple.simplethink.buzzy.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.TotleItem
import com.example.simple.simplethink.utils.FilesUtils
import java.util.ArrayList

/**
 * Created by jiessie on 2019/6/11.
 */
class BuzzyCourseAdapter(val context : Activity ) : RecyclerView.Adapter<CourseViewHolder>() {

    var totleLish = ArrayList<TotleItem>()

    override fun getItemCount(): Int {
        return totleLish?.size!!
    }

    override fun onBindViewHolder(holder: CourseViewHolder?, position: Int) {
        holder?.mTotleItem?.text = totleLish?.get(position)?.totleItemTxt
        FilesUtils.showImage(totleLish?.get(position)?.totleItemImage,context,holder?.mItemImage)
       /* holder?.mItemImage?.setImageBitmap(totleLish?.get(position)?.totleItemImage)*/
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CourseViewHolder? {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.buzzy_course_item, null, false)
        return CourseViewHolder(holder)
    }

    fun setData(totleList : ArrayList<TotleItem>){
        totleLish = totleList
        notifyDataSetChanged()
    }
}

class CourseViewHolder(view : View?): RecyclerView.ViewHolder(view) {
    var mTotleItem : TextView?
    var mItemImage : ImageView?

    init {
        mTotleItem = view?.findViewById(R.id.recycle_buzzy_course_tv)
        mItemImage = view?.findViewById(R.id.recycle_buzzy_course_img)
    }

}