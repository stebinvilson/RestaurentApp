package com.restoo.restaurentapp.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.restoo.restaurentapp.R
import de.hdodenhof.circleimageview.CircleImageView

class AdAdapter(val list : ArrayList<String>, val context : Activity) : RecyclerView.Adapter<AdAdapter.AdViewHolder>() {

    class AdViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mImgFoodItem: CircleImageView
        init {
            mImgFoodItem = view
                    .findViewById<CircleImageView>(R.id.img_offer)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        val itemView: View = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.layout_item_offer,
                        parent,
                        false)

        return AdAdapter.AdViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {

        Glide.with(context).load(list.get(position)).into(holder.mImgFoodItem)
    }

    override fun getItemCount(): Int {
       return list.size
    }
}