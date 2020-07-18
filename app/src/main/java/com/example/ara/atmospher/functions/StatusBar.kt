package com.example.ara.atmospher.functions

import android.app.Activity
import android.os.Build
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager

fun hideStatusBar(activity: Activity) {

    activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        activity.window.setFlags(
                WindowInsetsController.BEHAVIOR_SHOW_BARS_BY_SWIPE,
                WindowInsetsController.BEHAVIOR_SHOW_BARS_BY_SWIPE
        )
    } else {
        activity.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}