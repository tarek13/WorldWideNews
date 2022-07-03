package com.link.worldwidenews.utils.extensions

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.link.worldwidenews.R

fun Activity?.hideKeyboard() {
    if (this != null) {
        val inputMethodManager = inputMethodManager
        inputMethodManager.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
    }
}
fun AppCompatActivity.getCurrentFragment(): Fragment? {
    val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
    return navHostFragment?.childFragmentManager?.fragments?.get(0)
}