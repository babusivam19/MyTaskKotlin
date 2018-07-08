package com.babu.mytaskkotlin.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.babu.mytaskkotlin.data.model.Response
import com.babu.mytaskkotlin.data.repositry.RepositryApi

/**
 * Created by Babu on 7/8/2018.
 */
class MainActivityViewModel : ViewModel() {
    private var repositoryApi: RepositryApi = RepositryApi.getInstance()
    private var mutableLiveData: MutableLiveData<Response>? = null

    init {
        if (mutableLiveData == null) {
            mutableLiveData = MutableLiveData()
            loadInformation()
        }
    }

    fun loadInformation(): LiveData<Response>? {
        repositoryApi.loadFactInformation().observeForever({ response -> mutableLiveData!!.setValue(response) })
        return mutableLiveData
    }

}