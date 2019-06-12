package com.example.simple.simplethink.totle.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.TotleItem
import com.example.simple.simplethink.model.TotleSortResponse
import com.example.simple.simplethink.totle.fragment.TotleFragment


/**
 * Created by jiessie on 2019/6/11.
 */
class TotleAdapter(val context : Context, val totleLish : List<TotleSortResponse>) : RecyclerView.Adapter<TotleViewHolder>() {

    override fun getItemCount(): Int {
        return totleLish.size
    }

    override fun onBindViewHolder(holder: TotleViewHolder?, position: Int) {
        holder?.mTotleItem?.text = totleLish.get(position).category_name
        holder?.mItemImage?.setImageBitmap(totleLish.get(position).image)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TotleViewHolder {
        val holder = TotleViewHolder(LayoutInflater.from(context).inflate(R.layout.totle_item,parent,false))
        return holder
    }

}

class TotleViewHolder(view : View?): RecyclerView.ViewHolder(view) {
    var mTotleItem : TextView?
    var mItemImage : ImageView?

    init {
        mTotleItem = view?.findViewById(R.id.recycle_tv)
        mItemImage = view?.findViewById(R.id.recycle_img)
    }

}


