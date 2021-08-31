package com.restoo.restaurentapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.restoo.restaurentapp.Interface.RestaurantAdapterListner
import com.restoo.restaurentapp.R
import com.restoo.restaurentapp.model.FoodItem
import com.restoo.restaurentapp.model.Restaurents

class RestaurentAdapter (val list : ArrayList<Restaurents>, val context : FragmentActivity,listner1: RestaurantAdapterListner) : RecyclerView.Adapter<RestaurentAdapter.RestaurentViewHolder>() {
      var listner2 : RestaurantAdapterListner = listner1

    class RestaurentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mImgRestaurant: ImageView
        var mTxtRestaurantName : TextView
        var mTxtResRating : TextView
        var mTxtRestaurantLocation : TextView
        var mTxtRestaurantRating : RatingBar
        var mTxtFoodType : TextView
        lateinit var listner : RestaurantAdapterListner

        init {

            mImgRestaurant = view.findViewById<ImageView>(R.id.img_restaurent)
            mTxtRestaurantName = view.findViewById(R.id.txt_res_name)
            mTxtRestaurantRating = view.findViewById(R.id.res_rating)
            mTxtResRating = view.findViewById(R.id.res_txt_rating)
            mTxtRestaurantLocation = view.findViewById(R.id.res_location)
            mTxtFoodType = view.findViewById(R.id.res_food_type)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurentViewHolder {
        val itemView: View = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.layout_item_restaurents,
                        parent,
                        false)

        return RestaurentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RestaurentViewHolder, position: Int) {
        holder.mTxtRestaurantName.setText(list.get(position).restaurentname)
        holder.mTxtResRating.setText(list.get(position).rating)
        holder.mTxtRestaurantLocation.setText(list.get(position).restaurentlocation)
        holder.mTxtRestaurantRating.setRating(list.get(position).rating.toFloat());
        Glide.with(context).load(list.get(position).restauranturl).into(holder.mImgRestaurant)
        holder.mTxtFoodType.setText(list.get(position).resfoodtype)

        holder.mTxtRestaurantName.setOnClickListener(View.OnClickListener {
            listner2.onRestaurantItemClick(position)

        })

    }

    override fun getItemCount(): Int {
       return list.size
    }
}