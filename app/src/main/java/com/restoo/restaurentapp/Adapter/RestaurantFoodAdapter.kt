package com.restoo.restaurentapp.Adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.restoo.restaurentapp.Interface.RestaurantFoodItemListner
import com.restoo.restaurentapp.R
import com.restoo.restaurentapp.model.RestaurantFood

class RestaurantFoodAdapter(val list : ArrayList<RestaurantFood>, val context : FragmentActivity,listner : RestaurantFoodItemListner) : RecyclerView.Adapter<RestaurantFoodAdapter.RestaurantFoddViewholder>() {

    var mListner : RestaurantFoodItemListner = listner

    class RestaurantFoddViewholder(view: View) : RecyclerView.ViewHolder(view) {
        var mTxtFoodItemName: TextView
        var mTxtFoodPrice : TextView
        var mImgFood : ImageView
        var mBtnAdd : ElegantNumberButton
        init {
            mTxtFoodItemName = view
                    .findViewById<TextView>(R.id.res_foodname)
            mTxtFoodPrice = view.findViewById(R.id.res_price)
            mImgFood = view.findViewById(R.id.rest_food_image)
            mBtnAdd = view.findViewById(R.id.food_add_button)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantFoddViewholder {
        val itemview : View = LayoutInflater.from(parent.context).inflate(R.layout.layout_restaurant_fooditems,parent,false)
        return RestaurantFoddViewholder(itemview)
    }

    override fun onBindViewHolder(holder: RestaurantFoddViewholder, position: Int) {

        holder.mTxtFoodItemName.setText(list.get(position).foodname)
        holder.mTxtFoodPrice.setText("\u20B9 "+list.get(position).price)
        Glide.with(context).load(list.get(position).foodurl).into(holder.mImgFood)

        holder.mBtnAdd.setOnValueChangeListener { view, oldValue, newValue ->
            mListner.onfooditemclick(position,newValue)
        }
    }

    override fun getItemCount(): Int {
     return  list.size
    }
}