package com.example.simple.simplethink.totle.fragment.scenePage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.SceneItem
import com.example.simple.simplethink.model.ScenesResponse
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.adapter.SceneAdapter
import kotlinx.android.synthetic.main.fragment_scence.*
import java.util.ArrayList

/**
 * Created by jiessie on 2019/6/5.
 */
class SceneFragment : Fragment() ,SceneContact.View{
    lateinit var sceneAdapter: SceneAdapter
    lateinit var persenter: SceneContact.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_scence,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }
    fun initView(){
        val httpResposityImpl = HttpResposityImpl()
        persenter = ScenePresenter(httpResposityImpl,this)
        persenter.getAllScene()
        setAdapter()
    }

    fun setAdapter(){
        sceneAdapter = SceneAdapter(this.activity!!)
        scene_rv.layoutManager = LinearLayoutManager(this.activity,LinearLayoutManager.VERTICAL,false)
        scene_rv.adapter = sceneAdapter
    }
    override fun setSenceAdapter(scenesResponse:  ArrayList<SceneItem>) {
        sceneAdapter.setData(scenesResponse)
    }

    fun createFragment(): SceneFragment {
        val bundle = Bundle()
        val fragment = SceneFragment()
        fragment.setArguments(bundle)
        return fragment
    }
}