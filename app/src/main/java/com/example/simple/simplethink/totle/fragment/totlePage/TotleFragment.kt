package com.example.simple.simplethink.totle.fragment.totlePage


import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.simple.simplethink.R
import com.example.simple.simplethink.buzzy.BuzzyCourseActivity
import com.example.simple.simplethink.model.Course
import com.example.simple.simplethink.model.FirstCourseResponse
import com.example.simple.simplethink.model.TotleItem
import com.example.simple.simplethink.model.TotleSortResponse
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.activity.RecyclerViewSpacesItemDecoration
import com.example.simple.simplethink.totle.activity.course.CourseDetailActivity
import com.example.simple.simplethink.totle.adapter.CourseAdapter
import com.example.simple.simplethink.totle.adapter.OnCoursetemClickListener
import com.example.simple.simplethink.totle.adapter.OnTotleItemClickListener
import com.example.simple.simplethink.totle.adapter.TotleAdapter
import com.example.simple.simplethink.utils.LocalDataCache
import com.example.simple.simplethink.utils.ResourcesUtils
import com.example.simple.simplethink.utils.URLConstant
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_totle.*
import kotlinx.android.synthetic.main.fragment_white_noise.*


/**
 * Created by jiessie on 2019/6/5.
 */
class TotleFragment : Fragment(), TotleContact.View {

    lateinit var persenter: TotleContact.Presenter
    var totleList = ArrayList<TotleSortResponse>()
    var courseList = ArrayList<TotleItem>()
    lateinit var totleAdapter: TotleAdapter
    lateinit var courseAdapter: CourseAdapter
    var buzzyItems = 0
    //lateinit var getTotleSort : List<TotleSortResponse>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_totle, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        totleMore.setOnClickListener { showBuzzyCourse(ResourcesUtils.getString(R.string.suggestion_course),true) }
    }

    private fun initView() {
        showLocalDataView()
    }

    fun showLocalDataView(){
        val httpResposityImpl = HttpResposityImpl()
        persenter = TotlePresenter(httpResposityImpl, this,this.activity!!)
        persenter.getBanner()
         var getTotleSort  = LocalDataCache.getLocalData(URLConstant.GETTOTLESORT)
         var getCourseImage  = LocalDataCache.getLocalData(URLConstant.GETCOURSEIMAGE)
        if(getTotleSort == null){ persenter.getTotleSort() }
        else{
           // for (i in 0 until (getTotleSort as List<TotleSortResponse>).size) {
                setTotleIcon(getTotleSort as List<TotleSortResponse>)
           // }
        }
        if(getCourseImage == null){persenter.getCourse()}
        else{
           // for (i in 0 until (getCourseImage as FirstCourseResponse).courses.size) {
                setCourseIcon((getCourseImage as FirstCourseResponse).courses)
           // }
        }
         getCourseImage?.let {
             setBuzzyItem((getCourseImage as FirstCourseResponse).id)
         }
    }



    fun createFragment(): TotleFragment {
        val bundle = Bundle()
        val fragment = TotleFragment()
        fragment.setArguments(bundle)
        return fragment
    }

    override fun getTotleSortIcon(isLocal: Boolean,list: List<TotleSortResponse>) {
        /*for (i in 0 until list.size) {
            if (isLocal){
                var itemBitmap = FilesUtils.getItemIcon(list[i].category_name,"png")
                getItemImage(list[i].category_name,itemBitmap)
            } else{
                persenter.getItemImage(list[i].image, list[i].category_name)
            }
        }*/
    }

    override fun getItemImage(imageName: String, image: Bitmap?) {
      /*  image?.let {
            var totleItem = TotleItem(imageName, image)
            totleList?.add(totleItem)
        }
        totleAdapter.setData(totleList)*/
    }

    override fun setTotleIcon(message: List<TotleSortResponse>) {
        totleAdapter = TotleAdapter(this.activity!!,message)
        recycle_tv.layoutManager = GridLayoutManager(this.context, 4)
        recycle_tv.adapter = totleAdapter
        totleAdapter.notifyDataSetChanged()
        totleAdapter.setOnItemClickListener(object : OnTotleItemClickListener{
            override fun onItemClick(v: View?, position: Int) {
                buzzyItems = message[position].id
                showBuzzyCourse(message[position].category_name,false)
            }
        })
    }
    override fun setCourseIcon(courses : List<Course>) {
        courseAdapter = CourseAdapter(this.activity!!,courses)
        recycle_course_tv.layoutManager = GridLayoutManager(this.context, 2)
        val stringIntegerHashMap = HashMap< String, Int>()
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, 50)//右间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION, 50)
        recycle_course_tv.addItemDecoration(RecyclerViewSpacesItemDecoration(stringIntegerHashMap))
        recycle_course_tv.adapter = courseAdapter
        courseAdapter.notifyDataSetChanged()
        courseAdapter.setOnItemClickListener(object :OnCoursetemClickListener{
            override fun onItemClick(v: View?, position: Int) {
                showCourseDetail(courses[position].id)
            }
        })
    }

    override fun setCourseAdapterView(isLocal: Boolean,list: List<Course>) {
        /*for (i in 0 until list.size) {
            if (isLocal){
                var itemBitmap = FilesUtils.getItemIcon(list[i].title,"jpg")
                getCourseImageView(list[i].title,itemBitmap)
            } else{
                persenter.getCourseImage(list[i].title_img_new, list[i].title)
            }
        }*/
    }

    override fun getCourseImageView(imageName: String, image: Bitmap?) {
      /*  image?.let {
            var totleItem = TotleItem(imageName, image)
            courseList?.add(totleItem)
        }
        courseAdapter.setData(courseList)*/
    }

    override fun setBanner(bannerUrlList: ArrayList<String>) {
        mbanner.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
                Glide.with(context).load(path).into(imageView)
            }
        })
        mbanner.setDelayTime(4000)
        mbanner.setImages(bannerUrlList)
        mbanner.isAutoPlay(true)
        mbanner.start()
    }

    override fun setBuzzyItem(id: Int) {
        buzzyItems = id
    }

    private fun showBuzzyCourse(title: String,isBuzzy : Boolean) {
        var buzzyCourseActivity = BuzzyCourseActivity.newIntent(buzzyItems,this.activity, title,isBuzzy)
        startActivity(buzzyCourseActivity)
    }

    private fun showCourseDetail(title: Int) {
        var courseActivity = CourseDetailActivity.newIntent(title,this.activity)
        startActivity(courseActivity)
    }

    override fun onResume() {
        super.onResume()
        totleList = ArrayList<TotleSortResponse>()
        courseList = ArrayList<TotleItem>()
    }
}