package com.example.ara.atmospher.functions;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ViewManager {

    public void toggleView(View view) {
        if (view.getVisibility() == View.INVISIBLE)
            view.setVisibility(View.VISIBLE);
        else if (view.getVisibility() == View.VISIBLE)
            view.setVisibility(View.INVISIBLE);
    }

    public void hideView(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    public void showView(View view) {
        view.setVisibility(View.VISIBLE);
    }

    public void toggleSoftKeyboard(InputMethodManager imm) {
        if (imm.isActive())
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        else
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

    }

    public void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
