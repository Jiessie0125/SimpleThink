package com.example.simple.simplethink.totle.fragment.totlePage


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.simple.simplethink.R
import com.example.simple.simplethink.buzzy.BuzzyCourseActivity
import com.example.simple.simplethink.model.*
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.BaseFragment
import com.example.simple.simplethink.totle.activity.RecyclerViewSpacesItemDecoration
import com.example.simple.simplethink.totle.activity.course.CourseDetailActivity
import com.example.simple.simplethink.totle.adapter.CourseAdapter
import com.example.simple.simplethink.totle.adapter.OnCoursetemClickListener
import com.example.simple.simplethink.totle.adapter.OnTotleItemClickListener
import com.example.simple.simplethink.totle.adapter.TotleAdapter
import com.example.simple.simplethink.utils.DateUtils
import com.example.simple.simplethink.utils.LocalDataCache
import com.example.simple.simplethink.utils.ResourcesUtils
import com.example.simple.simplethink.utils.URLConstant
import com.example.simple.simplethink.vip.VIPCenterActivity
import com.example.simple.simplethink.welcome.Activity.AdvertisementActivity
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_main_prelogon.*
import kotlinx.android.synthetic.main.fragment_totle.*
import kotlinx.android.synthetic.main.fragment_white_noise.*
import java.util.*
import kotlin.collections.ArrayList


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
    private var handler: Handler = Handler()
    private var runnable: Runnable = Runnable {}
    private var activityCount: Int = 0
    private val mHasLoadedOnce = false
    private val isPrepared = false
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


    override fun setBanner(message: List<BannerResponse>) {
        if (message.size == 0) {
            Glide.with(context!!).load(R.drawable.sugges_activity).into(mbanner)
            return
        }
        if (message.size == 1) {
            val suggestedActivity = message?.get(0)
            val date = DateUtils.DateToString(Date(), DateUtils.DATE_TO_STRING_DETAIAL_PATTERN)
            if (date >= suggestedActivity?.start_time.toString() && date <= suggestedActivity?.end_time.toString()) {
                Glide.with(context!!).load(message?.get(0).imgURL).into(mbanner)
                mbanner.setOnClickListener {
                    redirectorBanner(message?.get(0))
                }
            } else {
                Glide.with(context!!).load(R.drawable.sugges_activity).into(mbanner)
            }
            return
        }
        handler = Handler()
        runnable = object : Runnable {
            override fun run() {
                handler.postDelayed(this, 4000)

                if (activityCount == message.size) {
                    activityCount = 0
                }

                val date = DateUtils.DateToString(Date(), DateUtils.DATE_TO_STRING_DETAIAL_PATTERN)
                if (date >= message?.get(activityCount)?.start_time.toString() && date <= message?.get(activityCount)?.end_time.toString()) {
                    Glide.with(context!!).load(message?.get(activityCount).imgURL).into(mbanner)
                    activityCount++
                    mbanner.setOnClickListener {
                        redirectorBanner(message?.get(activityCount))
                    }
                }
            }
        }
        handler.post(runnable)
    }

    private fun redirectorBanner(bannerResponse: BannerResponse?) {
        when (bannerResponse?.tag) {
            "vip" -> {
                val intent = VIPCenterActivity.newIntent(context)
                startActivity(intent)
            }
            "lessions" -> {
                val intent = CourseDetailActivity.newIntent(bannerResponse.lessionsID!!.toInt(),context)
                startActivity(intent)
            }
            "sign" -> {
            }
            "advertisment" -> enterBannerActivity(AdvertisementActivity::class.java, "", bannerResponse)
        }

    }

    private fun enterBannerActivity(activity: Class<*>, from: String, bannerResponse: BannerResponse?) {

        val intent = Intent(context, activity)

        if (from == "") {
            intent.putExtra("from", "main")
        } else {
            intent.putExtra("from", from)
        }
        bannerResponse?.let {
            intent.putExtra("BannerResponse", it)
        }
        startActivity(intent)
        this.activity?.finish()
    }

//    override fun setBanner(bannerUrlList: ArrayList<String>) {
//        mbanner.setImageLoader(object : ImageLoader() {
//            override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
//                Glide.with(context).load(path).into(imageView)
//            }
//        })
//        mbanner.setDelayTime(4000)
//        mbanner.setImages(bannerUrlList)
//        mbanner.isAutoPlay(true)
//        mbanner.start()
//    }

    override fun setBuzzyItem(id: Int) {
        buzzyItems = id
    }

    private fun showBuzzyCourse(title: String,isBuzzy : Boolean) {
        var buzzyCourseActivity = BuzzyCourseActivity.newIntent(buzzyItems,this.activity, title,isBuzzy)
        startActivity(buzzyCourseActivity)
    }

    private fun showCourseDetail(title: Int?) {
        var courseActivity = CourseDetailActivity.newIntent(title!!,this.activity)
        startActivity(courseActivity)
    }

    override fun onResume() {
        super.onResume()
        totleList = ArrayList<TotleSortResponse>()
        courseList = ArrayList<TotleItem>()
    }

}