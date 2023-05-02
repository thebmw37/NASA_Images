package com.nasaImages.ui

import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nasaImages.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ComposeFragment()).commit()

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setOnItemSelectedListener {
            val itemId = it.itemId

            val selectedFragment: Fragment = if (itemId == R.id.xml)
                XmlFragment()
            else
                ComposeFragment()

            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()

            true
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        hideSoftwareKeyboard(this)
        return super.onTouchEvent(event)
    }

    private fun hideSoftwareKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        activity.currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}