package com.example.simple.simplethink.main.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.PracticeResponse

/**
 * Created by jiessie on 2019/6/11.
 */
class PraticeAdapter(val context: Activity, val practiceList : ArrayList<PracticeResponse>) : RecyclerView.Adapter<PraticeViewHolder>() {

    private var mClickListener : OnCourseClickListener ?= null

    override fun getItemCount(): Int {
        return practiceList.size
    }

    override fun onBindViewHolder(holder: PraticeViewHolder?, position: Int) {
        holder?.courseTitle?.text = practiceList[position].course.title
        holder?.sectionTitle?.text = practiceList[position].section.title
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PraticeViewHolder? {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder = layoutInflater.inflate(R.layout.pratice_date_item, null, false)
        return PraticeViewHolder(holder,mClickListener)
    }
    fun setOnItemClickListener( listener : OnCourseClickListener){
        this.mClickListener = listener
    }
}

class PraticeViewHolder(view : View?, private val mListener: OnCourseClickListener?): RecyclerView.ViewHolder(view),View.OnClickListener {
    var courseTitle : TextView?
    var sectionTitle : TextView?

    init {
        courseTitle = view?.findViewById(R.id.practice_course_title)
        sectionTitle = view?.findViewById(R.id.pratice_item_section_title)
        view?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        mListener?.onItemClick(v, getPosition())
    }
}
interface OnPractisetemClickListener {
    fun onItemClick(v: View?, position: Int)
}
