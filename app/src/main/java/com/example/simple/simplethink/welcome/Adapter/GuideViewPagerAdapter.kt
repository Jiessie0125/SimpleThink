package com.example.simple.simplethink.welcome.Adapter

import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup

/*我们需要使用适配器来帮助我们更方便的使用ViewPager*/

class GuideViewPagerAdapter(private val views: List<View>?) : PagerAdapter() {

    override fun getCount(): Int {
        return views?.size ?: 0
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(views!![position])
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        (container as ViewPager).addView(views!![position], 0)
        return views[position]
    }
}