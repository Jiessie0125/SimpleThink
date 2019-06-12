package com.example.simple.simplethink.totle.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simple.simplethink.R
import com.example.simple.simplethink.model.TotleItem
import com.example.simple.simplethink.totle.adapter.TotleAdapter
import kotlinx.android.synthetic.main.fragment_totle.*


/**
 * Created by jiessie on 2019/6/5.
 */
class TotleFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_totle,container,false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        initView()
    }

    private fun initView(){
        val totleItem = TotleItem()
        val layoutManager = GridLayoutManager(this.context,4)
        recycle_tv.layoutManager = layoutManager
        recycle_tv.adapter = TotleAdapter(this.context,totleItem)
    }
    fun createFragment(): TotleFragment {
        val bundle = Bundle()

        val fragment = TotleFragment()
        fragment.setArguments(bundle)

        return fragment
    }
}