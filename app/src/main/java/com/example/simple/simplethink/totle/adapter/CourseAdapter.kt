package com.example.simple.simplethink.totle.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.TotleItem
import java.util.ArrayList

/**
 * Created by jiessie on 2019/6/11.
 */
class CourseAdapter( ) : RecyclerView.Adapter<CourseViewHolder>() {

    var totleLish = ArrayList<TotleItem>()

    override fun getItemCount(): Int {
        return totleLish?.size!!
    }

    override fun onBindViewHolder(holder: CourseViewHolder?, position: Int) {
        holder?.mTotleItem?.text = totleLish?.get(position)?.totleItemTxt
        holder?.mItemImage?.setImageBitmap(totleLish?.get(position)?.totleItemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CourseViewHolder? {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.course_item, null, false)
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
        mTotleItem = view?.findViewById(R.id.recycle_course_tv)
        mItemImage = view?.findViewById(R.id.recycle_course_img)
    }

}