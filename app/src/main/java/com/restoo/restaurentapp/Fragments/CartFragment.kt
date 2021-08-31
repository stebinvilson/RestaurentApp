package com.restoo.restaurentapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.restoo.restaurentapp.R
import com.restoo.restaurentapp.model.RestaurantFood

class CartFragment : Fragment() {
    var cartItems : ArrayList<RestaurantFood> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_cart, container, false)
        Initialisation(view)
        setData()
        EventListner()
        postponeEnterTransition()
        return view
    }

    private fun EventListner() {
        var bundle :Bundle ?= activity?.intent?.extras
        cartItems = arguments?.getSerializable("cartitems") as ArrayList<RestaurantFood>
       // cartItems = bundle?.get("cartitems") as ArrayList<RestaurantFood>
    }

    private fun setData() {

    }

    private fun Initialisation(view: View?) {


    }
}