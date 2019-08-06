package com.example.simple.simplethink.totle.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.VIPItem

/**
 * Created by jiessie on 2019/6/11.
 */
class DownloadItemAdapter(val context: Activity, val vipArray : ArrayList<String>) : RecyclerView.Adapter<DownloadHolder>() {

    private var mClickListener : OnTotleItemClickListener?= null
    override fun getItemCount(): Int {
        return vipArray?.size!!
    }

    override fun onBindViewHolder(holder: DownloadHolder?, position: Int) {
        holder?.mNameItem?.text = vipArray[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DownloadHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.download_item, null, false)
        return DownloadHolder(holder, mClickListener)
    }

    fun setOnItemClickListener( listener : OnTotleItemClickListener){
        this.mClickListener = listener
    }

}

class DownloadHolder(view : View?, private val mListener: OnTotleItemClickListener?): RecyclerView.ViewHolder(view), View.OnClickListener {
    var mPlayItem : ImageView?
    var mNameItem : TextView?
    var mDeleteItem : ImageView?

    init {
        mPlayItem = view?.findViewById(R.id.download_play_ry)
        mNameItem = view?.findViewById(R.id.download_name_ry)
        mDeleteItem = view?.findViewById(R.id.download_delete_ry)

        view?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        mListener?.onItemClick(v, getPosition())
    }
}

interface OnDownloadItemClickListener {
    fun onItemClick(v: View?, position: Int)
}