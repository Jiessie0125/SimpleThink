package com.example.simple.simplethink.totle.fragment.scenePage

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.ScenesResponse
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.BaseFragment
import com.example.simple.simplethink.totle.activity.SceneDetailActivity
import com.example.simple.simplethink.totle.adapter.OnItemClickListener
import com.example.simple.simplethink.totle.adapter.SceneAdapter
import kotlinx.android.synthetic.main.fragment_scence.*

/**
 * Created by jiessie on 2019/6/5.
 */
class SceneFragment : Fragment() ,SceneContact.View{
    lateinit var sceneAdapter: SceneAdapter
    lateinit var persenter: SceneContact.Presenter
    private val mHasLoadedOnce = false
    private val isPrepared = false
   // lateinit var sceneList : List<ScenesResponse>

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
    }

    fun setAdapter(sceneList : List<ScenesResponse>){
        scene_rv.layoutManager = LinearLayoutManager(this.activity,LinearLayoutManager.VERTICAL,false)
        sceneAdapter = SceneAdapter(this.activity!!,sceneList)
        scene_rv.adapter = sceneAdapter
        sceneAdapter.setOnItemClickListener(object :OnItemClickListener{
            override fun onItemClick(view: View, postion: Int) {
                showSceneDetailPage(sceneList[postion])
               }
        })
    }

    fun showSceneDetailPage(sceneItem : ScenesResponse){
        var sceneDetailActivity = SceneDetailActivity.newIntent(this.activity,sceneItem)
        startActivity(sceneDetailActivity)
    }

    override fun setSenceAdapter(scenesResponse:  List<ScenesResponse>) {
        //sceneList = scenesResponse
        setAdapter(scenesResponse)
        //sceneAdapter.setData(scenesResponse)
    }

    fun createFragment(): SceneFragment {
        val bundle = Bundle()
        val fragment = SceneFragment()
        fragment.setArguments(bundle)
        return fragment
    }
}