package com.link.worldwidenews.utils.extensions

import androidx.fragment.app.Fragment
import com.link.worldwidenews.R

/*
fun Fragment.logoutAndClearData(clearUserData: () -> Unit) {
    clearUserData.invoke()
    findNavController().navigate(R.id.loginFragment)
}*/
fun Fragment.getCurrentFragment(): Fragment? {
    val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment)
    return navHostFragment?.childFragmentManager?.fragments?.get(0)
}