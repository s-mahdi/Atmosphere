package com.example.ara.atmospher.functions

import android.app.Activity
import android.content.Context

fun checkFirstTime(activity: Activity): Boolean {
    return activity.getPreferences(Context.MODE_PRIVATE).getBoolean("isFirstTime", true)
}
