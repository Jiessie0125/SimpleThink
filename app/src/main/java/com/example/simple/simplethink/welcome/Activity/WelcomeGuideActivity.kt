package com.example.simple.simplethink.welcome.Activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout


import com.example.simple.simplethink.R
import com.example.simple.simplethink.main.MainActivity
import com.example.simple.simplethink.utils.SharedPreferencesUtil
import com.example.simple.simplethink.welcome.Adapter.GuideViewPagerAdapter

import java.util.ArrayList

class WelcomeGuideActivity : Activity(), View.OnClickListener {
    private var viewPager: ViewPager? = null
    private var adapter: GuideViewPagerAdapter? = null
    private var views: MutableList<View>? = null
    private var startBtn: Button? = null
    private var linearLayout: LinearLayout? = null

    /*底部小点图片*/
    private var dots: Array<ImageView?>? = null

    /*用于记录当前选中位置*/
    private var currentIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        views = ArrayList()

        /*初始化引导页视图列表,需要对资源进行处理*/
        for (i in pics.indices) {
            val view = LayoutInflater.from(this).inflate(pics[i], null)

            if (i == pics.size - 1) {
                startBtn = view.findViewById(R.id.vp_guide_btn)
                /*这里使用setTag方法进行标注。在View中的setTag(Onbect)表示给View
                添加一个格外的数据，以后可以用getTag()将这个数据取出来。可以用在
                多个Button添加一个监听器，每个Button都设置不同的setTag。这个监听
                器就通过getTag来分辨是哪个Button 被按下。*/
                startBtn!!.tag = "enter"
                startBtn!!.setOnClickListener(this)
            }
            views!!.add(view)
        }

        viewPager = findViewById<View>(R.id.vp_guide) as ViewPager
        /*初始化adapter*/
        adapter = GuideViewPagerAdapter(views)
        viewPager!!.adapter = adapter
        /*需要设置页面改变的监听器，这样我们能把我页面改变时的具体操作细节，所以
        需要创建PageChangeListener，实现OnPageChangeListener接口*/
        viewPager!!.addOnPageChangeListener(PageChangeListener())
        initDots()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        /*如果切换到后台，就设置下次不进入功能引导页*/
        SharedPreferencesUtil.setBoolean(this@WelcomeGuideActivity, SharedPreferencesUtil.FIRST_OPEN, false)
        finish()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initDots() {
        linearLayout = findViewById<View>(R.id.ll) as LinearLayout
        dots =  arrayOfNulls(pics.size)

        /*循环取得小点图片*/
        for (i in pics.indices) {
            /*得到一个LinearLayout下面的每一个子元素*/
            dots!![i] = linearLayout!!.getChildAt(i) as ImageView
            dots!![i]!!.isSelected = false//设置成灰色
            dots!![i]!!.setOnClickListener(this)
            dots!![i]!!.tag = i//设置位置tag，方便取出与当前位置对应,原理同上
        }
        currentIndex = 0
        dots!![currentIndex]!!.isSelected = true // 设置为白色，即选中状态
    }

    /**
     * 设置当前view
     *
     * @param position
     */
    private fun setCurrentView(position: Int) {
        if (position < 0 || position > pics.size) {
            return
        }
        viewPager!!.currentItem = position
    }

    /**
     * 设置当前指示点
     *
     * @param position
     */
    private fun setCurDot(position: Int) {
        if (position < 0 || position > pics.size || currentIndex == position) {
            return
        }
        dots!![position]!!.isSelected = true
        dots!![currentIndex]!!.isSelected = false
        currentIndex = position
        if(position === pics.size -1){
            linearLayout?.visibility = View.GONE
        }else{
            linearLayout?.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View) {
        if (v.tag == "enter") {
            enterMainActivity()
            return
        }
        val position = v.tag as Int
        setCurrentView(position)
        setCurDot(position)
    }

    private fun enterMainActivity() {
        val intent = Intent(this@WelcomeGuideActivity, MainActivity::class.java)
        startActivity(intent)
        SharedPreferencesUtil.setBoolean(this@WelcomeGuideActivity, SharedPreferencesUtil.FIRST_OPEN, false)
        finish()
    }

    private inner class PageChangeListener : ViewPager.OnPageChangeListener {
        /*当滑动状态改变时调用*/

        override fun onPageScrollStateChanged(state: Int) {
            /*arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。*/
        }

        /*当前页面被滑动时调用*/

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            // arg0 :当前页面，及你点击滑动的页面
            // arg1:当前页面偏移的百分比
            // arg2:当前页面偏移的像素位置
        }

        /*当新的页面被选中时调用*/
        override fun onPageSelected(position: Int) {
            setCurDot(position)
        }
    }

    companion object {
        /*引导页图片资源*/
        private val pics = intArrayOf(R.layout.guid_view1, R.layout.guid_view2, R.layout.guid_view3,R.layout.guid_view4)
    }
}