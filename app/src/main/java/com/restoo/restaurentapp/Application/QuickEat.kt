package com.restoo.restaurentapp.Application

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object QuickEat {
    private const val NAME = "QuickEat"
    private const val MODE = Context.MODE_PRIVATE
    lateinit  var application : QuickEat
    lateinit var _preferences: SharedPreferences
    private val IS_FIRST_RUN_PREF = Pair("is_first_run", false)
    private val USER_UID = Pair("user_id", "")

    fun init(context: Context) {
        application = this
        _preferences = context.getSharedPreferences(NAME,MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var firstrun : Boolean

    get() = _preferences.getBoolean(IS_FIRST_RUN_PREF.first, IS_FIRST_RUN_PREF.second)

    set(value) = _preferences.edit {
        it.putBoolean(IS_FIRST_RUN_PREF.first,value)
    }

    var UserId : String

        get() = _preferences.getString(USER_UID.first, USER_UID.second)!!

        set(value) = _preferences.edit {
            it.putString(USER_UID.first,value)
        }
}