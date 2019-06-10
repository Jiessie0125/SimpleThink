package com.example.simple.simplethink.totle

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.example.simple.simplethink.R
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.utils.ResourcesUtils
import kotlinx.android.synthetic.main.activity_totle.*

/**
 * Created by mobileteam on 2019/6/3.
 */
class TotleActivity: AppCompatActivity(){

    lateinit var persenter : TotleContact.Presenter
    val tabTitles = arrayOf(ResourcesUtils.getString(R.string.totle),
            ResourcesUtils.getString(R.string.scene),
            ResourcesUtils.getString(R.string.whith_noise))
    private lateinit var mTabLayout : TabLayout
    private lateinit var mViewPager : ViewPager
    private var mTitleText : TextView? = null
    private val mFragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_totle)
        init()
    }

    fun init(){
        val httpResposityImpl = HttpResposityImpl()
        persenter = TotlePresenter(httpResposityImpl)
        persenter.getTotleSort()
        mTabLayout = tablayout
        mViewPager = viewpager

        val totleFragment = TotleFragment()
        val scenceFragment = SceneFragment()
        val whithFragment = WhithNoiseFragment()
        mFragments.add(totleFragment.createFragment())
        mFragments.add(scenceFragment.createFragment())
        mFragments.add(whithFragment.createFragment())

        for (i in 0 until tabTitles.size) {
            val tab = mTabLayout.getTabAt(i)
            tab?.setCustomView(R.layout.tab_item)
        }

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
        mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                 updateTablayoutTitle(tab,true)
                mViewPager.setCurrentItem(tab!!.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                 updateTablayoutTitle(tab,false)
            }
        })
    }
    fun updateTablayoutTitle(tab : TabLayout.Tab?, isSelected : Boolean){
        mTitleText = tab?.customView?.findViewById<TextView>(R.id.title_item)
        if(isSelected){
           /* mTitleText?.isSelected = true
            mTitleText?.textSize = ResourcesUtils.resource.getDimensionPixelSize(R.dimen.first_use).toFloat()*/
        }else{
            mTitleText?.isSelected = false
            mTitleText?.setTextSize(36.0f)
        }
    }
}