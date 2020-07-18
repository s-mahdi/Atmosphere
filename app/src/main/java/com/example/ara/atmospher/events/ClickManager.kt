package com.example.ara.atmospher.events

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.ara.atmospher.MainActivity
import com.example.ara.atmospher.R
import com.example.ara.atmospher.functions.ViewManager

class ClickManager(private val activity: Activity, val onSearch: () -> Unit) : View.OnClickListener {

    private val viewManager = ViewManager();
    private val searchBar = activity.findViewById<ConstraintLayout>(R.id.searchPlot)
    private val searchInput = activity.findViewById<EditText>(R.id.editText_search_city);
    private val mDrawerLayout = activity.findViewById<DrawerLayout>(R.id.drawer_layout);
    private val imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager;

    override fun onClick(view: View) {

        when (view.id) {
            R.id.imageButton_addCity -> {
                viewManager.showView(searchBar)
                searchInput.requestFocus()
                //show soft keyboard
                viewManager.toggleSoftKeyboard(imm);
            }
            R.id.search_city_button -> onSearch()
            R.id.ImageButton_close_search_plot -> {
                viewManager.hideView(searchBar)
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            }
            R.id.imageButton_drawer_hamburger -> mDrawerLayout.openDrawer(GravityCompat.END)
            else -> {
                viewManager.hideKeyboardFrom(activity, view)
                viewManager.hideView(searchBar);
            }
        }
    }

}