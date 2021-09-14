package com.restoo.restaurentapp.Fragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.restoo.restaurentapp.Activity.LoginActivity
import com.restoo.restaurentapp.Activity.WelcomeActivity
import com.restoo.restaurentapp.Application.QuickEat
import com.restoo.restaurentapp.R
import com.restoo.restaurentapp.model.UserDetails


class RegisterFragment : Fragment() {
    lateinit var mEdtUsername : EditText
    lateinit var mEdtPhonenumber : EditText
    lateinit var mEdtEmail : EditText
    lateinit var mEdtPassword : EditText
    lateinit var mBtnGetstarted : Button
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var mLayoutEmail : TextInputLayout
    var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+" as String
    lateinit var auth : FirebaseAuth
    lateinit var mTxtBack : TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.layout_register, container, false)
        Initialisation(view)
        EventListner()
        return view
    }
    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun EventListner() {

        mTxtBack.setOnClickListener(View.OnClickListener {
            activity?.moveTaskToBack(true)
            activity?.finish()
        })

        mEdtEmail.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                validatedata()
            }
        })

        mBtnGetstarted.setOnClickListener(View.OnClickListener {
            AuthenticationToFirebase(mEdtEmail.text.toString(), mEdtPassword.text.toString())
        })
    }

    fun AuthenticationToFirebase(email: String, password: String) {

        activity?.let {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(it,
                    OnCompleteListener<AuthResult?> { task ->
                        if (task.isSuccessful) {
                            val userdetails = UserDetails(
                                mEdtUsername.text.toString(),
                                mEdtPassword.text.toString(),
                                mEdtPhonenumber.text.toString(),
                                mEdtEmail.text.toString()
                            )
                            val user1: FirebaseUser? = FirebaseAuth.getInstance().getCurrentUser()
                            if (user1 != null) {
                                databaseReference.child("user").child(user1.uid)
                                    .addValueEventListener(
                                        object :
                                            ValueEventListener {
                                            override fun onDataChange(snapshot: DataSnapshot) {
                                                if (snapshot.getValue() == null) {
                                                    QuickEat.UserId = user1.uid
                                                    databaseReference.child("user").child(user1.uid)
                                                        .setValue(userdetails)
                                                    val intent = Intent(activity, WelcomeActivity::class.java)
                                                    startActivity(intent)
                                                    updateFirebaseUserDisplayName()
                                                }
                                            }

                                            override fun onCancelled(error: DatabaseError) {
                                                Toast.makeText(
                                                    activity,
                                                    "Fail to add data $error",
                                                    Toast.LENGTH_SHORT).show()
                                            }
                                            })
                            }

                        } else if (!task.isSuccessful) {

                            try {
                                throw task.exception!!
                            } catch (weakPassword: FirebaseAuthWeakPasswordException) {
                                Log.d(TAG, "onComplete: weak_password")
                                Toast.makeText(
                                    activity,
                                    "Something went wrong",
                                    Toast.LENGTH_SHORT
                                ).show()


                            } catch (malformedEmail: FirebaseAuthInvalidCredentialsException) {
                                Log.d(TAG, "onComplete: malformed_email")
                                Toast.makeText(
                                    activity,
                                    "Something went wrong",
                                    Toast.LENGTH_SHORT
                                ).show()

                            } catch (existEmail: FirebaseAuthUserCollisionException) {
                                Log.d(TAG, "onComplete: exist_email")
                                Toast.makeText(
                                    activity,
                                    "This emailid is used already",
                                    Toast.LENGTH_SHORT
                                ).show()

                            } catch (e: Exception) {
                                Log.d(TAG, "onComplete: " + e.message)
                                Toast.makeText(
                                    activity,
                                    "Something went wrong",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
        }
    }

    private fun updateFirebaseUserDisplayName() {

        FirebaseAuth.getInstance().currentUser?.apply {
            val profileUpdates : UserProfileChangeRequest = UserProfileChangeRequest.Builder().setDisplayName(
                mEdtUsername.text.toString()
            ).build()
            updateProfile(profileUpdates)?.addOnCompleteListener(OnCompleteListener {
                when (it.isSuccessful) {
                    true -> apply {

                    }
                }
            })
        }
    }

    private fun validatedata() {

       if ( mEdtEmail.text.toString().isEmailValid() ) {
           mLayoutEmail.setError(null)
       } else {
           mLayoutEmail.setError("Enter valid email")
       }
    }

    private fun Initialisation(view: View) {
        auth = Firebase.auth
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference
        mLayoutEmail = view.findViewById(R.id.email_text_input)
        mEdtUsername = view.findViewById(R.id.name_edit_text)
        mEdtPhonenumber = view.findViewById(R.id.phonenumber_edit_text)
        mEdtEmail = view.findViewById(R.id.email_edit_text)
        mEdtPassword = view.findViewById(R.id.password_edit_text)
        mBtnGetstarted = view.findViewById(R.id.get_started)
        mTxtBack = view.findViewById(R.id.txt_back)

    }
}





