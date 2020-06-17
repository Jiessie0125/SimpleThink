package com.example.simple.simplethink.vip

import android.app.Activity
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.Common
import com.example.simple.simplethink.model.TotleSortResponse
import com.example.simple.simplethink.model.VIPItem
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.ResourcesUtils

/**
 * Created by jiessie on 2019/6/11.
 */
class VIPItemAdapter(val context: Activity, val vipArray : List<Common>?,val isVip: Boolean?) : RecyclerView.Adapter<VIPViewHolder>() {

    private var mClickListener : OnVIPItemClickListener?= null
    override fun getItemCount(): Int {
        return vipArray?.size!!
    }

    override fun onBindViewHolder(holder: VIPViewHolder?, position: Int) {
        holder?.mVipDateItem?.text = vipArray?.get(position)?.title
        holder?.mVipMoneyImage?.text = ResourcesUtils.getString(R.string.vip_money_sign) + vipArray?.get(position)?.price.toString()
        if(!vipArray?.get(position)?.discount_price!!.equals( 0.00)){
            holder?.mVipDiscountMoneyImage?.visibility = View.VISIBLE
            holder?.mVipDiscountMoneyImage?.text = ResourcesUtils.getString(R.string.vip_money_sign) + vipArray?.get(position)?.price.toString().split(".")[0]
            holder?.mVipDiscountMoneyImage?.getPaint()?.setFlags(Paint.STRIKE_THRU_TEXT_FLAG)
            holder?.mVipMoneyImage?.text = ResourcesUtils.getString(R.string.vip_money_sign) + vipArray?.get(position)?.discount_price.toString().split(".")[0]
        }
        if(isVip!!) holder?.mVipBtn?.text = ResourcesUtils.getString(R.string.renew)
        holder?.mVipBtn?.tag = position
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VIPViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.vip_detail_item, null, false)
        return VIPViewHolder(holder, mClickListener)
    }

    fun setOnItemClickListener( listener : OnVIPItemClickListener){
        this.mClickListener = listener
    }
}

class VIPViewHolder(view : View?, private val mListener: OnVIPItemClickListener?): RecyclerView.ViewHolder(view), View.OnClickListener {
    var mVipDateItem : TextView?
    var mVipMoneyImage : TextView?
    var mVipDiscountMoneyImage : TextView?
    var mVipBtn : TextView?

    init {
        mVipDateItem = view?.findViewById(R.id.recycle_vip_date)
        mVipMoneyImage = view?.findViewById(R.id.recycle_vip_money)
        mVipDiscountMoneyImage = view?.findViewById(R.id.recycle_vip_discount_money)
        mVipBtn = view?.findViewById(R.id.course_play)

        view?.setOnClickListener(this)
        mVipBtn?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val position = v?.getTag()      //getTag()获取数据
        if (mListener != null) {
            when (v?.getId()) {
                R.id.course_play -> mListener?.onItemClick(v, position as Int)
            }
        }
    }
}

interface OnVIPItemClickListener {
    fun onItemClick(v: View?, position: Int)
}