package com.restoo.restaurentapp.Helper

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.restoo.restaurentapp.Activity.HomeActivity
import com.restoo.restaurentapp.model.CartItem

class RestaurantFragmentHelper(context: FragmentActivity?) {
    var context : FragmentActivity = context!!

    fun showCartfragment(cart: ArrayList<CartItem>){
        var intent = Intent(context, HomeActivity::class.java)
        intent.putExtra("Type","Cartitems")
        intent.putExtra("cartfoods",cart)
        context.startActivity(intent)
    }
}