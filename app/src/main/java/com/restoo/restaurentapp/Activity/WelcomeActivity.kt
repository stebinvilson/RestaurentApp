package com.restoo.restaurentapp.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.restoo.restaurentapp.R


class WelcomeActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var myViewPagerAdapter: MyViewPagerAdapter
    private lateinit var dotsLayout: LinearLayout
    private lateinit var dots : ArrayList<TextView>
    private lateinit var mImgSkip: ImageView
    private lateinit var mImgNext: ImageView
    lateinit var layouts: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.layout_welcome)

        viewPager = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.layoutDots);
        mImgSkip = findViewById(R.id.btn_skip);
        mImgNext = findViewById(R.id.btn_next);

        layouts = intArrayOf(
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4)

        addBottomDots(0);

        myViewPagerAdapter = MyViewPagerAdapter(applicationContext, layouts);
        viewPager.setAdapter(myViewPagerAdapter);
        mImgSkip.setOnClickListener(View.OnClickListener {

        })
        mImgNext.setOnClickListener(View.OnClickListener {

            var current = getItem(+1);
            if (current < layouts.size) {
                // move to next screen
                viewPager.setCurrentItem(current);
            } else {
                val intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)
            }
        })

        //  viewpager change listener
        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

            }
        })
    }

    private fun getItem(i: Int): Int {
        return viewPager.getCurrentItem() + i;
    }

    private fun addBottomDots(currentPage: Int) {

        dots = ArrayList<TextView>(layouts.size)

        val colorsActive = getResources().getIntArray(R.array.array_dot_active);
        val colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

      //  dotsLayout?.removeAllViews();
        for (i in 0 until (dotsLayout.size)) {
            dots[i] = TextView(this);
            dots[i].setTextSize(35.0f);
            dots[i].setTextColor(colorsInactive[currentPage])
            dotsLayout.addView(dots[i]);
        }

        if (dots.size > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);

    }


   class MyViewPagerAdapter(context: Context, layouts: IntArray) : PagerAdapter() {
       var layouts : IntArray = layouts
       var context : Context = context
       lateinit var layoutInflater : LayoutInflater;
       var images = arrayOf("https://cdn.dribbble.com/users/2315586/screenshots/13931449/media/aac362353835bdf70c8ce09581cfaa69.jpg?compress=1&resize=800x600",
               "https://cdn.dribbble.com/users/1954478/screenshots/15744790/media/cc11ac065eb299b3abab2d5e0298372c.png?compress=1&resize=800x600",
               "https://cdn.dribbble.com/users/729161/screenshots/4205996/restaurent.jpg?compress=1&resize=800x600",
               "https://cdn.dribbble.com/users/730703/screenshots/15381871/media/a5a49eea2019f8c979332d121bb5d0da.jpg?compress=1&resize=800x600")


        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            layoutInflater = (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater)!!

            val view: View = layoutInflater.inflate(layouts.get(position), container, false)
            val imageView = view.findViewById<View>(R.id.slider_image) as ImageView
            Glide.with(context).load(images.get(position)).into(imageView)
            container.addView(view)
            return view
        }


        override fun getCount(): Int {
            return layouts.size;
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

       override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
           val view = `object` as View
           container.removeView(view)
       }
   }
}
