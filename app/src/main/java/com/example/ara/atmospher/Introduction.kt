package com.example.ara.atmospher

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Introduction : AppCompatActivity() {
    private var letsGoButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val i = Intent(this@Introduction, MainActivity::class.java)
        val sharedPreferences = getSharedPreferences("com.example.ara.atmospher", Context.MODE_PRIVATE)
        if (sharedPreferences.getString("isLogedIn", false.toString()) != "true") {
            setContentView(R.layout.activity_introduction)
            sharedPreferences.edit().putString("isLogedIn", "true").apply()
            letsGoButton = findViewById(R.id.button_start)
            letsGoButton?.setOnClickListener(View.OnClickListener {
                startActivity(i)
                finish()
            })
        } else {
            startActivity(i)
            finish()
        }
    }
}