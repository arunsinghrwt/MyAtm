package com.arun.myatm.view.utility

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager


/**
// Created by Arun Singh Rawat on 02-06-2022.



 **/

fun saveFirstInstall(tag: String, value: Boolean , context : Context) {
    val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    with (preferences.edit()) {
        putBoolean(tag, value)
        apply()
    }
}
