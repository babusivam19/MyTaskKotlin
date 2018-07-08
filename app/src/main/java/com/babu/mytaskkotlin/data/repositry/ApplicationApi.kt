package com.babu.mytaskkotlin.data.repositry

import com.babu.mytaskkotlin.data.model.FactData
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Babu on 7/8/2018.
 */
interface ApplicationApi {

    @GET("s/2iodh4vg0eortkl/facts.json")
    fun loadFactInformation(): Call<FactData>

}