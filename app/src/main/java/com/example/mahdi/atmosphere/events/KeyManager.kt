package com.example.mahdi.atmosphere.events

import android.view.KeyEvent
import android.view.View

class KeyManager(val onSubmit: () -> Unit) : View.OnKeyListener {
    override fun onKey(view: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_ENTER -> {
                    onSubmit()
                    return true
                }
            }
        }
        return false
    }

}