package com.example.simple.simplethink.totle.fragment


import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.TotleItem
import com.example.simple.simplethink.model.TotleSortResponse
import com.example.simple.simplethink.netapi.HttpResposityImpl
import com.example.simple.simplethink.totle.adapter.TotleAdapter
import kotlinx.android.synthetic.main.fragment_totle.*
import okhttp3.ResponseBody
import java.util.*


/**
 * Created by jiessie on 2019/6/5.
 */
class TotleFragment : Fragment(),TotleContact.View {

    lateinit var persenter : TotleContact.Presenter
    var totleList : ArrayList<TotleItem?> ?=null
    var totleItem : TotleItem ?= null
    var itemImage : Bitmap ?= null
    lateinit var totleAdapter : TotleAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_totle,container,false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        initView()
    }

    private fun initView(){
        val httpResposityImpl = HttpResposityImpl()
        persenter = TotlePresenter(httpResposityImpl,this)
        persenter.getTotleSort()

       // setAdapter()
    }
    fun createFragment(): TotleFragment {
        val bundle = Bundle()

        val fragment = TotleFragment()
        fragment.setArguments(bundle)

        return fragment
    }

    override fun getTotleSortIcon(list: List<TotleSortResponse>) {
        for(i in 0 until list.size){
            persenter.getItemImage(list[i].image,list[i].category_name)
            /*totleItem?.totleItemTxt = list[i].category_name
            totleItem?.totleItemImage = itemImage
            totleList?.add(totleItem)*/
        }
    }

    override fun getItemImage(image: Bitmap)  {
       // itemImage = image.bytes()
    }

    private fun setAdapter(){
       /* totleAdapter = TotleAdapter(this.context,totleList)
        recycle_tv.layoutManager = GridLayoutManager(this.context,4)
        recycle_tv.adapter = totleAdapter*/
    }
}