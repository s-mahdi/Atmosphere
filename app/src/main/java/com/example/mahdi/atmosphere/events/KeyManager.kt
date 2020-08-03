package com.example.mahdi.atmosphere.events
import android.app.Activity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.mahdi.atmosphere.R
import com.example.mahdi.atmosphere.functions.ViewManager

class KeyManager(activity: Activity, val onSubmit: () -> Unit) : View.OnKeyListener {
    private val searchBar: View = activity.findViewById(R.id.searchbar)
    private val input: EditText = activity.findViewById(R.id.editText_search_city)

    private val viewManager = ViewManager(activity)
    private val imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

    override fun onKey(view: View?, p1: Int, event: KeyEvent?): Boolean {
        when (view?.id) {
            R.id.editText_search_city -> onSubmit()
        }
        return false
    }

}