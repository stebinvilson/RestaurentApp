package com.restoo.restaurentapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.restoo.restaurentapp.Adapter.AdAdapter
import com.restoo.restaurentapp.Adapter.FavFoodAdapter
import com.restoo.restaurentapp.Adapter.RestaurentAdapter
import com.restoo.restaurentapp.Application.QuickEat
import com.restoo.restaurentapp.Helper.HomeFragmentHelper
import com.restoo.restaurentapp.Interface.RestaurantAdapterListner
import com.restoo.restaurentapp.R
import com.restoo.restaurentapp.model.FoodItem
import com.restoo.restaurentapp.model.Restaurents
import com.restoo.restaurentapp.model.UserDetails
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset



class HomeFragment : Fragment() , RestaurantAdapterListner {
    var list : ArrayList<FoodItem> = ArrayList()
    var adlist : ArrayList<String> = ArrayList()
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    var restaurentlist : ArrayList<Restaurents> = ArrayList()
    lateinit var recycler : RecyclerView
    lateinit var recycler_restaurant : RecyclerView
    lateinit var recycler_offer : RecyclerView
    lateinit var progressBar : ProgressBar
    lateinit var  helper : HomeFragmentHelper
    lateinit var mTxtUsername : TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        Initialisation(view)
        helper = HomeFragmentHelper(activity)
        setData()

//        progressBar = view.findViewById(R.id.progressBar1)
//        val draw: Drawable? = activity?.resources?.getDrawable(R.drawable.custom_progress_bar)
//        progressBar.setProgressDrawable(draw)
//        progressBar.showContextMenu()
        EventListner()
        return view
    }

    private fun setData() {

        setfooditems()
        setAd()
        databaseReference.child("user").child(QuickEat.UserId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var user : UserDetails? = snapshot.getValue(UserDetails::class.java)
                if (user != null) {
                    mTxtUsername.setText("Hi " + user.username)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        databaseReference.child("QuickFood").child("1").child("Restaurent").addValueEventListener(object :
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    var restaurent: Restaurents? = postSnapshot.getValue(Restaurents::class.java)
                    if (restaurent != null) {
                        restaurentlist.add(restaurent)
                    }
                }
                recycler_restaurant.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                recycler_restaurant.setNestedScrollingEnabled(false);
                recycler_restaurant.adapter = activity?.let { RestaurentAdapter(restaurentlist, it,this@HomeFragment) }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun setAd() {

        var jsonad: String? = null
        try {
            val inputstream : InputStream? = activity?.assets?.open("Ad.json")
            val size: Int? = inputstream?.available()
            val buffer = size?.let { ByteArray(it) }
            val charset: Charset = Charsets.UTF_8
            if (inputstream != null) {
                inputstream.read(buffer)
            }
            inputstream?.close()
            jsonad = buffer?.let { String(it, charset) }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

        try {
            val obj = JSONObject(jsonad)
            val userArray = obj.getJSONArray("Offer")
            for (i in 0 until userArray.length()) {
                val userDetail = userArray.getJSONObject(i)
                val ad = (userDetail.getString("ad"))

                adlist.add(ad)
            }
            recycler_offer.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            recycler_offer.setNestedScrollingEnabled(false);
            recycler_offer.adapter = activity?.let { AdAdapter(adlist, it) }
        }
        catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun setfooditems() {
        var json: String? = null
        try {
            val inputstream : InputStream? = activity?.assets?.open("FoodItems.json")
            val size: Int? = inputstream?.available()
            val buffer = size?.let { ByteArray(it) }
            val charset: Charset = Charsets.UTF_8
            if (inputstream != null) {
                inputstream.read(buffer)
            }
            inputstream?.close()
            json = buffer?.let { String(it, charset) }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

        try {
            val obj = JSONObject(json)
            val userArray = obj.getJSONArray("FoodItem")
            for (i in 0 until userArray.length()) {
                val userDetail = userArray.getJSONObject(i)
                val foodname1 = (userDetail.getString("Foodname"))
                val foodid1 =(userDetail.getString("Foddid"))
                val foodimage1 = userDetail.getString("FoodImage")
                var item : FoodItem = FoodItem(foodname1, foodid1, foodimage1)
                list.add(item)
            }
            recycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            recycler.setNestedScrollingEnabled(false);
            recycler.adapter = activity?.let { FavFoodAdapter(list, it) }
        }
        catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun Initialisation(view: View?) {
        if (view != null) {
            mTxtUsername = view.findViewById(R.id.txt_username)
            recycler = view.findViewById(R.id.recyclerview_fooditems)
        }
        if (view != null) {
            recycler_restaurant = view.findViewById(R.id.rv_restaurants)
        }
        if (view != null) {
            recycler_offer = view.findViewById(R.id.recyclerview_offers)
        }
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference

    }

    private fun EventListner() {

    }

    override fun onRestaurantItemClick(position: Int) {
        helper.showRestaurantfragment(position = position,restaurentlist.get(position)  )

    }


}