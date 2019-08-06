package com.example.simple.simplethink.totle

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
import com.example.simple.simplethink.R
import com.example.simple.simplethink.totle.activity.down.DownloadActivity
import com.example.simple.simplethink.totle.fragment.scenePage.SceneFragment
import com.example.simple.simplethink.totle.fragment.totlePage.TotleFragment
import com.example.simple.simplethink.totle.fragment.whiteNoisePage.WhithNoiseFragment
import com.example.simple.simplethink.utils.ResourcesUtils
import kotlinx.android.synthetic.main.activity_totle.*

/**
 * Created by mobileteam on 2019/6/3.
 */
class TotleActivity: AppCompatActivity() {

    val tabTitles = arrayOf(ResourcesUtils.getString(R.string.totle),
            ResourcesUtils.getString(R.string.scene),
            ResourcesUtils.getString(R.string.whith_noise))
    private lateinit var mTabLayout : TabLayout
    private lateinit var mViewPager : ViewPager
    private val mFragments = ArrayList<Fragment>()
    private var holder : ViewHolder?= null

    companion object {
        fun newIntent (context: Context) : Intent {
            var intent = Intent(context, TotleActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_totle)
        init()
    }

    fun init(){
        user.setOnClickListener{finish()}
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
}

internal class ViewHolder(tabView: View?) {
    var mTabItem: TextView?

    init {
        mTabItem = tabView?.findViewById(R.id.title_item)
    }
}