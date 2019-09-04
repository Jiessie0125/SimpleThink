package com.example.simple.simplethink.main.fragment

import android.content.Intent
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.simple.simplethink.MyApp
import com.example.simple.simplethink.R
import com.example.simple.simplethink.main.MainContract
import com.example.simple.simplethink.main.MainPresenter
import com.example.simple.simplethink.main.UserInfoActivity.UserInfoActivity
import com.example.simple.simplethink.main.activity.PraticeActivity
import com.example.simple.simplethink.main.activity.SelectionDetailsActivity
import com.example.simple.simplethink.main.adapter.CourseAdapter
import com.example.simple.simplethink.main.adapter.OnCoursetemClickListener
import com.example.simple.simplethink.main.adapter.OnCourseClickListener
import com.example.simple.simplethink.main.adapter.PraticeAdapter
import com.example.simple.simplethink.model.*
import com.example.simple.simplethink.totle.activity.RecyclerViewSpacesItemDecoration
import com.example.simple.simplethink.totle.activity.course.CourseDetailActivity
import com.example.simple.simplethink.utils.*
import com.example.simple.simplethink.utils.auth.AuthInstance
import com.example.simple.simplethink.vip.VIPCenterActivity
import com.example.simple.simplethink.welcome.Activity.AdvertisementActivity
import kotlinx.android.synthetic.main.activity_vip_center.*
import kotlinx.android.synthetic.main.fragment_main_postlogon.*
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by Ashur on 2019/8/7.
 */
class PostLogonFragment : LogonBaseFragment(),MainContract.View {

    private var presenter: MainContract.Presenter = MainPresenter()
    private var handler: Handler = Handler()
    private var runnable: Runnable = Runnable {}
    private var activityCount: Int = 0
    lateinit var courseAdapter: CourseAdapter
    lateinit var practiceAdapter: PraticeAdapter
    lateinit var practiceMap: HashMap<String, ArrayList<PracticeResponse>>
    var sortedPracticeMap = HashMap<String, ArrayList<PracticeResponse>>()
    var latestThreePractice = ArrayList<PracticeResponse>()
    lateinit var selectionList: List<ActivityResponse>
    var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    override fun onGetPracticeSuccess(message: Map<String, List<PracticeResponse>>?) {
        message?.let {
            practiceMap = message as HashMap<String, ArrayList<PracticeResponse>>
            recommend_course_list.visibility = View.GONE
            recommend_course_line.visibility = View.GONE
            recommend_practice_line.visibility = View.VISIBLE
            recommend_practice.visibility = View.VISIBLE
            sortPractice()
            getLatestThreePractice()
            setPracticeAdapter(latestThreePractice)
            return
        }
        presenter.getSuggestedCourse()
    }


    private fun sortPractice(){
        var keyArray: MutableSet<String> = practiceMap.keys
        var sortedKyes = keyArray.sorted().reversed()
        for(item: String in sortedKyes){
            practiceMap.get(item)?.let {
                sortedPracticeMap.put(item, it)
            }
        }
        sortedPracticeMap.let {
            practice_more.setOnClickListener {
                var intent = PraticeActivity.newIntent(context)
                intent.putExtra("sortedPracticeMap", sortedPracticeMap)
                startActivity(intent)
            }
        }
    }

    private fun getLatestThreePractice(){
        var counter = 0;
        for(item: String in sortedPracticeMap.keys.reversed()){
            sortedPracticeMap.get(item)?.let {
                it.forEach { item ->
                    if(counter == 3){
                        return
                    }
                    latestThreePractice.add(item)
                    counter++
                }
            }
        }
    }

    override fun onGetSuggestedActivitySuccess(message: List<ActivityResponse>) {
        selectionList = message
        selection_more.setOnClickListener {
            var intent = SelectionDetailsActivity.newIntent(context)
            intent.putExtra("selectionList", selectionList as Serializable)
            startActivity(intent)
        }
        setSuggestedActivity(message)
    }

    override fun onGetBottomActivitySuccess(message: BottomActivityResponse) {
    }

    override fun onGetSuggestedCourseSuccess(message: List<SuggestedCourse>) {
        setCouseAdapter(message)
        recommend_course_list.visibility = View.VISIBLE
        recommend_course_line.visibility = View.VISIBLE
        recommend_practice_line.visibility = View.GONE
        recommend_practice_list.visibility = View.GONE
    }

    override fun onFailure(e: Throwable) {
        ErrorHandler.showErrorWithToast(context!!, e)
    }

    companion object {
        fun newInstance(): LogonBaseFragment {
            return PostLogonFragment()
        }
    }

    private fun setPracticeAdapter(practiceList: ArrayList<PracticeResponse>) {
        practiceAdapter = PraticeAdapter(activity!!, practiceList)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recommend_practice.layoutManager = layoutManager
        recommend_practice.adapter = practiceAdapter
        practiceAdapter.notifyDataSetChanged()
        practiceAdapter.setOnItemClickListener(object : OnCourseClickListener {
            override fun onItemClick(v: View?, position: Int) {
                showCourseDetail(practiceList[position].course.id)
            }
        })
    }

