package com.example.ara.atmospher.functions;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ViewManager {

    public void toggleView(View view) {
        if (view.getVisibility() == View.INVISIBLE)
            view.setVisibility(View.VISIBLE);
        else if (view.getVisibility() == View.VISIBLE)
            view.setVisibility(View.INVISIBLE);
    }

    public void toggleSoftKeyboard(InputMethodManager imm) {
        if (imm.isActive())
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        else
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

    }
}
