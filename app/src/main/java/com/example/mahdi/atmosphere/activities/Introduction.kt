package com.example.mahdi.atmosphere.activities

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.mahdi.atmosphere.R
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment
import com.github.appintro.model.SliderPage

class Introduction : AppIntro2() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isColorTransitionsEnabled = true

        val slide1 = SliderPage()
        slide1.title = "اتمسفر"
        slide1.description = "اتمسفر یک نرم افزار وضعیت آب و هواست"
        slide1.imageDrawable = R.drawable.ic_clouds
        slide1.backgroundColor = ContextCompat.getColor(this, R.color.blue)
        addSlide(AppIntroFragment.newInstance(slide1))

        val slide2 = SliderPage()
        slide2.title = "وضعیت آب‌ و هوا"
        slide2.description = "اتمسفر وضعیت آب و هوا و پیشبینی رو تا یک هفته نشون میده"
        slide2.imageDrawable = R.drawable.ic_sun
        slide2.backgroundColor = ContextCompat.getColor(this, R.color.purple)
        addSlide(AppIntroFragment.newInstance(slide2))

        val slide3 = SliderPage()
        slide3.title = "رایگان و منبع باز"
        slide3.description = "اتمسفر رایگان و برای ‌همه است."
        slide3.imageDrawable = R.drawable.ic_heart
        slide3.backgroundColor = ContextCompat.getColor(this, R.color.red)
        addSlide(AppIntroFragment.newInstance(slide3))
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        startActivity(Intent(this@Introduction, MainActivity::class.java))
        finish()
    }


    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        startActivity(Intent(this@Introduction, MainActivity::class.java))
        finish()
    }
}