package com.restoo.restaurentapp.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.restoo.restaurentapp.Fragments.*
import com.restoo.restaurentapp.R
import com.restoo.restaurentapp.model.RestaurantFood
import de.hdodenhof.circleimageview.CircleImageView


class HomeActivity : AppCompatActivity() {

    lateinit var appbar: MaterialToolbar
    lateinit var toolbar: ImageView
    lateinit var mImgprofileimg: CircleImageView
    lateinit var navigationView: NavigationView
    lateinit var mImgProfile: CircleImageView
    lateinit var cartItems: ArrayList<RestaurantFood>
    lateinit var home_back: ImageView
    lateinit var mBottomNavigation: BottomNavigationView
    lateinit var searchview: androidx.appcompat.widget.SearchView
    lateinit var home_title: TextView
    val fragmentManager: FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.layout_home)
        Initialization()
        setData()
       // val navController = findNavController(R.id.nav_host_fragment)
      //  mBottomNavigation.setupWithNavController(navController)
        Eventlistner()
    }

    private fun Eventlistner() {

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        toolbar.setOnClickListener(View.OnClickListener {
            drawer.openDrawer(GravityCompat.START)
        })

//        mBottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.page_1 -> {
//                    fragmentManager.beginTransaction().hide(activeFragment).show(homefragment).addToBackStack(null).commit()
//                    activeFragment = homefragment
//                    true
//                }
//                R.id.page_2 -> {
//                    fragmentManager.beginTransaction().hide(activeFragment).show(favfragment).addToBackStack(null).commit()
//                    activeFragment = favfragment
//                    true
//                }
//                R.id.page_3 -> {
//                    fragmentManager.beginTransaction().hide(activeFragment).show(cartfragment).addToBackStack(null).commit()
//                    activeFragment = cartfragment
//                    true
//                }
//                else -> false
//            }
//        }

        mBottomNavigation.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                val previousItem: Int = mBottomNavigation.getSelectedItemId()
                val nextItem = item.itemId
                if (previousItem != nextItem) {
                    when (nextItem) {
                        R.id.page_1 -> {
                            home_back.visibility = View.INVISIBLE
                            home_title.visibility = View.INVISIBLE
                            mImgProfile.visibility = View.VISIBLE
                            toolbar.visibility = View.VISIBLE
                            searchview.visibility = View.VISIBLE
                            //     val fragmentManager: FragmentManager = supportFragmentManager
                            val fragment = HomeFragment()
                            fragmentManager.beginTransaction().replace(
                                R.id.nav_host_fragment,
                                fragment
                            ).commit()
                        }
                        R.id.page_2 -> {
                            //    val fragmentManager: FragmentManager = supportFragmentManager
                            val fragment = FavoriteFragment()
                            fragmentManager.beginTransaction().replace(
                                R.id.nav_host_fragment,
                                fragment
                            ).commit()
                        }
                        R.id.page_3 -> {
                            searchview.visibility = View.INVISIBLE
                            toolbar.visibility = View.INVISIBLE
                            home_back.visibility = View.VISIBLE
                            mImgProfile.visibility = View.INVISIBLE
                            home_title.setText("My Cart")
//                                val fragmentManager: FragmentManager = supportFragmentManager
                            val fragment = CartFragment()
                            //  var bundle = Bundle()
                            //    bundle.putSerializable("cartitems", cartItems)
                            //  fragment.arguments = bundle
                            fragmentManager.beginTransaction().replace(
                                R.id.nav_host_fragment,
                                fragment
                            ).commit()
                        }
                        R.id.page_4 -> {
                            //    val fragmentManager: FragmentManager = supportFragmentManager
                            val fragment = AccountFragment()
                            fragmentManager.beginTransaction().replace(
                                R.id.nav_host_fragment,
                                fragment
                            ).commit()
                        }
                    }
                }
                true
            }
        )
    }

    private fun setData() {
        var bundle :Bundle ?=intent.extras
        var type = bundle?.getString("Type")
        var position = bundle?.getString("Position")
        if (type.equals("Cartitems")) {
            cartItems = bundle?.getSerializable("cartfoods") as ArrayList<RestaurantFood>
        }
        Glide.with(applicationContext).load("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Alesso_profile.png/467px-Alesso_profile.png").into(
            mImgprofileimg
        )
        Glide.with(applicationContext).load("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Alesso_profile.png/467px-Alesso_profile.png").into(
            mImgProfile
        )
      //  navigationView.setNavigationItemSelectedListener(this)
      //  navigationView.setCheckedItem(R.id.Orders)

        if (type != null) {

            if (type.equals("RestaurantItem")) {

                val fragment = RestaurantFragment()
                var bundle = Bundle()
                bundle.putString("Position", position)
                fragment.arguments = bundle
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit()
            } else if (type.equals("Cartitems")) {
                searchview.visibility = View.INVISIBLE
                toolbar.visibility = View.INVISIBLE
                home_back.visibility = View.VISIBLE
                mImgProfile.visibility = View.INVISIBLE
                home_title.setText("My Cart")
                val fragmentManager: FragmentManager = supportFragmentManager
                val fragment = CartFragment()
                var bundle = Bundle()
                bundle.putSerializable("cartitems", cartItems)
                fragment.arguments = bundle
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit()
                mBottomNavigation.setSelectedItemId(R.id.page_3);
            }
            else if(type.equals("Map")){
                var intent = Intent(this,MapsActivity::class.java)
                startActivity(intent)
//                val fragment = MapsFragment()
//                fragmentManager.beginTransaction().add(R.id.nav_host_fragment, fragment).addToBackStack(
//                    null
//                ).commit()
            }
            else {
                val fragmentManager: FragmentManager = supportFragmentManager
                val fragment = HomeFragment()
                fragmentManager.beginTransaction().add(R.id.nav_host_fragment, fragment).addToBackStack(
                    null
                ).commit()
                mBottomNavigation.setSelectedItemId(R.id.page_1);
            }
        }
        else {
            val fragmentManager: FragmentManager = supportFragmentManager
            val fragment = HomeFragment()
            fragmentManager.beginTransaction().add(R.id.nav_host_fragment, fragment).commit()
            mBottomNavigation.setSelectedItemId(R.id.page_1);
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
        mBottomNavigation = findViewById(R.id.bottom_navigation)
    }
}
