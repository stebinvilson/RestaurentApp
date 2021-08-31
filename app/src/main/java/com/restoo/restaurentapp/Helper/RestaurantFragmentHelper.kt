package com.restoo.restaurentapp.Helper

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.restoo.restaurentapp.Activity.HomeActivity
import com.restoo.restaurentapp.model.RestaurantFood
import com.restoo.restaurentapp.model.Restaurents

class RestaurantFragmentHelper(context: FragmentActivity?) {
    var context : FragmentActivity = context!!

    fun showCartfragment(cart : ArrayList<RestaurantFood>){
        var intent = Intent(context, HomeActivity::class.java)
        intent.putExtra("Type","Cartitems")
        intent.putExtra("cartfoods",cart)
        context.startActivity(intent)
    }
}