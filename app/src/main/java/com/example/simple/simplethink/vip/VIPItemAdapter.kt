package com.example.simple.simplethink.vip

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.TotleSortResponse
import com.example.simple.simplethink.model.VIPItem
import com.example.simple.simplethink.utils.FilesUtils

/**
 * Created by jiessie on 2019/6/11.
 */
class VIPItemAdapter(val context: Activity, val vipArray : ArrayList<VIPItem>) : RecyclerView.Adapter<TotleViewHolder>() {

    private var mClickListener : OnTotleItemClickListener?= null
    override fun getItemCount(): Int {
        return vipArray?.size!!
    }

    override fun onBindViewHolder(holder: TotleViewHolder?, position: Int) {
        holder?.mVipDateItem?.text = vipArray?.get(position)?.vipDate
        holder?.mVipMoneyImage?.text = vipArray[position].vipMoney
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TotleViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.vip_detail_item, null, false)
        return TotleViewHolder(holder, mClickListener)
    }

    fun setOnItemClickListener( listener : OnTotleItemClickListener){
        this.mClickListener = listener
    }

}

class TotleViewHolder(view : View?, private val mListener: OnTotleItemClickListener?): RecyclerView.ViewHolder(view), View.OnClickListener {
    var mVipDateItem : TextView?
    var mVipMoneyImage : TextView?

    init {
        mVipDateItem = view?.findViewById(R.id.recycle_vip_date)
        mVipMoneyImage = view?.findViewById(R.id.recycle_vip_money)

        view?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        mListener?.onItemClick(v, getPosition())
    }
}

interface OnTotleItemClickListener {
    fun onItemClick(v: View?, position: Int)
}