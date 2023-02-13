package com.rzgonz.nutechwallet.core.utils

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import java.text.DecimalFormat
import java.text.NumberFormat

fun safeLaunch(block: () -> Unit) = try {
    block.invoke()
} catch (e: Exception) {
    e.printStackTrace()
}

inline fun <reified T> safeLaunchWithResult(default: T, block: () -> T): T = try {
    block()
} catch (e: Exception) {
    e.printStackTrace()
    default
}

fun isValidEmail(target: CharSequence?): Boolean {
    return if (TextUtils.isEmpty(target)) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}
fun Context.showToastMessage(message:String){
    Toast.makeText(
        this, message,
        Toast.LENGTH_LONG
    ).show()
}

fun Int.decimalFormat(): String {
    val formatter: NumberFormat = DecimalFormat("#,###")
    return formatter.format(this)
}