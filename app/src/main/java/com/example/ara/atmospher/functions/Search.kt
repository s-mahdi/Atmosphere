package com.example.ara.atmospher.functions

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.ara.atmospher.R

fun onSearch (activity: Activity, callback: () -> Unit) {
    val searchInput = activity.findViewById<EditText>(R.id.editText_search_city);
    val searchBar = activity.findViewById<ConstraintLayout>(R.id.searchbar)
    val viewManager = ViewManager();
    val searchValue = searchInput.text.toString()
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager;

    if (searchValue.isNotEmpty()) {
        viewManager.hideView(searchBar)
        callback();

        //hide soft keyboard
        viewManager.toggleSoftKeyboard(imm)
        searchInput.setText("")
    } else Toast.makeText(activity, "متنی وارد نشده!", Toast.LENGTH_SHORT).show()
}