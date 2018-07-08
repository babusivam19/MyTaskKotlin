package com.babu.mytaskkotlin.data.model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

/**
 * Created by Babu on 7/8/2018.
 */
data class FactData(@SerializedName("title") val title: String, @SerializedName("rows") val informationData: ArrayList<InformationData>)

data class InformationData(@SerializedName("title") val title: String, @SerializedName("description") val description: String, @SerializedName("imageHref") val imageUrl: String)