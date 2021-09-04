package com.restoo.restaurentapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.restoo.restaurentapp.R
import com.restoo.restaurentapp.model.CartItem
import com.restoo.restaurentapp.model.RestaurantFood

class CartAdapter (val list : ArrayList<CartItem>, val context : FragmentActivity) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mImgcartItem: ImageView
        var mTxtcartFoodName : TextView
        var mBtnCount : ElegantNumberButton

        init {
            mImgcartItem = view
                .findViewById<ImageView>(R.id.item_cart_image)
            mTxtcartFoodName = view.findViewById(R.id.txt_cart_fooditemname)
            mBtnCount = view.findViewById(R.id.food_add_button)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layout_item_cart,
                parent,
                false)

        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.mTxtcartFoodName.setText(list.get(position).resFood.foodname)
        holder.mBtnCount.setNumber(list.get(position).count.toString())
        Glide.with(context).load(list.get(position).resFood.foodurl).into(holder.mImgcartItem)

    }

    override fun getItemCount(): Int {
       return list.size
    }
}