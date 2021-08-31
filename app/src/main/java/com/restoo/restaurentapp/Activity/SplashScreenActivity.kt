package com.restoo.restaurentapp.Activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.restoo.restaurentapp.R


class SplashScreenActivity : AppCompatActivity() {
    lateinit var mTxtAppTitle : TextView
    lateinit var mImgOne: ImageView
    lateinit var mBtnSignin : Button
    lateinit var mBtnReister : Button
    lateinit var mImgTwo : ImageView
    lateinit var mImgThree : ImageView
    lateinit var mImgFour : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_splashscreen)
        supportActionBar?.hide()
        Initialisation()
        setScreen()
        Eventlistner()
    }

    private fun Eventlistner() {
        mBtnReister.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra(getString(R.string.type), "2")
            startActivity(intent)
        })
        mBtnSignin.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra(getString(R.string.type), "1")
            startActivity(intent)
        })
    }

    private fun setScreen() {
        Glide.with(applicationContext).load("https://images.unsplash.com/photo-1499028344343-cd173ffc68a9?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80").into(mImgOne)
        Glide.with(applicationContext).load("https://images.unsplash.com/photo-1432139555190-58524dae6a55?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=755&q=80").into(mImgTwo)
        Glide.with(applicationContext).load("https://images.unsplash.com/photo-1555939594-58d7cb561ad1?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80").into(mImgThree)
        Glide.with(applicationContext).load("https://images.unsplash.com/photo-1593584785033-9c7604d0863f?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=658&q=80").into(mImgFour)

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
        mTxtAppTitle.setText(wordtoSpan)
    }

    private fun Initialisation() {
        mImgOne = findViewById<View>(R.id.food) as ImageView
        mImgTwo = findViewById(R.id.food1) as ImageView
        mImgThree = findViewById(R.id.food2) as ImageView
        mImgFour = findViewById(R.id.food3) as ImageView
        mTxtAppTitle = findViewById(R.id.app_title)
        mBtnSignin = findViewById(R.id.btn_signin)
        mBtnReister = findViewById(R.id.btn_register)

    }
}


