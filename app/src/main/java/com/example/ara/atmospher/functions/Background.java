package com.example.ara.atmospher.functions;

import android.graphics.drawable.Drawable;

import com.example.ara.atmospher.R;

public class Background {
    public int getBackground(int statusId) {
        switch (statusId / 100) {
            case 2:
                return R.drawable.thunderstorm;
            case 3:
                return R.drawable.drizzle;
            case 5:
                return R.drawable.rainy_3;
            case 6:
                return R.drawable.snowy_2;
            case 7:
                return R.drawable.foggy_2;
            case 8:
                return R.drawable.sunny;
            default:
                return 0;
        }

    }
}
