package com.link.worldwidenews.utils.extensions

import android.app.Activity

fun Activity?.hideKeyboard() {
    if (this != null) {
        val inputMethodManager = inputMethodManager
        inputMethodManager.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
    }
}