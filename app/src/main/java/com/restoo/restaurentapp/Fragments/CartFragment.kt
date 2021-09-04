package com.restoo.restaurentapp.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.restoo.restaurentapp.Activity.HomeActivity
import com.restoo.restaurentapp.Adapter.CartAdapter
import com.restoo.restaurentapp.R
import com.restoo.restaurentapp.model.CartItem


class CartFragment : Fragment() {
    var cartItems : ArrayList<CartItem> = ArrayList()
    lateinit var mRvcartItems : RecyclerView
    lateinit var button : MaterialButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_cart, container, false)
        // Inflate the layout for this fragment
        Initialisation(view)
        setData()
        EventListner()
        return view
    }

    private fun EventListner() {
        button.setOnClickListener(View.OnClickListener {
            mapFragment("")
        })

    }

    private fun setData() {

        if (arguments?.getSerializable("cartitems")  != null) {
            cartItems = arguments?.getSerializable("cartitems") as ArrayList<CartItem>
        }

        mRvcartItems.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mRvcartItems.setNestedScrollingEnabled(false);
        mRvcartItems.adapter = activity?.let { CartAdapter(cartItems, it) }
    }

    private fun Initialisation(view: View?) {
        if (view != null) {
            mRvcartItems = view.findViewById(R.id.foodcart_items)
            button = view.findViewById(R.id.btn_proceed)
        }

    }
    fun mapFragment(st : String){

    var intent = Intent(context, HomeActivity::class.java)
    intent.putExtra("Type","Map")
        activity?.startActivity(intent)
    }

}