package com.example.simple.simplethink.totle.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simple.simplethink.R

/**
 * Created by jiessie on 2019/6/5.
 */
class SceneFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_scence,container,false)
    }

    fun createFragment(): SceneFragment {
        val bundle = Bundle()
        val fragment = SceneFragment()
        fragment.setArguments(bundle)
        return fragment
    }
}