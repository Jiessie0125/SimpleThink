package com.example.simple.simplethink.totle

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.example.simple.simplethink.R
import com.example.simple.simplethink.netapi.HttpResposityImpl
import kotlinx.android.synthetic.main.activity_totle.*

/**
 * Created by mobileteam on 2019/6/3.
 */
class TotleActivity: AppCompatActivity(){

    lateinit var persenter : TotleContact.Presenter
    val tabTitles = arrayOf("全部","场景","白噪音")
    private lateinit var mTabLayout : TabLayout
    private lateinit var mViewPager : ViewPager
    private val mFragments = ArrayList<TotleFragment>()

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
        for (i in 0 until tabTitles.size) {
            mFragments.add(totleFragment.createFragment(tabTitles[i]))
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
    }
}

/*class viewPagerAdapter : FragmentPagerAdapter{
    var fragments: MutableList<Fragment> = ArrayList()

    constructor(fm: FragmentManager) : super(fm) {
        fragments.add(Fragment1())
        fragments.add(Fragment2())
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int = fragments.size

}*/