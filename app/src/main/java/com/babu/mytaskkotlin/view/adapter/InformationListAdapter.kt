package com.babu.mytaskkotlin.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.babu.mytaskkotlin.R
import com.babu.mytaskkotlin.data.model.InformationData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.informartion_item_list.view.*
import java.util.ArrayList

/**
 * Created by Babu on 7/8/2018.
 */
class InformationListAdapter(private val context: Context, informationDataArrayList: ArrayList<InformationData>) : RecyclerView.Adapter<InformationListAdapter.InformationListViewHolder>() {
    private val informationDataArrayList: ArrayList<InformationData>? = informationDataArrayList

    class InformationListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title = v.tv_title!!
        val description = v.tv_description!!
        val image = v.iv_imageView!!

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): InformationListViewHolder {
        val v = LayoutInflater.from(parent!!.getContext()).inflate(R.layout.informartion_item_list, parent, false)
        return InformationListViewHolder(v)

    }

    override fun getItemCount(): Int {
        return informationDataArrayList?.size ?: 0
    }

    override fun onBindViewHolder(holder: InformationListViewHolder, position: Int) {
        val informationData = informationDataArrayList!![position]
        if (!TextUtils.isEmpty(informationData.title)) {
            holder.title.text = informationData.title
        } else {
            holder.title.text = ""
        }
        if (!TextUtils.isEmpty(informationData.description)) {
            holder.description.text = informationData.description
        } else {
            holder.description.text = ""
        }
        val url = informationData.imageUrl
        if (!TextUtils.isEmpty(url)) {
            Glide.with(context).load(url).apply(RequestOptions()
                    .placeholder(R.drawable.img_not_available)
                    .error(R.drawable.img_not_available)).into(holder.image)
        } else {
            holder.image.setImageResource(R.drawable.img_not_available)
        }
    }
}