package com.babu.mytaskkotlin.view.activity

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.babu.mytaskkotlin.R
import com.babu.mytaskkotlin.data.model.FactData
import com.babu.mytaskkotlin.data.model.InformationData
import com.babu.mytaskkotlin.data.model.Response
import com.babu.mytaskkotlin.view.adapter.InformationListAdapter
import com.babu.mytaskkotlin.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    lateinit var informationListAdapter: InformationListAdapter
    private lateinit var informationDataArrayList: ArrayList<InformationData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        informationDataArrayList = ArrayList()
        initialiseView()
        setUpEvent()
        loadData()
    }

    private fun initialiseView() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_information_list.setLayoutManager(linearLayoutManager)
        //Add row divider
        val dividerItemDecoration = DividerItemDecoration(rv_information_list.getContext(),
                linearLayoutManager.orientation)
        rv_information_list.addItemDecoration(dividerItemDecoration)
        //load value in adapter
        informationListAdapter = InformationListAdapter(this, informationDataArrayList)
        rv_information_list.adapter = informationListAdapter
    }

    //receive response and load data
    private fun loadData() {
        swipe_refresh.isRefreshing = true
        loadInformation()
    }

    private fun loadInformation() {
        mainActivityViewModel.loadInformation()!!.observeForever({ response ->
            swipe_refresh.isRefreshing = false
            updateResponse(response!!)
        })
    }

    //refresh data
    private fun setUpEvent() {
        swipe_refresh.setOnRefreshListener({
            loadInformation()
        })
    }

    //receive response from api
    fun updateResponse(response: Response) {
        informationDataArrayList.clear()
        if (response.status === Response.Status.SUCCESS) {
            val fact = response.data as FactData
            Log.d("Actionbar title--->", fact.title)
            this.supportActionBar!!.title = fact.title
            if (fact.informationData.size > 0) {
                informationDataArrayList.addAll(parseInformation(fact.informationData))
            }
        } else {
            runOnUiThread {
                Toast.makeText(this.applicationContext, "Something wrong", Toast.LENGTH_SHORT).show()
            }
        }
        informationListAdapter.notifyDataSetChanged()

    }

    //remove the empty or null value in list
    private fun parseInformation(informationData: ArrayList<InformationData>): ArrayList<InformationData> {
        val list = ArrayList<InformationData>()
        for (information in informationData) {
            if (!TextUtils.isEmpty(information.title) || !TextUtils.isEmpty(information.description) || !TextUtils.isEmpty(information.imageUrl)) {
                list.add(list.size, information)
            }
        }
        return list
    }
}
