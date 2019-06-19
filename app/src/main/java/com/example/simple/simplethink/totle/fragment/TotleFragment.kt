package com.example.simple.simplethink.totle.fragment


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.TotleItem
import com.example.simple.simplethink.model.TotleSortResponse
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.adapter.CourseAdapter
import com.example.simple.simplethink.totle.adapter.TotleAdapter
import com.example.simple.simplethink.utils.FilesUtils
import com.example.simple.simplethink.utils.ResourcesUtils
import kotlinx.android.synthetic.main.fragment_totle.*
import java.util.*


/**
 * Created by jiessie on 2019/6/5.
 */
class TotleFragment : Fragment(),TotleContact.View {

    init {

    }

    lateinit var persenter : TotleContact.Presenter
    var totleList = ArrayList<TotleItem>()

    var itemImage : Bitmap ?= null
    lateinit var totleAdapter : TotleAdapter
    lateinit var courseAdapter : CourseAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_totle,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        for(i in 0 until 8){
            var totleItem = TotleItem("1",BitmapFactory.decodeResource(ResourcesUtils.resource,R.drawable.download))
            totleList.add(totleItem)
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        /*val httpResposityImpl = HttpResposityImpl()
        persenter = TotlePresenter(httpResposityImpl,this)
        persenter.getTotleSort()*/
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

    override fun getItemImage(imageName : String)  {
        val itemImage = FilesUtils.getItemIcon(imageName)
        itemImage?.let {
            /*totleItem?.totleItemTxt = imageName
            totleItem?.totleItemImage = itemImage
            totleList?.add(totleItem)*/
        }
    }

    private fun setAdapter(){
        totleAdapter = TotleAdapter(totleList)
        recycle_tv.layoutManager = GridLayoutManager(this.context,4)
        recycle_tv.adapter = totleAdapter
    }

    private fun setCourseAdapter(){
        courseAdapter = CourseAdapter(totleList)
        recycle_course_tv.layoutManager = GridLayoutManager(this.context,2)
        recycle_course_tv.adapter = courseAdapter
    }
}