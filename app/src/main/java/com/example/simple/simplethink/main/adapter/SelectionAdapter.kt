package com.example.simple.simplethink.main.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.ActivityResponse
import com.example.simple.simplethink.utils.DateUtils
import java.util.*

/**
 * Created by jiessie on 2019/6/11.
 */
class SelectionAdapter(val context: Activity, val selectionList : ArrayList<ActivityResponse>) : RecyclerView.Adapter<SelectionViewHolder>() {

    private var mClickListener : OnSelectionitemClickListener ?= null

    override fun getItemCount(): Int {
        return selectionList.size
    }

    override fun onBindViewHolder(holder: SelectionViewHolder?, position: Int) {
        holder?.selectionTitle?.text = selectionList[position].title
        holder?.selectionSubTitle?.text = selectionList[position].subtitle
        val date = DateUtils.DateToString(Date(), DateUtils.DATE_TO_STRING_DETAIAL_PATTERN)
        if (date >= selectionList[position]?.start_time.toString() && date <= selectionList[position]?.end_time.toString()) {
            Glide.with(context!!).load(selectionList[position].imgURL).apply(RequestOptions().placeholder(R.drawable.sugges_activity)).into(holder?.selectionImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SelectionViewHolder? {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.selection_detail_item, null, false)
        return SelectionViewHolder(holder,mClickListener)
    }

    fun setOnItemClickListener( listener : OnSelectionitemClickListener){
        this.mClickListener = listener
    }
}

class SelectionViewHolder(view : View?, private val mListener: OnSelectionitemClickListener?): RecyclerView.ViewHolder(view),View.OnClickListener {
    var selectionTitle : TextView?
    var selectionSubTitle : TextView?
    var selectionImage: ImageView?

    init {
        selectionTitle = view?.findViewById(R.id.selection_title_detail)
        selectionSubTitle = view?.findViewById(R.id.selection_sub_title_detail)
        selectionImage = view?.findViewById(R.id.selection_image_detail)
        view?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        mListener?.onItemClick(v, getPosition())
    }
}
interface OnSelectionitemClickListener {
    fun onItemClick(v: View?, position: Int)
}
