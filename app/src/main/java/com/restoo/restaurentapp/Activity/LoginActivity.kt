package com.restoo.restaurentapp.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.restoo.restaurentapp.Application.QuickEat
import com.restoo.restaurentapp.Fragments.LoginFragment
import com.restoo.restaurentapp.Fragments.RegisterFragment
import com.restoo.restaurentapp.R

class LoginActivity : AppCompatActivity() {
    lateinit var type : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QuickEat.init(this)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        receiveArguments()
    }

    private fun receiveArguments() {
        type = intent.getStringExtra(getString(R.string.type)).toString()
        if (type.equals("1")) {
            val fragment = LoginFragment()
            this.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.containerlayout, fragment)
                .setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
                .commit()
        } else if (type.equals("2")) {
            val fragment = RegisterFragment()
            this.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.containerlayout, fragment)
                .setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
                .commit()
        }
    }
}