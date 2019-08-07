package com.example.simple.simplethink.totle.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.simple.simplethink.R

/**
 * Created by jiessie on 2019/6/11.
 */
class DownloadSmallItemAdapter(val context: Activity, val vipArray : ArrayList<String>) : RecyclerView.Adapter<DownloadSmallItemAdapter.DownloadSmallHolder>(), View.OnClickListener {

    private var mClickListener : OnDownloadSmallItemClickListener?= null
    override fun getItemCount(): Int {
        return vipArray?.size!!
    }

    override fun onBindViewHolder(holder: DownloadSmallHolder?, position: Int) {
        holder?.mNameItem?.text = vipArray[position]
        holder?.mDeleteItem?.tag = position
        holder?.mPlayItem?.tag = position
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DownloadSmallHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.download_small_item, null, false)
        return DownloadSmallHolder(holder)
    }

    fun setOnItemClickListener( listener : OnDownloadSmallItemClickListener){
        this.mClickListener = listener
    }

   inner class DownloadSmallHolder(view : View?): RecyclerView.ViewHolder(view){
        var mNameItem : TextView?
        var mDeleteItem : ImageView?
        var mPlayItem : ImageView?

        init {
            mNameItem = view?.findViewById(R.id.download_small_name_ry)
            mDeleteItem = view?.findViewById(R.id.download_small_delete_ry)
            mPlayItem = view?.findViewById(R.id.download_small_play_ry)

            mPlayItem?.setOnClickListener(this@DownloadSmallItemAdapter)
            mDeleteItem?.setOnClickListener(this@DownloadSmallItemAdapter)
        }
    }

    interface OnDownloadSmallItemClickListener {
        fun onItemClick(v: View?, position: Int)
    }

    override fun onClick(v: View?) {
        val position = v?.getTag()      //getTag()获取数据
        if (mClickListener != null) {
            when (v?.getId()) {
                R.id.download_delete_ry -> mClickListener?.onItemClick(v, position as Int)
                R.id.download_small_play_ry -> mClickListener?.onItemClick(v,  position as Int)
            }
        }
    }

}