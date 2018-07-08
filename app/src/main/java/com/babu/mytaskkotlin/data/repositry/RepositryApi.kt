package com.babu.mytaskkotlin.data.repositry

import android.arch.lifecycle.MutableLiveData
import com.babu.mytaskkotlin.BuildConfig
import com.babu.mytaskkotlin.data.model.FactData
import com.babu.mytaskkotlin.data.model.Response
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

/**
 * Created by Babu on 7/8/2018.
 */
class RepositryApi() {
    private val applicationApi: ApplicationApi


    init {
        val CONNECTION_TIMEOUT = 60
        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build()
        applicationApi = retrofit.create(ApplicationApi::class.java)
    }

    companion object {
        var repositryApi: RepositryApi? = null
        @Synchronized
        fun getInstance(): RepositryApi {
            if (repositryApi == null) {
                repositryApi = RepositryApi()
            }
            return repositryApi as RepositryApi
        }
    }

    fun loadFactInformation(): MutableLiveData<Response> {
        val responseMutableLiveData = MutableLiveData<Response>()
        applicationApi.loadFactInformation().enqueue(object : Callback<FactData> {
            override fun onResponse(call: Call<FactData>, response: retrofit2.Response<FactData>) {
                if (response.isSuccessful) {
                    responseMutableLiveData.postValue(Response.success(response.body()))
                } else {
                    when (response.code()) {
                        HttpURLConnection.HTTP_UNAUTHORIZED -> responseMutableLiveData.postValue(Response.error(null, Throwable("Un authorized error")))
                        HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> responseMutableLiveData.postValue(Response.error(null, Throwable("Connection time out")))
                        else -> responseMutableLiveData.postValue(Response.error(null, Throwable("Something wrong")))
                    }
                }
            }

            override fun onFailure(call: Call<FactData>, t: Throwable) {
                responseMutableLiveData.postValue(Response.error(null, Throwable("Check your Internet Connection")))
            }
        })
        return responseMutableLiveData
    }

}