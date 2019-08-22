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
class PracticeDetailsAdapter(val context: Activity, val practiceList : ArrayList<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mClickListener : OnCourseClickListener ?= null
    private val ITEM_HEADER = 1
    private val ITEM_CONTENT = 2
    private val ITEM_FOOTER = 3

    override fun getItemCount(): Int {
        return practiceList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        /**头部*/
        if (getItemViewType(position) == ITEM_HEADER) {
            var headerHolder =  holder as DateViewHolder;
            val date = practiceList.get(position) as String;
            headerHolder.date?.text = date
        }else if(getItemViewType(position) == ITEM_CONTENT){
            var contentHolder =  holder as CourseHolder;
            val course = practiceList.get(position) as PracticeResponse;
            contentHolder.courseTitle?.text = course.course.title
            contentHolder.sectionTitle?.text = course.section.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        val view:View
        if (viewType === ITEM_HEADER) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.pratice_detail_date, parent, false)
            return DateViewHolder(view)
        } else if (viewType === ITEM_CONTENT) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.pratice_detail_item, parent, false)
            return CourseHolder(view,mClickListener)
        } else if (viewType === ITEM_FOOTER) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.pratice_detail_footer, parent, false)
            return FootViewHolderViewHolder(view)
        }
        return null

    }
    fun setOnItemClickListener( listener : OnCourseClickListener){
        this.mClickListener = listener
    }


    override fun getItemViewType(position: Int): Int {

        if (practiceList.get(position) is String){
            return ITEM_HEADER;
        }else if (practiceList.get(position) is PracticeResponse){
            return ITEM_CONTENT;
        }else if(practiceList.get(position) is Int){
            return ITEM_FOOTER;
        }
        return ITEM_CONTENT;
    }
}

class DateViewHolder(view : View? ): RecyclerView.ViewHolder(view){
    var date : TextView?
    init {
        date = view?.findViewById(R.id.recycle_pratice_time)
    }

}

class FootViewHolderViewHolder(view : View? ): RecyclerView.ViewHolder(view){
}

class CourseHolder(view : View?, private val mListener: OnCourseClickListener?): RecyclerView.ViewHolder(view),View.OnClickListener {
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

interface OnCourseClickListener {
    fun onItemClick(v: View?, position: Int)
}
