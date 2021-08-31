package com.restoo.restaurentapp.Activity

import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.restoo.restaurentapp.Fragments.CartFragment
import com.restoo.restaurentapp.Fragments.HomeFragment
import com.restoo.restaurentapp.Fragments.RestaurantFragment
import com.restoo.restaurentapp.R
import com.restoo.restaurentapp.model.RestaurantFood
import de.hdodenhof.circleimageview.CircleImageView


class HomeActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    lateinit var appbar : MaterialToolbar
    lateinit var  toolbar : ImageView
    lateinit var mImgprofileimg : CircleImageView
    lateinit var navigationView : NavigationView
    lateinit var mImgProfile : CircleImageView
    lateinit var cartItems: ArrayList<RestaurantFood>
    lateinit var home_back : ImageView
    lateinit var searchview : androidx.appcompat.widget.SearchView
    lateinit var home_title : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.layout_home)
        Initialization()
        setData()
        Eventlistner()
    }

    private fun Eventlistner() {

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        toolbar.setOnClickListener(View.OnClickListener {
            drawer.openDrawer(GravityCompat.START)
        })
    }

    private fun setData() {
        var bundle :Bundle ?=intent.extras
        var type = bundle?.getString("Type")
        var position = bundle?.getString("Position")
        if (type.equals("Cartitems")) {
            cartItems = bundle?.getSerializable("cartfoods") as ArrayList<RestaurantFood>
        }
        Glide.with(applicationContext).load("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Alesso_profile.png/467px-Alesso_profile.png").into(mImgprofileimg)
        Glide.with(applicationContext).load("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Alesso_profile.png/467px-Alesso_profile.png").into(mImgProfile)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.setCheckedItem(R.id.Orders)

        if (type != null) {

            if (type.equals("RestaurantItem")) {
               // val details: RestaurantFragment = RestaurantFragment()
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    details.setSharedElementEnterTransition(RestaurantFragment())
//                    details.setEnterTransition(Fade())
//                    details.setExitTransition(Fade())
//                    details.setSharedElementReturnTransition(RestaurantFragment())
//                }

                val fragmentManager: FragmentManager = supportFragmentManager
                val fragment = RestaurantFragment()
                var bundle = Bundle()
                bundle.putString("Position",position)
                fragment.arguments = bundle
                fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
            } else if (type.equals("Cartitems")) {
                searchview.visibility = View.INVISIBLE
                toolbar.visibility = View.INVISIBLE
                home_back.visibility = View.VISIBLE
                mImgProfile.visibility = View.INVISIBLE
                home_title.setText("My Cart")
                val fragmentManager: FragmentManager = supportFragmentManager
                val fragment = CartFragment()
                var bundle = Bundle()
                bundle.putSerializable("cartitems",cartItems)
                fragment.arguments = bundle
                fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
            }
            else {
                val fragmentManager: FragmentManager = supportFragmentManager
                val fragment = HomeFragment()
                fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
            }
        } else {
            val fragmentManager: FragmentManager = supportFragmentManager
            val fragment = HomeFragment()
            fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
        }
    }

    private fun Initialization() {
        home_title = findViewById(R.id.home_title)
        searchview = findViewById(R.id.home_searchview)
        toolbar = findViewById(R.id.menu)
        navigationView =  findViewById(R.id.nav_view)
        val header: View = navigationView.getHeaderView(0)
        mImgprofileimg = header.findViewById(R.id.navigation_profile_photo)
        mImgProfile = findViewById(R.id.profile_image)
        home_back = findViewById(R.id.home_back)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//
//        var fragment: Fragment? = null
//        val fragmentManager = supportFragmentManager
//        if (id === R.id.search) {
//            fragment = HomeFragment()
//        }
//
//        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment!!).commit()
//        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
//        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
