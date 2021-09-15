package com.restoo.restaurentapp.Activity

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.animation.Animation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.restoo.restaurentapp.Adapter.SliderAdapter
import com.restoo.restaurentapp.R
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView


class ProActivity : AppCompatActivity() {

   lateinit var sliderview : SliderView
   lateinit var mTxtLogoName : TextView
    lateinit var mTxtBling : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_proactivity)
        Initialization()
        setData()
        Eventlistner()
    }

    private fun Eventlistner() {

    }

    private fun setData() {

        val wordtoSpan: Spannable =
                SpannableString("Quick Eat")
        wordtoSpan.setSpan(
                ForegroundColorSpan(Color.parseColor("#246EE9")),
                0,
                5,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        wordtoSpan.setSpan(
                ForegroundColorSpan(Color.parseColor("#3EB489")),
                6,
                9,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        mTxtLogoName.setText(wordtoSpan)
        val url1 = "https://images.unsplash.com/photo-1499028344343-cd173ffc68a9?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80"
        val url2 = "https://images.unsplash.com/photo-1432139555190-58524dae6a55?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=755&q=80"
        val url3 = "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80"
        var imagedata : ArrayList<Int> = ArrayList()
        imagedata.add(R.drawable.slidingposter)
        imagedata.add(R.drawable.slidingposter1)
        imagedata.add(R.drawable.slidingposter2)
        val adapter = SliderAdapter(imagedata)

        sliderview.setIndicatorAnimation(IndicatorAnimationType.SLIDE);
        sliderview.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
       // sliderview.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR)
        sliderview.setSliderAdapter(adapter)
        sliderview.setScrollTimeInSec(5)
        sliderview.setAutoCycle(true)
        sliderview.startAutoCycle()
    }

    private fun Initialization() {
        mTxtBling = findViewById(R.id.bling_textview)
        sliderview = findViewById(R.id.imageSlider)
        mTxtLogoName = findViewById(R.id.title_logo)
    }
}