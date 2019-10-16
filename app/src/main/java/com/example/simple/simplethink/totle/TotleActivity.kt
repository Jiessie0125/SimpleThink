package com.example.simple.simplethink.totle

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.simple.simplethink.R
import com.example.simple.simplethink.R.id.user
import com.example.simple.simplethink.main.MainActivity
import com.example.simple.simplethink.totle.activity.down.DownloadActivity
import com.example.simple.simplethink.totle.fragment.scenePage.SceneFragment
import com.example.simple.simplethink.totle.fragment.totlePage.TotleFragment
import com.example.simple.simplethink.totle.fragment.whiteNoisePage.WhithNoiseFragment
import com.example.simple.simplethink.utils.PermissionInterface
import com.example.simple.simplethink.utils.PermissionUtils
import com.example.simple.simplethink.utils.ResourcesUtils
import com.example.simple.simplethink.utils.SharedPreferencesUtil
import com.example.simple.simplethink.utils.auth.AuthInstance
import com.example.simple.simplethink.utils.auth.AuthInstance.Companion.AUTH
import com.example.simple.simplethink.utils.auth.AuthInstance.Companion.REFRESHTOKEN
import kotlinx.android.synthetic.main.activity_totle.*
import kotlinx.android.synthetic.main.fragment_main_postlogon.*

/**
 * Created by mobileteam on 2019/6/3.
 */
class TotleActivity: AppCompatActivity() , PermissionInterface {

    val tabTitles = arrayOf(ResourcesUtils.getString(R.string.totle),
            ResourcesUtils.getString(R.string.scene),
            ResourcesUtils.getString(R.string.whith_noise))
    private lateinit var mTabLayout : TabLayout
    private lateinit var mViewPager : ViewPager
    private val mFragments = ArrayList<Fragment>()
    private var holder : ViewHolder?= null
    private val presenter = TotlePresenter(this)
    var LOCATION: Int = 1
    var STORATE: Int = 2
    val arrayOfStringCall: Array<String> = arrayOf(Manifest.permission.READ_PHONE_STATE
            , Manifest.permission.READ_CALL_LOG, Manifest.permission.CALL_PHONE,
            Manifest.permission.ADD_VOICEMAIL, Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.USE_SIP, Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
    )

    companion object {
        fun newIntent (context: Context) : Intent {
            var intent = Intent(context, TotleActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PermissionUtils.requestPermissions(arrayOfStringCall, LOCATION, this, this)
        setContentView(R.layout.activity_totle)
        init()
    }

    fun init(){
        if(!SharedPreferencesUtil.getString(this,REFRESHTOKEN).isNullOrEmpty()){
            presenter.refreshToken(SharedPreferencesUtil.getString(this,REFRESHTOKEN))
        }
        user.setOnClickListener{showMainActivity()}
        download.setOnClickListener {  showDownClassActivity()}

        mTabLayout = tablayout
        mViewPager = viewpager

        val totleFragment = TotleFragment()
        val scenceFragment = SceneFragment()
        val whithFragment = WhithNoiseFragment()
        mFragments.add(totleFragment.createFragment())
        mFragments.add(scenceFragment.createFragment())
        mFragments.add(whithFragment.createFragment())
        initAdapter()
        initTabView()
    }

    fun initUserInfo(){
        val userInfo = AuthInstance.getInstance().userInfo
        Glide.with(this).load(userInfo?.avatar).apply(RequestOptions().placeholder(R.drawable.photo)).into(user)
    }

    private fun showMainActivity(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    private fun showDownClassActivity(){
        val downloadIntent = DownloadActivity.newIntent(this)
        startActivity(downloadIntent)
    }

    private fun initTabView(){
        for (i in 0 until tabTitles.size) {
            val tab = mTabLayout.getTabAt(i)
            tab?.setCustomView(R.layout.tab_item)
            holder = ViewHolder(tab?.customView)
            holder?.mTabItem?.text = tabTitles[i]
            if (i == 0){
                holder?.mTabItem?.isSelected = true
                holder?.mTabItem?.textSize = 20.0f 
                holder?.mTabItem?.setTextColor(ResourcesUtils.resource.getColor(R.color.wordWhite))
            }
        }

        mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                updateTablayoutTitle(tab,true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                updateTablayoutTitle(tab,false)
            }
        })
    }

    fun initAdapter(){
        mViewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return mFragments[position]
            }

            override fun getCount(): Int {
                return mFragments.size
            }

            /**
             * 为TabLayout中每一个tab设置标题
             */
            override fun getPageTitle(position: Int): CharSequence {
                return tabTitles[position]
            }
        }
        mTabLayout.setupWithViewPager(mViewPager)
        mTabLayout.tabMode = TabLayout.MODE_SCROLLABLE
    }

    fun updateTablayoutTitle(tab : TabLayout.Tab?, isSelected : Boolean){
        holder = ViewHolder(tab?.customView)
        if(isSelected){
            holder?.mTabItem?.isSelected = true
            holder?.mTabItem?.textSize = 20.0f
            holder?.mTabItem?.setTextColor(ResourcesUtils.resource.getColor(R.color.wordWhite))
            mViewPager.currentItem = tab?.position!!
        }else{
            holder?.mTabItem?.isSelected = false
            holder?.mTabItem?.textSize = 17.0f
            holder?.mTabItem?.setTextColor(ResourcesUtils.resource.getColor(R.color.totleTitle))
        }
    }
    override fun requestPermissionsSuccess(callBackCode: Int) {
//        when (callBackCode) {
//            LOCATION -> PermissionUtils.requestPermissions(arrayOfStringStorage,2,this,this)
//            STORATE -> PermissionUtils.requestPermissions(arrayOfStringLocation,3,this,this)
//        }

    }

    override fun requestPermissionsFail(callBackCode: Int) {
//        when (callBackCode) {
//            LOCATION -> PermissionUtils.requestPermissions(arrayOfStringStorage,2,this,this)
//            STORATE -> PermissionUtils.requestPermissions( arrayOfStringLocation,3,this,this)
//        }
    }
}

internal class ViewHolder(tabView: View?) {
    var mTabItem: TextView?

    init {
        mTabItem = tabView?.findViewById(R.id.title_item)
    }
}