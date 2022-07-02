package com.link.worldwidenews.utils.extensions

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.link.worldwidenews.R


fun Context.showToastMessage(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resId, duration).show()
}

fun Context.showToastMessage(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text.orEmpty(), duration).show()
}


fun Context.loadColor(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}


fun Context.loadDrawable(@DrawableRes colorRes: Int): Drawable? {
    return ContextCompat.getDrawable(this, colorRes)
}

fun Context.displayAlertDialog(
    message: String?,
    title: String? = null,
    positiveButtonTitle: String? = null,
    negativeButtonTitle: String? = null,
    cancelable: Boolean = true,
    positiveOnClickListener: DialogInterface.OnClickListener? = null,
    negativeOnClickListener: DialogInterface.OnClickListener? = null
) {
    val builder = AlertDialog.Builder(this)
    builder.setMessage(message)
        .setCancelable(cancelable)
        .setTitle(title)
        .setPositiveButton(
            positiveButtonTitle ?: this.resources.getString(android.R.string.ok),
            positiveOnClickListener
        )
    if (negativeButtonTitle != null) {
        builder.setNegativeButton(negativeButtonTitle, negativeOnClickListener)
    }
    val dialog = builder.create()


    dialog.show()
}

fun Context.openUrl(url: String?) {
    try {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        this.startActivity(i)
    } catch (e1: Exception) {
        e1.printStackTrace()
        try {
            this.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: Exception) {
            e.printStackTrace()
            showToastMessage(
                R.string.this_is_not_valid_link,
            )
        }
    }
}