package com.restoo.restaurentapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.restoo.restaurentapp.Adapter.RestaurantFoodAdapter
import com.restoo.restaurentapp.Helper.RestaurantFragmentHelper
import com.restoo.restaurentapp.Interface.RestaurantFoodItemListner
import com.restoo.restaurentapp.R
import com.restoo.restaurentapp.model.CartItem
import com.restoo.restaurentapp.model.Foodcount
import com.restoo.restaurentapp.model.RestaurantFood
import java.util.*
import kotlin.collections.ArrayList

class RestaurantFragment : Fragment() ,RestaurantFoodItemListner {

    lateinit var databaseReference: DatabaseReference
    lateinit var firebaseDatabase: FirebaseDatabase
    var restaurantfoodlist : ArrayList<RestaurantFood> = ArrayList()
    lateinit var rv_restaurant_foods : RecyclerView
    lateinit var mImg_restaurant : ImageView
    lateinit var mTxtRestaurantName : TextView
    lateinit var mTxtRestaurantLocation : TextView
    lateinit var mTxtRestaurantRating : TextView
    var totalfoodcount : ArrayList<Foodcount> = ArrayList()
    lateinit var mTxtBottomItemCount : TextView
    lateinit var mTxtMoveToCart : TextView
    lateinit var mCardview_bottom : CardView
    var cartItems : ArrayList<CartItem> = ArrayList()
    lateinit var  helper : RestaurantFragmentHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_restaurant, container, false)
        Initialisation(view)
        helper = RestaurantFragmentHelper(activity)
        setData()
        EventListner()
        postponeEnterTransition()
        return view
    }

    private fun setData() {
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference
        var bundle :Bundle ?= activity?.intent?.extras
        var position = bundle?.get("Position")
        var resturantname  = bundle?.get("RestaurantName")
        var restaurantrating = bundle?.get("RestaurantRating")
        var restaurantlocaion = bundle?.get("RestaurantLocation")
        var restauranturl = bundle?.get("Restauranturl")
        mTxtRestaurantName.setText(resturantname.toString())
        mTxtRestaurantLocation.setText(restaurantlocaion.toString())
        mTxtRestaurantRating.setText(restaurantrating.toString())
        activity?.let { Glide.with(it).load(restauranturl).into(mImg_restaurant) }
        databaseReference.child("QuickFood").child("2").child("Restaurantfood")
                .child(position.toString()).child("Fooditems").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    var restaurentfood: RestaurantFood? =
                        postSnapshot.getValue(RestaurantFood::class.java)
                    if (restaurentfood != null) {
                        restaurantfoodlist.add(restaurentfood)
                    }
                }

                rv_restaurant_foods.layoutManager = LinearLayoutManager(
                    activity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                rv_restaurant_foods.setNestedScrollingEnabled(false);
                rv_restaurant_foods.adapter = activity?.let {
                    RestaurantFoodAdapter(
                        restaurantfoodlist,
                        it,
                        this@RestaurantFragment
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun EventListner() {
        mTxtMoveToCart.setOnClickListener(View.OnClickListener {
            helper.showCartfragment(cartItems)
        })
    }

    private fun Initialisation(view: View?) {
        if (view != null) {
            mImg_restaurant = view.findViewById(R.id.restaurant_image)
            mTxtRestaurantName = view.findViewById(R.id.restaurant_name)
            mTxtRestaurantLocation = view.findViewById(R.id.restaurant_location)
            mTxtRestaurantRating = view.findViewById(R.id.restaurant_rating)
            rv_restaurant_foods = view.findViewById(R.id.rv_restaurant_foods)
            mTxtBottomItemCount = view.findViewById(R.id.bottom_cart_items)
            mTxtMoveToCart = view.findViewById(R.id.move_to_cart)
            mCardview_bottom = view.findViewById(R.id.bottom_card_view)

        }
    }

    override fun onfooditemclick(itemposition: Int, count: Int) {
        cartItems = ArrayList()
        var isexist : Boolean = false
        if (totalfoodcount.size> 0) {
            val iterater = totalfoodcount.listIterator()
            for (item in iterater) {
                if (item.position == itemposition) {
                    isexist = true
                    if (count == 0) {
                        iterater.remove()
                    } else {
                        iterater.remove()
                        iterater.add(Foodcount(itemposition, count))
                    }
                }
            }
            if (!isexist) {
                iterater.add(Foodcount(itemposition, count))
            }
        } else {
            totalfoodcount.add(Foodcount(itemposition,count))
        }
        if (totalfoodcount.size>0) {
            mCardview_bottom.visibility = View.VISIBLE
            var data : String = totalfoodcount.size.toString() +  " Items"
            mTxtBottomItemCount.setText(data)


            for ((index, value) in restaurantfoodlist.withIndex()) {
                for (item in totalfoodcount) {
                    if (item.position == index) {
                        cartItems.add(CartItem(count,value))
                    }
                }
            }

        } else {
            mCardview_bottom.visibility = View.GONE
            cartItems = ArrayList()
        }
    }
}