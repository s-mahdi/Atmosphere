package com.example.ara.atmospher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class Introduction extends AppCompatActivity {

    Button letsGoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent i = new Intent(Introduction.this, MainActivity.class);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.ara.atmospher", Context.MODE_PRIVATE);

        if (!Objects.equals(sharedPreferences.getString("isLogedIn", String.valueOf(false)), "true")) {
            setContentView(R.layout.activity_introduction);
            sharedPreferences.edit().putString("isLogedIn", "true").apply();

            letsGoButton = findViewById(R.id.button_start);

            letsGoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(i);
                    finish();
                }
            });

        } else {
            startActivity(i);
            finish();
        }



    }
}
