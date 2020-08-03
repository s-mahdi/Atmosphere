package com.example.mahdi.atmosphere.functions

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.mahdi.atmosphere.fragments.AmbiguousLocationDialogFragment
import com.example.mahdi.atmosphere.models.opencage.Result
import com.google.gson.Gson

fun launchLocationPickerDialog(activity: FragmentActivity, cityList: List<Result>) {
    val fragment = AmbiguousLocationDialogFragment()
    val bundle = Bundle()
    val fragmentManager = activity.supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()

    bundle.putString("cityList", Gson().toJson(cityList))
    fragment.arguments = bundle
    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
    fragmentTransaction.add(android.R.id.content, fragment).addToBackStack(null).commit()
}
