package com.example.simple.simplethink.totle.adapter

import android.app.Activity
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
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.FilesUtils.downloadImage
import java.util.ArrayList


/**
 * Created by jiessie on 2019/6/11.
 */
class TotleAdapter(val context:Activity ,val totleLish : List<TotleSortResponse>) : RecyclerView.Adapter<TotleViewHolder>() {

    private var mClickListener : OnTotleItemClickListener ?= null
    override fun getItemCount(): Int {
        return totleLish?.size!!
    }

    override fun onBindViewHolder(holder: TotleViewHolder?, position: Int) {
        holder?.mTotleItem?.text = totleLish?.get(position)?.category_name
        FilesUtils.getItemIcon(totleLish?.get(position)?.category_name,context,holder?.mItemImage)
       // holder?.mItemImage?.setImageBitmap(totleLish?.get(position)?.totleItemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TotleViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.totle_item, null, false)
        return TotleViewHolder(holder,mClickListener)
    }

    /*fun setData(totleList : ArrayList<TotleSortResponse>){
        totleLish = totleList
        notifyDataSetChanged()
    }*/

    fun setOnItemClickListener( listener : OnTotleItemClickListener){
        this.mClickListener = listener
    }

}

class TotleViewHolder(view : View?, private val mListener: OnTotleItemClickListener?): RecyclerView.ViewHolder(view),View.OnClickListener {
    var mTotleItem : TextView?
    var mItemImage : ImageView?

    init {
        mTotleItem = view?.findViewById(R.id.recycle_tv)
        mItemImage = view?.findViewById(R.id.recycle_img)

        view?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        mListener?.onItemClick(v, getPosition())
    }
}

interface OnTotleItemClickListener {
    fun onItemClick(v: View?, position: Int)
}


