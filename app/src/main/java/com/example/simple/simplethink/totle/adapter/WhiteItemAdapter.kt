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
import com.example.simple.simplethink.model.TotleItem
import com.example.simple.simplethink.model.WhiteNoiseItemResponse
import com.example.simple.simplethink.utils.DownloadHelper
import com.example.simple.simplethink.utils.FilesUtils
import java.io.File
import java.util.ArrayList

/**
 * Created by jiessie on 2019/6/11.
 */
class WhiteItemAdapter( val context: Activity,val totleLish: List<WhiteNoiseItemResponse>) : RecyclerView.Adapter<WhiteItemViewHolder>() {

    private var mClickListener : OnWhiteItemClickListener ?= null
    private var mPosition = -1
    private var isShow = false

    override fun getItemCount(): Int {
        return totleLish?.size!!
    }

    override fun onBindViewHolder(holder: WhiteItemViewHolder?, position: Int) {
        holder?.mTotleItem?.text = totleLish?.get(position)?.title
        FilesUtils.showImage(totleLish?.get(position)?.img, context, holder?.mItemImage)
        if(isShow && mPosition == position){
            holder?.mSelectedItemImage?.visibility = View.VISIBLE
        }else{
            holder?.mSelectedItemImage?.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WhiteItemViewHolder? {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.white_noise_item, null, false)
        return WhiteItemViewHolder(holder,mClickListener)
    }

   fun setOnItemClickListener( listener : OnWhiteItemClickListener){
       this.mClickListener = listener
   }

    fun changetShowDelImage(isShow: Boolean,position: Int) {
        mPosition = position
        this.isShow = isShow
        notifyDataSetChanged()
    }

}

class WhiteItemViewHolder(view : View?, private val mListener: OnWhiteItemClickListener?): RecyclerView.ViewHolder(view),View.OnClickListener{
    var mTotleItem : TextView?
    var mItemImage : ImageView?
    var mSelectedItemImage : ImageView?

    init {
        mTotleItem = view?.findViewById(R.id.recycle_white_txt)
        mItemImage = view?.findViewById(R.id.recycle_white_sign)
        mSelectedItemImage = view?.findViewById(R.id.white_selected)

        view?.setOnClickListener (this)
    }

    override fun onClick(v: View?) {
        mListener?.onItemClick(v, getPosition())
    }

}

interface OnWhiteItemClickListener {
    fun onItemClick(v: View?, position: Int)
}