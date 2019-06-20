package com.example.simple.simplethink.totle.fragment.totlePage


import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.Course
import com.example.simple.simplethink.model.TotleItem
import com.example.simple.simplethink.model.TotleSortResponse
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.adapter.CourseAdapter
import com.example.simple.simplethink.totle.adapter.TotleAdapter
import kotlinx.android.synthetic.main.fragment_totle.*
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by jiessie on 2019/6/5.
 */
class TotleFragment : Fragment(), TotleContact.View {

    lateinit var persenter : TotleContact.Presenter
    var totleList = ArrayList<TotleItem>()
    var courseList = ArrayList<TotleItem>()
    lateinit var totleAdapter : TotleAdapter
    lateinit var courseAdapter : CourseAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_totle,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /*for(i in 0 until 8){
            var totleItem = TotleItem("1",BitmapFactory.decodeResource(ResourcesUtils.resource,R.drawable.download))
            totleList.add(totleItem)
        }*/
        initView()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun initView(){
        val httpResposityImpl = HttpResposityImpl()
        persenter = TotlePresenter(httpResposityImpl, this)
        persenter.getTotleSort()
        persenter.getCourse()
        setAdapter()
        setCourseAdapter()

    }

    fun createFragment(): TotleFragment {
        val bundle = Bundle()
        val fragment = TotleFragment()
        fragment.setArguments(bundle)
        return fragment
    }

    override fun getTotleSortIcon(list: List<TotleSortResponse>) {
        for(i in 0 until list.size){
            persenter.getItemImage(list[i].image,list[i].category_name)
        }
    }

    override fun getItemImage(imageName : String,image: Bitmap)  {
        image?.let {
            var totleItem = TotleItem(imageName,image)
            totleList?.add(totleItem)
        }
      totleAdapter.setData(totleList)
    }

    private fun setAdapter(){
        totleAdapter = TotleAdapter()
        recycle_tv.layoutManager = GridLayoutManager(this.context,4)
        recycle_tv.adapter = totleAdapter
    }

    private fun setCourseAdapter(){
        courseAdapter = CourseAdapter()
        recycle_course_tv.layoutManager = GridLayoutManager(this.context,2)
        recycle_course_tv.adapter = courseAdapter
    }

    override fun setCourseAdapterView(list: List<Course>) {
        for(i in 0 until list.size){
            persenter.getCourseImage(list[i].title_img_new,list[i].title)
        }
    }

    override fun getCourseImageView(imageName: String, image: Bitmap) {
        image?.let {
            var totleItem = TotleItem(imageName,image)
            courseList?.add(totleItem)
        }
        courseAdapter.setData(courseList)
    }

    override fun onResume() {
        super.onResume()
        totleList = ArrayList<TotleItem>()
        courseList = ArrayList<TotleItem>()
    }
}