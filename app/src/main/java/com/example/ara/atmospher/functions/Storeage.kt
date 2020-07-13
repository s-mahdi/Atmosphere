package com.example.ara.atmospher.functions

import android.content.SharedPreferences
import com.example.ara.atmospher.R

fun syncStorage (sharedPreferences: SharedPreferences): String? {
   return sharedPreferences.getString(R.string.city_key.toString(), "tehran")
}