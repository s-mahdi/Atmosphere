package com.example.ara.atmospher.functions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class ViewManager {
    fun toggleView(view: View) {
        if (view.visibility == View.INVISIBLE) view.visibility = View.VISIBLE else if (view.visibility == View.VISIBLE) view.visibility = View.INVISIBLE
    }

    fun hideView(view: View) {
        view.visibility = View.INVISIBLE
    }

    fun showView(view: View) {
        view.visibility = View.VISIBLE
    }

    fun toggleSoftKeyboard(imm: InputMethodManager) {
        if (imm.isActive) imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0) else imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}