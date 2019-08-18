package com.example.simple.simplethink.main.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.simple.simplethink.R

/**
 * Created by jiessie on 2019/6/11.
 */
class PraticeAdapter(val context: Activity, val totallist : List<String>) : RecyclerView.Adapter<PraticeViewHolder>() {

    override fun getItemCount(): Int {
        return totallist.size
    }

    override fun onBindViewHolder(holder: PraticeViewHolder?, position: Int) {
        holder?.mTotleItem?.text = totallist[position]
        holder?.mPraticeRecyclerView?.tag = position
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PraticeViewHolder? {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.pratice_item, null, false)
        return PraticeViewHolder(holder)
    }
}

class PraticeViewHolder(view : View?): RecyclerView.ViewHolder(view) {
    var mTotleItem : TextView?
    var mPraticeRecyclerView : RecyclerView?

    init {
        mTotleItem = view?.findViewById(R.id.recycle_pratice_time)
        mPraticeRecyclerView = view?.findViewById(R.id.pratice_item_rv)
    }


}
