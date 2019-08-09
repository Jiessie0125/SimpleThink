package com.example.simple.simplethink.totle.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.VIPItem

/**
 * Created by jiessie on 2019/6/11.
 */
class DownloadItemAdapter(val context: Activity) : RecyclerView.Adapter<DownloadItemAdapter.DownloadHolder>(), View.OnClickListener  {

    private var mClickListener : OnDownloadItemClickListener?= null
    var hidenClass : String =""
    var vipArray = ArrayList<String>()

    override fun getItemCount(): Int {
        return vipArray?.size!!
    }

    override fun onBindViewHolder(holder: DownloadHolder?, position: Int) {
        holder?.mNameItem?.text = vipArray[position]
        holder?.mDeleteItem?.tag = position
        holder?.mDownloadRelative?.tag = position
        if(hidenClass == holder?.mNameItem?.text){
            holder?.mDownloadRelative?.visibility = View.GONE
        }else{
            holder?.mDownloadRelative?.visibility = View.VISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DownloadHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.download_item, null, false)
        return DownloadHolder(holder, mClickListener)
    }

    fun setOnItemClickListener( listener : OnDownloadItemClickListener){
        this.mClickListener = listener
    }

    /*fun setData(bigClassName : String){
        hidenClass = bigClassName
        notifyDataSetChanged()
    }*/
    fun setData(vipArray : ArrayList<String>){
        this.vipArray = vipArray
        notifyDataSetChanged()
    }

   inner class DownloadHolder(view : View?, private val mListener: OnDownloadItemClickListener?): RecyclerView.ViewHolder(view){
        var mNameItem : TextView?
        var mDeleteItem : ImageView?
        var mDownloadRelative : RelativeLayout?

        init {
            mNameItem = view?.findViewById(R.id.download_name_ry)
            mDeleteItem = view?.findViewById(R.id.download_delete_ry)
            mDownloadRelative = view?.findViewById(R.id.download_relative)

            mDownloadRelative?.setOnClickListener(this@DownloadItemAdapter)
            mDeleteItem?.setOnClickListener(this@DownloadItemAdapter)
        }
    }

    interface OnDownloadItemClickListener {
        fun onItemClick(v: View?, position: Int)
    }

    override fun onClick(v: View?) {
        val position = v?.getTag()      //getTag()获取数据
        if (mClickListener != null) {
            when (v?.getId()) {
                R.id.download_delete_ry -> mClickListener?.onItemClick(v, position as Int)
                else -> mClickListener?.onItemClick(v,  position as Int)
            }
        }
    }

}

