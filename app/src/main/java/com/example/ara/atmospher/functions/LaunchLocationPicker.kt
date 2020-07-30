package com.example.ara.atmospher.functions

import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.ara.atmospher.R
import com.example.ara.atmospher.fragments.AmbiguousLocationDialogFragment
import com.example.ara.atmospher.models.opencage.Result
import com.google.gson.Gson
import org.json.JSONArray

fun launchLocationPickerDialog(activity: FragmentActivity, cityList: List<Result>) {
    val fragment = AmbiguousLocationDialogFragment()
    val bundle = Bundle()
    val fragmentManager = activity.supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()



    bundle.putString("cityList", Gson().toJson(cityList))
    fragment.arguments = bundle
    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
    fragmentTransaction.add(android.R.id.content, fragment).addToBackStack(null).commit()
//    fragmentTransaction.add(R.id.content, fragment)
//            .addToBackStack(null).commit()
}