    private fun setCouseAdapter(totalList: List<SuggestedCourse>) {
        courseAdapter = CourseAdapter(activity!!, totalList)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recommend_course.layoutManager = layoutManager
        val stringIntegerHashMap = HashMap<String, Int>()
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION, 25)
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION, 50)
        recommend_course.addItemDecoration(RecyclerViewSpacesItemDecoration(stringIntegerHashMap))
        recommend_course.adapter = courseAdapter
        courseAdapter.notifyDataSetChanged()
        courseAdapter.setOnItemClickListener(object : OnCoursetemClickListener {
            override fun onItemClick(v: View?, position: Int) {
                showCourseDetail(totalList[position].id)
            }
        })
    }

    private fun showCourseDetail(title: Int) {
        var courseActivity = CourseDetailActivity.newIntent(title, context)
        startActivity(courseActivity)
    }

    private fun setSuggestedActivity(message: List<ActivityResponse>) {
        if (message.size == 0) {
            Glide.with(context!!).load(R.drawable.sugges_activity).into(selection_image1)
            return
        }
        if (message.size == 1) {
            val suggestedActivity = message?.get(0)
            val date = DateUtils.DateToString(Date(), DateUtils.DATE_TO_STRING_DETAIAL_PATTERN)
            if (date >= suggestedActivity?.start_time.toString() && date <= suggestedActivity?.end_time.toString()) {
                Glide.with(context!!).load(message?.get(0).imgURL).apply(RequestOptions().placeholder(R.drawable.sugges_activity)).into(selection_image1)
                selection_title1.text = message.get(activityCount).title
                selection_sub_title1.text = message.get(activityCount).subtitle

                selection_image1.setOnClickListener {
                    redirector(message?.get(0))
                }
            } else {
                Glide.with(context!!).load(R.drawable.sugges_activity).into(selection_image1)
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
                    Glide.with(context!!).load(message?.get(activityCount).imgURL).apply(RequestOptions().placeholder(R.drawable.sugges_activity)).into(selection_image1)
                    selection_title1.text = message.get(activityCount).title
                    selection_sub_title1.text = message.get(activityCount).subtitle
                    activityCount++
                    selection_image1.setOnClickListener {
                        redirector(message?.get(activityCount))
                    }
                }
            }
        }
        handler.post(runnable)
    }

    private fun redirector(acitivityResponse: ActivityResponse?) {
        when (acitivityResponse?.tag) {
            "vip" -> {
                showActivity()
            }
            "lessions" -> {
                val intent = CourseDetailActivity.newIntent(acitivityResponse.lessionsID.toInt(),context)
                startActivity(intent)

            }
            "sign" -> {
            }
            "advertisment" -> enterActivity(AdvertisementActivity::class.java, "", acitivityResponse)
        }

    }

    private fun showActivity(){
        val intent = VIPCenterActivity.newIntent(context)
        startActivity(intent)
    }

    private fun enterActivity(activity: Class<*>, from: String, acitivityResponse: ActivityResponse?) {


        val intent = Intent(context, activity)

        if (from == "") {
            intent.putExtra("from", "main")
        } else {
            intent.putExtra("from", from)
        }
        acitivityResponse?.let {
            intent.putExtra("ActivityResponse", it)
        }
        startActivity(intent)
        this.activity?.finish()
    }

    override fun getLayoutId(): Int = R.layout.fragment_main_postlogon

    override fun initView() {
        initUserInfoView()
        presenter.bind(this)
        getDatas()
    }

    private fun getDatas() {
        presenter.getSubscription()
        presenter.getSuggestedActivity()
        presenter.getPracticeList()
    }

    private fun initUserInfoView() {
        val userInfo = AuthInstance.getInstance().userInfo
        Glide.with(context!!).load(userInfo?.avatar).apply(RequestOptions().placeholder(R.drawable.photo)).into(avatar)
        user_name.text = userInfo?.nickName
        totalTime.text = userInfo?.durationCount
        totalDate.text = userInfo?.continueDay

        avatar.setOnClickListener {
            val intent = UserInfoActivity.newIntent(context)
            startActivity(intent)
        }

        vip_link.setOnClickListener {
            showActivity()
        }
    }

    override fun onDestroy() {
        presenter.unbind()
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }

    override fun updateVipItem(sub: SubscriptionResponse) {
        val date = Date(System.currentTimeMillis())
        val startTime = sdf.parse(sub.user.start_at)
        val endTime = sdf.parse(sub.user.end_at)
        val expireTime = sub.user.end_at.substring(0,sub.user.end_at.indexOf(" "))
        if(FilesUtils.belongCalendar(date, startTime, endTime)) {
            vip_info.text = String.format(getString(R.string.vip_date), expireTime)
            vip_link.text = ResourcesUtils.getString(R.string.to_renew)
        }else{
            vip_info.text = ResourcesUtils.getString(R.string.not_vip)
            vip_link.text = ResourcesUtils.getString(R.string.to_buy)
        }
    }
}