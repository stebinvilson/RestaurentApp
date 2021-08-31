package com.restoo.restaurentapp.Fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.restoo.restaurentapp.R


class LoginFragment : Fragment() {

    lateinit var app_title : TextView
   lateinit var firebaseDatabase: FirebaseDatabase
   lateinit var databaseReference: DatabaseReference
   lateinit var mTxtBack : TextView
    lateinit var context1 : Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_login, container, false)
        Initialisation(view)
        EventListner()
        return  view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) context1 = context as FragmentActivity
    }



    private fun EventListner() {
        mTxtBack.setOnClickListener(View.OnClickListener {

        })
    }

    private fun Initialisation(view: View) {
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("User");
        app_title = view.findViewById(R.id.app_welcome)
        mTxtBack = view.findViewById(R.id.txt_back)

    }
}