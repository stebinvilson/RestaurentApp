package com.restoo.restaurentapp.Helper

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.restoo.restaurentapp.Activity.HomeActivity
import com.restoo.restaurentapp.model.Restaurents

class HomeFragmentHelper(context: FragmentActivity?) {
     var context : FragmentActivity = context!!

    fun showRestaurantfragment(position: Int, get: Restaurents){
        var intent = Intent(context,HomeActivity::class.java)
        intent.putExtra("Type","RestaurantItem")
        intent.putExtra("RestaurantName",get.restaurentname)
        intent.putExtra("RestaurantLocation",get.restaurentlocation)
        intent.putExtra("Restauranturl",get.restauranturl)
        intent.putExtra("RestaurantRating",get.rating)
        intent.putExtra("Position", position)
        context.startActivity(intent)
    }

}


