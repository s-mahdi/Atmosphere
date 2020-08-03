package com.example.mahdi.atmosphere.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mahdi.atmosphere.activities.MainActivity
import com.example.mahdi.atmosphere.R
import com.example.mahdi.atmosphere.adapters.LocationsRecyclerAdapter
import com.example.mahdi.atmosphere.functions.getLocationName
import com.example.mahdi.atmosphere.models.opencage.Geometry
import com.example.mahdi.atmosphere.models.opencage.Result
import com.google.gson.Gson
import org.json.JSONException

class AmbiguousLocationDialogFragment : Fragment(), LocationsRecyclerAdapter.ItemClickListener {
    private var recyclerAdapter: LocationsRecyclerAdapter? = null
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_ambiguous_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val formatting = Formatting(activity)
        val bundle = arguments
        val toolbar: Toolbar = view.findViewById(R.id.dialogToolbar)
        val recyclerView: RecyclerView = view.findViewById(R.id.locationsRecyclerView)
        val linearLayout = view.findViewById<LinearLayout>(R.id.locationsLinearLayout)
        toolbar.title = "موقعیت‌ها"
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
//        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp)
        toolbar.setNavigationOnClickListener(View.OnClickListener { activity!!.supportFragmentManager.popBackStack() })
        sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
        try {
            // city list comes from other activities as prop
            val list = bundle!!.getString("cityList")
            val results = Gson().fromJson(list, Array<Result>::class.java).toList()
            recyclerAdapter = LocationsRecyclerAdapter(activity!!.applicationContext, results as java.util.ArrayList<Result>, this)
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = recyclerAdapter
            recyclerView.layoutManager = LinearLayoutManager(activity)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    @SuppressLint("ApplySharedPref")
    override fun onItemClickListener(view: View?, position: Int) {
        val location = recyclerAdapter!!.getItem(position)
        val intent = Intent(activity, MainActivity::class.java)
        val bundle = Bundle()
        val locationName = getLocationName(location)
        val map: Map<String, Geometry> = mapOf(locationName to location.geometry)
        sharedPreferences!!.edit().putString("city", Gson().toJson(map)).commit()
        bundle.putBoolean("shouldRefresh", true)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}