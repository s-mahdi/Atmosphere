package com.example.mahdi.atmosphere.functions

import android.app.Activity
import android.content.Context
import com.example.mahdi.atmosphere.models.opencage.Geometry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

fun syncPreferences(activity: Activity): Map<String, Geometry>? {
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    val city = sharedPref.getString("city", null)
    val mapType: Type = object : TypeToken<Map<String, Geometry>>() {}.type
    return Gson().fromJson<Map<String, Geometry>>(city, mapType)
}

fun updatePreferences(activity: Activity,key: String, value: String) {
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    sharedPref.edit().putString(key, value).apply()
}
