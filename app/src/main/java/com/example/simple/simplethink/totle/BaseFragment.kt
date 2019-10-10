package com.example.simple.simplethink.totle

import android.support.v4.app.Fragment

/**
 * Created by jiessie on 2019/10/6.
 */

abstract class BaseFragment : Fragment() {


    /** Fragment当前状态是否可见  */
    protected var isVisibles = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (getUserVisibleHint()) {
            isVisibles = true
            onVisible()
        } else {
            isVisibles = false
            onInvisible()
        }
    }

    /**
     * 可见
     */
    protected fun onVisible() {
        lazyLoad()
    }

    /**
     * 不可见
     */
    protected fun onInvisible() {}

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract fun lazyLoad()
}
